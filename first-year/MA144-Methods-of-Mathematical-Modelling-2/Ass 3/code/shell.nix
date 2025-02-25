{pkgs ? import <nixpkgs> {}}: let
  python = pkgs.python3.withPackages (p: with p; [scipy]);
in
  pkgs.mkShell {
    buildInputs = [python];
  }
