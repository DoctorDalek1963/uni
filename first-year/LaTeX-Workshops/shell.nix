{pkgs ? import <nixpkgs> {}}: let
  texlive = pkgs.texlive.withPackages (p:
    with p; [
      scheme-medium

      bclogo
      esdiff
      framed
      gensymb
      luapstricks
      mdframed
      multirow
      needspace
      pst-blur
      pst-coil
      pst-grad
      pst-node
      pst-tools
      pstricks
      tabu
      wrapfig
      zref
    ]);
in
  pkgs.mkShell {
    buildInputs = [
      texlive
      # pkgs.texmaker
    ];
  }
