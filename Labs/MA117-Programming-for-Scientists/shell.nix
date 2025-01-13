{pkgs ? import <nixpkgs> {}}:
pkgs.mkShell {
  buildInputs = with pkgs; [
    just
    mktemp
    sshfs
    jdk
  ];
}
