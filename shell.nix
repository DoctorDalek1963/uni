{pkgs ? import <nixpkgs> {}}: let
  texlive = pkgs.texlive.withPackages (p:
    with p; [
      scheme-medium
      gensymb
      cancel
      csquotes
    ]);
in
  pkgs.mkShell {
    buildInputs =
      [texlive]
      ++ (with pkgs; [
        # Only here for CI
        direnv

        fd
        inotify-tools
        just
        python3
      ]);
  }
