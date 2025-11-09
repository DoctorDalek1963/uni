#!/usr/bin/env python

import bisect

towers = [
    50,
    18,
    10,
    30,
    5,
    30,
    15,
    26,
    18,
    100,
    12,
    15,
    2,
    25,
    100_000,
    20,
    29,
    31,
    1,
]
n = len(towers)


def D(i):
    l = towers[i]
    count = 1
    j = i + 1
    while j < n:
        if towers[j] < l:
            count += 1
        j += 1
    return count


brute_force = [D(i) for i in range(n)]
print(brute_force)


def clever_with_one_drop():
    d = [1] * n

    for i in range(n - 1):
        if towers[i] > towers[i + 1]:
            k = i
            break

    j = n - 1
    i = k

    while i >= 0:
        while towers[j] >= towers[i] and j > k:
            j -= 1

        d[i] = 1 + j - k
        i -= 1

    return d


# print(clever_with_one_drop())


def clever_general(A: list[int]):
    d = [1] * n

    def populate_d(l, r):
        if l == r:
            return

        m = (l + r) // 2
        populate_d(l, m)
        populate_d(m + 1, r)

        # print(l, m, r)

        right_half = [A[i] for i in range(m + 1, r + 1)]
        right_half.sort()
        # print(right_half)

        for i in range(l, m + 1):
            # print(i, d[i])
            d[i] += bisect.bisect_left(right_half, A[i])
            # print(i, d[i])

        # print()

    populate_d(0, n - 1)
    return d


print(clever_general(towers))
