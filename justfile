# source_directory() will always use the directory of this justfile, even when
# called via a justfile that imports this one
export TEXMFHOME := source_directory() / "texmf"

alias b := build
alias v := view
alias w := watch
alias c := clean
alias r := rename
alias f := finish
alias na := new-ass
alias nj := new-justfile

_default:
	@just --list

# This three-recipe build system allows certain assignments to override
# _pre-build to do extra things, like running Python to generate images for
# example, or if an assignment has intra-doc references and needs to be built
# twice, _pre-build can depend on `_build` rather than `build` to prevent
# dependency cycles.

# do the actual build
[no-cd]
_build:
	lualatex -file-line-error -halt-on-error -interaction=nonstopmode -shell-escape -recorder --jobname="main" "main.tex"

# run commands before the build
_pre-build:
	@true

# build the assignment in the current directory
build: _pre-build _build

# Like with the build recipes, these clean recipes allow individual assignments
# (like coding projects) to override `clean` while also defering to
# _clean_latex when needed

# just clean the LaTeX
[no-cd]
_clean_latex:
	latexmk -c -C
	texhash

# remove all build artifacts
[no-cd]
clean: _clean_latex

# open main.pdf to view
[no-cd]
view: build
	xdg-open main.pdf &> /dev/null & disown

# watch the directory and recompile whenever a file changes
[no-cd]
watch: view
	while inotifywait -q -e modify *; do just build; done

# build and rename main.pdf to proper filename
[no-cd]
rename: build
	#!/usr/bin/env python3
	import os
	import re

	[course_dir, ass_dir] = os.getcwd().split("/")[-2:]

	course_code = re.match(r"([A-Z0-9]{5}).*", course_dir).group(1)
	ass_num = re.match(r".*?(\d+)$", ass_dir).group(1)

	os.rename("main.pdf", f"Dyson_Dyson_5503449_{course_code}_Assignment_{ass_num}.pdf")

# build a fresh PDF from scratch and rename it
finish: clean rename

# create a new main.tex for an assignment in the current directory
[no-cd]
new-ass:
	#!/usr/bin/env python3
	import os
	import re

	[course_dir, ass_dir] = os.getcwd().split("/")[-2:]

	course_short, course_full = re.match(r"([A-Z0-9]{5})-(.*)", course_dir).group(1, 2)
	ass_num = re.match(r".*?(\d+)$", ass_dir).group(1)

	with open("{{source_directory()}}/templates/ass/main.tex", "r") as f:
		text = f.read()

	text = (
		text.replace("#COURSE-SHORT#", course_short)
		.replace("#COURSE-FULL#", f"{course_short} {course_full.replace("-", " ")}")
		.replace("#NUMBER#", ass_num)
	)

	with open("main.tex", "w") as f:
		f.write(text)


# create a new justfile in the current directory for specific additions and overrides
[no-cd]
new-justfile:
	#!/usr/bin/env python3

	from pathlib import PurePath

	with open("{{source_directory()}}/templates/justfile", "r") as f:
		text = f.read()

	rel_path = PurePath("{{source_directory()}}").relative_to(
		PurePath("{{invocation_directory()}}"), walk_up=True
	)

	with open("justfile", "w") as f:
		f.write(text.replace("#REL_PATH_TO_ROOT#", str(rel_path)))

# build every assignment for CI
ci-build-all:
	#!/usr/bin/env python3

	import os
	import subprocess
	import sys

	ass_dirs = [
		dir
		for (dir, _dirs, files) in os.walk("{{source_directory()}}")
		if "main.tex" in files and "templates" not in dir
	] + [
		os.path.join("{{source_directory()}}", p)
		for p in [
			"first-year/MA146-Methods-of-Mathematical-Modelling-1/Ass 1",
			"first-year/MA117-Programming-for-Scientists/projects/Project0",
			"first-year/MA117-Programming-for-Scientists/projects/Project1",
		]
	]

	failed_dirs = []

	for dir in sorted(ass_dirs):
		print(f"\n\n===== Building {dir} =====\n\n")
		sys.stdout.flush()

		child = subprocess.run(
			["direnv allow && direnv exec . just build"], cwd=dir, shell=True
		)

		if child.returncode != 0:
			failed_dirs.append(dir)

	if len(failed_dirs) > 0:
		print("\n\n===== FAILED DIRECTORIES: =====\n")
		print(*failed_dirs, sep="\n")
		exit(1)
	else:
		print("\n\n===== All builds successful! =====")
