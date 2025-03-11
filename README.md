# University work

This repo is a collection of all my university assignments, projects, coursework, etc.

## Usage

Always remember to `nix-direnv-reload` if the cache has expired.

Each assignment should be in a numbered folder within a folder for the module,
for example `"MA150-Algebra-1/Ass 2"`. It's important to hyphenate the module
code, but the space in the assignment folder name could be removed or replaced
with other punctuation.

To start a new assignment, create a folder in that format and run `just
new-ass`. Uncomment the `graphicx` import if you need images for this assignment.

Run `just build` to build once, `just view` to build and view, or `just watch`
to view and rebuild on any changes.

Once the assignment is complete, run `just finish` to clean, build, and rename the PDF.
