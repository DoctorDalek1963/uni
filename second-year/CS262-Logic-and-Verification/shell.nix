{
  pkgs ? import <nixpkgs> { },
}:
pkgs.mkShell {
  buildInputs = [
    pkgs.fd
    pkgs.swi-prolog
  ];
}
