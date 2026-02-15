{
  pkgs ? import <nixpkgs> { },
}:
let
  python = pkgs.python3.withPackages (
    p: with p; [
      rich

      numpy
      matplotlib
      jupyter
      jupyterlab-vim
    ]
  );
in
pkgs.mkShell {
  buildInputs = [
    python
    pkgs.nodejs
  ];

  shellHook = ''
    export PYTHONPATH="${python}/${python.sitePackages}"
  '';
}
