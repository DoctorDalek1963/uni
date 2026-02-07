% vim :set ft=prolog:

:- module(NAME, []).

thing().

:- begin_tests(NAME).

test(thing) :- !.

:- end_tests(NAME).
