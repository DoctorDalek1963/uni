{pkgs ? import <nixpkgs> {}}: let
  texlive = pkgs.texlive.withPackages (p:
    with p; [
      scheme-medium

      gensymb
      cancel
      csquotes

      # For questionbody environment
      mdframed
      needspace
      zref
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
