#!/usr/bin/env python3

from pathlib import Path
import numpy as np
import matplotlib.pyplot as plt


def generate_plot(r: float, title_text: str, file_number: int) -> None:
    """Generate the plot for given radius."""
    theta = np.linspace(0, 10 * np.pi, 100_000)
    x = (1 - r) * np.cos(theta) + r * np.cos((1 - r) / r * theta)
    y = (1 - r) * np.sin(theta) - r * np.sin((1 - r) / r * theta)

    plt.figure(figsize=(6, 6))
    plt.plot(np.cos(theta), np.sin(theta))
    plt.plot(x, y)
    plt.title(f"Hypocycloid with r = {title_text}")
    plt.xlim(-1.1, 1.1)
    plt.ylim(-1.1, 1.1)

    plt.savefig(Path(__file__).parent.parent / "imgs" / f"Q3a-{file_number}.png")
    plt.clf()


def main() -> None:
    """Generate the plots for the necessary radii."""
    for r, title_text, file_number in [
        (1 / 2, "1/2", 1),
        (1 / 4, "1/4", 2),
        (2 / 3, "2/3", 3),
        (1 / np.sqrt(2), "1/sqrt(2)", 4),
    ]:
        generate_plot(r, title_text, file_number)


if __name__ == "__main__":
    main()
