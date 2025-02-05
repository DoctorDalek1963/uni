#!/usr/bin/env python3

from pathlib import Path
import numpy as np
import matplotlib.pyplot as plt


def f(x, y):
    return np.exp(x) + y**3 - 2 * x - 3 * y


def plot_point(ax, coords, name):
    x, y = coords
    ax.plot(x, y, f(x, y), "o")
    ax.text(x, y, f(x, y) + 1, name)


def plot_3d_from_angle(filename, *, elev, azim):
    x = np.linspace(-2.5, 2.5)
    y = np.linspace(-2.5, 2.5)

    X, Y = np.meshgrid(x, y)

    fig = plt.figure()
    ax = fig.add_subplot(projection="3d")
    ax.set_box_aspect((1, 1, 1))
    ax.view_init(elev=elev, azim=azim)

    ax.plot_surface(X, Y, f(X, Y), alpha=0.7, cmap="rainbow")
    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_zlabel("z")

    # Plot point P
    plot_point(ax, (0, 2), "P")

    # Plot critical points
    plot_point(ax, (np.log(2), 1), "m")
    plot_point(ax, (np.log(2), -1), "s")

    plt.savefig(
        Path(__file__).parent.parent / "imgs" / f"{filename}.png",
        bbox_inches="tight",
        pad_inches=0.2,
    )
    plt.clf()


def main() -> None:
    plot_3d_from_angle("Q2d-1", elev=45, azim=-60)
    plot_3d_from_angle("Q2d-2", elev=35, azim=-120)


if __name__ == "__main__":
    main()
