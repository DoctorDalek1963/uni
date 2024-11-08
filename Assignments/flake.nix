{
  description = "The flake that provides the LaTeX toolchain for my maths assignments";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-24.05";
    flake-parts.url = "github:hercules-ci/flake-parts";
  };

  outputs = inputs @ {flake-parts, ...}:
    flake-parts.lib.mkFlake {inherit inputs;} {
      systems = ["x86_64-linux" "aarch64-linux"];
      perSystem = {system, ...}: let
        pkgs = inputs.nixpkgs.legacyPackages.${system};
        texlive = pkgs.texlive.withPackages (p: with p; [scheme-medium gensymb]);
      in {
        devShells.default = pkgs.mkShell {
          nativeBuildInputs = [texlive] ++ (with pkgs; [just fd]);
        };
      };
    };
}
