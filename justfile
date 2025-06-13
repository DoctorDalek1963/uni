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
[group("pdf")]
[no-cd]
_build:
    lualatex -file-line-error -halt-on-error -interaction=nonstopmode -shell-escape -recorder -synctex=1 --jobname="main" "main.tex"

# run commands before the build
[group("pdf")]
_pre-build:
    @true

# build the assignment in the current directory
[group("pdf")]
build: _pre-build _build

# Like with the build recipes, these clean recipes allow individual assignments
# (like coding projects) to override `clean` while also defering to
# _clean_latex when needed

# just clean the LaTeX
[group("pdf")]
[no-cd]
_clean_latex:
    latexmk -c -C
    texhash

# remove all build artifacts
[group("pdf")]
[no-cd]
clean: _clean_latex

# open main.pdf to view
[group("pdf")]
[no-cd]
view: build
    xdg-open main.pdf &> /dev/null & disown

# watch the directory and recompile whenever a file changes
[group("pdf")]
[no-cd]
watch: view
    while inotifywait -q -e modify *; do just build; done

# build and rename main.pdf to proper filename
[group("pdf")]
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
[group("pdf")]
finish: clean rename

# create a new main.tex for an assignment in the current directory
[group("create")]
[no-cd]
new-ass:
    #!/usr/bin/env python3
    import os
    import re

    from rich import print

    QUESTION_BODY_COLOR_MAP = {
        "CS147": "orange!50",
        "MA150": "magenta!50",
    }

    with open("{{ source_directory() }}/templates/ass/main.tex", "r") as f:
        text = f.read()

    try:
        [course_dir, ass_dir] = os.getcwd().split("/")[-2:]

        course_short, course_full = re.match(r"([A-Z0-9]{5})-(.*)", course_dir).group(1, 2)
        ass_num = re.match(r".*?(\d+)$", ass_dir).group(1)

        text = (
            text.replace("#COURSE-SHORT#", course_short)
            .replace("#COURSE-FULL#", f"{course_short} {course_full.replace("-", " ")}")
            .replace("#NUMBER#", ass_num)
        )

        try:
            question_body_color = QUESTION_BODY_COLOR_MAP[course_short]
            text = text.replace("#QUESTION-BODY-COLOR#", question_body_color)
        except KeyError:
            print(f'[bold yellow]WARNING[/bold yellow]: Short course name "{course_short}" not defined in QUESTION_BODY_COLOR_MAP, so not using correct colour')
    except (ValueError, AttributeError):
        print("[bold yellow]WARNING[/bold yellow]: Directory not formatted like an assignment, so just using template main.tex")

    with open("main.tex", "w") as f:
        f.write(text)

# create a new justfile in the current directory for specific additions and overrides
[group("create")]
[no-cd]
new-justfile:
    #!/usr/bin/env python3

    from pathlib import PurePath

    with open("{{ source_directory() }}/templates/justfile", "r") as f:
        text = f.read()

    rel_path = PurePath("{{ source_directory() }}").relative_to(
        PurePath("{{ invocation_directory() }}"), walk_up=True
    )

    with open("justfile", "w") as f:
        f.write(text.replace("#REL_PATH_TO_ROOT#", str(rel_path)))

# build every assignment for CI
[group("ci")]
ci-build-all:
    #!/usr/bin/env python3

    import os
    import subprocess
    import sys

    import rich

    ass_dirs = [
        dir
        for (dir, _dirs, files) in os.walk("{{ source_directory() }}")
        if "main.tex" in files and "templates" not in dir
    ] + [
        os.path.join("{{ source_directory() }}", p)
        for p in [
            "first-year/MA146-Methods-of-Mathematical-Modelling-1/Ass 1",
            "first-year/MA117-Programming-for-Scientists/projects/Project0",
            "first-year/MA117-Programming-for-Scientists/projects/Project1",
            "first-year/MA117-Programming-for-Scientists/projects/Project3",
        ]
    ]

    failed_dirs = []

    for dir in sorted(ass_dirs):
        rich.print(f"\n\n[bold]===== [magenta]Building {dir}[/magenta] =====[/bold]\n\n")
        sys.stdout.flush()

        child = subprocess.run(
            ["direnv allow && direnv exec . just clean build"],
            cwd=dir,
            shell=True,
            env=(os.environ | {"_nix_direnv_force_reload": "1"}),
        )

        if child.returncode != 0:
            failed_dirs.append(dir)

    if len(failed_dirs) > 0:
        rich.print(f"\n\n[bold]===== [red]FAILED {len(failed_dirs)}/{len(ass_dirs)} DIRECTORIES[/red]: =====[/bold]\n")
        print(*failed_dirs, sep="\n")
        exit(1)
    else:
        rich.print(f"\n\n[bold]===== [green]All {len(ass_dirs)} builds successful![/green] =====[/bold]")
