# source_directory() will always use the directory of this justfile, even when
# called via a justfile that imports this one
export TEXMFHOME := source_directory() / "texmf"

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

# build the assignment in the current directory (assuming main.tex)
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

# open ./main.pdf to view
view: build
	xdg-open "{{invocation_directory()}}/main.pdf" &> /dev/null & disown

# watch the directory and recompile whenever a file changes
[no-cd]
watch: view
	while inotifywait -q -e modify *; do just build; done

# build and rename main.pdf to proper filename
rename: build
	#!/usr/bin/env python3
	import os
	import re

	os.chdir("{{invocation_directory()}}")
	[course_dir, ass_dir] = os.getcwd().split("/")[-2:]

	course_code = re.match(r"([A-Z0-9]{5}).*", course_dir).group(1)
	ass_num = re.match(r".*?(\d+)$", ass_dir).group(1)

	os.rename("main.pdf", f"Dyson_Dyson_5503449_{course_code}_Assignment_{ass_num}.pdf")

# build a fresh PDF from scratch and rename it
finish: clean rename

[no-cd]
new-ass:
	#!/usr/bin/env python3
	import os
	import re

	os.chdir("{{invocation_directory()}}")
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

	with open("{{invocation_directory()}}/main.tex", "w") as f:
		f.write(text)
