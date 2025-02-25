#!/usr/bin/env python3

from math import pi
from scipy.integrate import tplquad


print(
    "SciPy gives:",
    tplquad(
        lambda _z, r, _t: r,
        0,
        2 * pi,
        0,
        1,
        lambda _t, r: r * r,
        lambda _t, r: 3 - 2 * r,
    )
)

print("Actual answer:", 7 * pi / 6)
