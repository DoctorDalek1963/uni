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
        fd
        inotify-tools
        just
        python3
      ]);
  }
