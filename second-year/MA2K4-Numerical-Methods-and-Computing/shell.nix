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
in
pkgs.mkShell {
  buildInputs = [ python ];

  shellHook = ''
    export PYTHONPATH="${python}/${python.sitePackages}"
  '';
}
