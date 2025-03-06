#!/usr/bin/env python3

from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np


def main() -> None:
    """Plot the torus."""
    N = 100
    u = np.linspace(0, 2 * np.pi, N)
    v = np.linspace(0, 2 * np.pi, N)

    U, V = np.meshgrid(u, v)

    fig = plt.figure(figsize=(6, 6))
    ax = fig.add_subplot(projection="3d")
    ax.set_box_aspect((1, 1, 1))
    ax.set_aspect("equal")

    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("z")

    ax.set_xlim(-3, 3)
    ax.set_ylim(-3, 3)
    ax.set_zlim(-3, 3)

    ax.plot_surface(
        (2 + np.cos(U)) * np.cos(V),
        (2 + np.cos(U)) * np.sin(V),
        np.sin(U),
        alpha=0.7,
        cmap="RdYlGn",
    )

    plt.savefig(Path(__file__).parent.parent / "imgs" / "Q3a-torus.png")
    plt.clf()


if __name__ == "__main__":
    main()
