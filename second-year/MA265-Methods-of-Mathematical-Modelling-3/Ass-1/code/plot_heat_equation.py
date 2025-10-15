#!/usr/bin/env python3

from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np

k = 0.5


def u(x, t):
    return np.exp(-(x**x) / (4 * k * t)) / np.sqrt(4 * np.pi * k * t)


def plot_heat_equation():
    x = np.linspace(0, 5, num=1000)
    t = np.linspace(0.01, 5, num=1000)

    X, T = np.meshgrid(x, t)

    fig = plt.figure()
    ax = fig.add_subplot(projection="3d")
    ax.set_box_aspect((1, 1, 1))
    ax.view_init(elev=30, azim=-45)

    ax.plot_surface(X, T, u(X, T), alpha=0.7, cmap="rainbow")
    ax.set_xlabel("x")
    ax.set_ylabel("t")
    ax.set_zlabel("u")

    plt.savefig(
        Path(__file__).parent.parent / "imgs" / "Q2.png",
        bbox_inches="tight",
        pad_inches=0.2,
    )
    plt.clf()


def main() -> None:
    plot_heat_equation()


if __name__ == "__main__":
    main()
