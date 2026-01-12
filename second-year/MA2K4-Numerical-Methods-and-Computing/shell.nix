{
  pkgs ? import <nixpkgs> { },
}:
let
  python = pkgs.python3.withPackages (
    p: with p; [
      numpy
      matplotlib
      jupyter
    ]
  );

  texlive = pkgs.texlive.withPackages (
    p: with p; [
      scheme-medium
      adjustbox
      amsmath
      environ
      enumitem
      pdfcol
      tcolorbox
      titling
      upquote
    ]
  );
in
pkgs.mkShell {
  buildInputs = [
    python
    texlive
  ]
  ++ (with pkgs; [
    just
    pandoc
  ]);

  shellHook = ''
    export PYTHONPATH="${python}/${python.sitePackages}"
  '';
}
