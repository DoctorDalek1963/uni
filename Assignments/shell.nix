{pkgs ? import <nixpkgs> {}}: let
  texlive = pkgs.texlive.withPackages (p: with p; [scheme-medium gensymb]);
in
  pkgs.mkShell {
    buildInputs = [texlive] ++ (with pkgs; [just fd]);
  }
