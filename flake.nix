{
  inputs = {
    # Use nixpkgs from flake registry
    flake-parts.url = "github:hercules-ci/flake-parts";
    pre-commit-hooks = {
      url = "github:cachix/pre-commit-hooks.nix";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs =
    inputs@{
      flake-parts,
      nixpkgs,
      ...
    }:
    flake-parts.lib.mkFlake { inherit inputs; } {
      imports = [
        inputs.pre-commit-hooks.flakeModule
      ];

      systems = [
        "x86_64-linux"
        "aarch64-linux"
      ];

      perSystem =
        {
          config,
          system,
          ...
        }:
        let
          pkgs = nixpkgs.legacyPackages.${system};

          texlive = pkgs.texlive.withPackages (
            p: with p; [
              scheme-medium

              gensymb
              cancel
              csquotes
              enumitem

              minted
              upquote

              # For questionbody environment
              mdframed
              needspace
              zref

              # For citations
              biber
              biblatex
            ]
          );

          python = pkgs.python3.withPackages (p: [ p.rich ]);

          # Include Catppuccin styles
          latexminted = pkgs.latexminted.overridePythonAttrs (oldAttrs: {
            dependencies = (oldAttrs.dependencies or [ ]) ++ [
              pkgs.python3Packages.catppuccin
            ];
          });

          nativeBuildInputs = [
            texlive
            python
            latexminted

            pkgs.fd
            pkgs.inotify-tools
            pkgs.just
          ];
        in
        {
          devShells = {
            default = pkgs.mkShell {
              inherit nativeBuildInputs;

              env.OSFONTDIR = "${pkgs.hack-font}/share/fonts";

              shellHook = ''
                ${config.pre-commit.installationScript}
              '';
            };

            ci = pkgs.mkShell {
              nativeBuildInputs = nativeBuildInputs ++ [ pkgs.direnv ];
            };
          };

          # See https://flake.parts/options/git-hooks-nix and
          # https://github.com/cachix/git-hooks.nix/blob/master/modules/hooks.nix
          # for all the available hooks and options
          pre-commit = {
            settings.hooks = {
              check-added-large-files.enable = true;
              check-merge-conflicts.enable = true;
              check-toml.enable = true;
              check-vcs-permalinks.enable = true;
              check-yaml.enable = true;
              end-of-file-fixer.enable = true;
              trim-trailing-whitespace.enable = true;

              nixfmt-rfc-style = {
                enable = true;
                package = pkgs.nixfmt;
              };

              chktex = {
                enable = true;
                name = "chktex";
                description = "Run chktex";
                files = "\\.(tex)$";
                entry = "${texlive}/bin/chktex";
              };
            };
          };
        };
    };
}
