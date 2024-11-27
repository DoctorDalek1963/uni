{pkgs ? import <nixpkgs> {}}: let
  texlive = pkgs.texlive.withPackages (p: with p; [scheme-medium gensymb cancel]);
in
  pkgs.mkShell {
    buildInputs = [texlive] ++ (with pkgs; [just fd python3]);
  }
