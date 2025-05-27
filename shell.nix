{pkgs ? import <nixpkgs> {}}: let
  texlive = pkgs.texlive.withPackages (p:
    with p; [
      scheme-medium

      gensymb
      cancel
      csquotes

      framed
      nowidow
    ]);

  python = pkgs.python3.withPackages (p: [p.rich]);
in
  pkgs.mkShell {
    buildInputs =
      [
        texlive
        python
      ]
      ++ (with pkgs; [
        # Only here for CI
        direnv

        fd
        inotify-tools
        just
      ]);
  }
