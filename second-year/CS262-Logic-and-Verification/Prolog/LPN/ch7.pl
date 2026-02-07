% vim :set ft=prolog:

:- module(ch7, [anbn_no_empty/2, anb2n/2]).

% === Exercise 7.2

anbn_no_empty --> a, b.
anbn_no_empty --> a, anbn_no_empty, b.
a --> [a].
b --> [b].

% === Exercise 7.3

anb2n --> [].
anb2n --> a, anb2n, b, b.

:- begin_tests(ch7).

test(anbn_no_empty, [fail]) :- anbn_no_empty([], []).
test(anbn_no_empty, [nondet]) :- anbn_no_empty([a, a, b, b], []).

test(anb2n, [nondet]) :- anb2n([], []).
test(anb2n, [nondet]) :- anb2n([a, a, b, b, b, b], []).

:- end_tests(ch7).
