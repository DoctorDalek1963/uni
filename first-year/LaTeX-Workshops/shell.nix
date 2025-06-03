{pkgs ? import <nixpkgs> {}}: let
  texlive = pkgs.texlive.withPackages (p: with p; [scheme-full]);
in
  pkgs.mkShell {
    buildInputs = [texlive pkgs.texmaker];
  }
