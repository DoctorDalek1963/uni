#!/usr/bin/env python3

from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np


def DO_THING():
    plt.savefig(
        Path(__file__).parent.parent / "imgs" / "FILENAME.png",
        bbox_inches="tight",
        pad_inches=0.2,
    )
    plt.clf()


def main() -> None:
    DO_THING()


if __name__ == "__main__":
    main()
