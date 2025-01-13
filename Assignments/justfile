export TEXMFHOME := justfile_directory() + "/texmf"

_default:
	@just --list

# build the assignment in the current directory (assuming main.tex)
build:
	cd "{{invocation_directory()}}" && lualatex -file-line-error -halt-on-error -interaction=nonstopmode -shell-escape -recorder --jobname="main" "main.tex"

# remove all build artifacts and clean things up
clean:
	cd "{{invocation_directory()}}" && latexmk -c -C
	texhash

view: build
	xdg-open "{{invocation_directory()}}/main.pdf" &> /dev/null & disown

watch: view
	cd "{{invocation_directory()}}" && while inotifywait -q -e modify main.tex; do just build; done

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
