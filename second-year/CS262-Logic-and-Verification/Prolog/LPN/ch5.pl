% vim :set ft=prolog:

:- module(ch5, [increment/2, my_sum/3, addone/2]).

:- use_module(library(clpfd)).

% === Exercise 5.2

increment(X, Y) :-
	Y #= X + 1.

my_sum(X, Y, Z) :-
	Z #= X + Y.

% === Exercise 5.3

addone([], []).
addone([H | T], [IH | IT]) :-
	increment(H, IH),
	addone(T, IT).

:- begin_tests(ch5).

test(increment) :- increment(4, 5).
test(increment, [fail]) :- increment(4, 6).

test(my_sum) :- my_sum(2, 3, 5).
test(my_sum) :-
	my_sum(X, 3, 5),
	X == 2.

test(addone) :- addone([1, 2, 3], [2, 3, 4]).
test(addone) :-
	addone([1, 2, 3], X),
	X == [2, 3, 4].
test(addone) :-
	addone(X, [1, 2, 3]),
	X == [0, 1, 2].

:- end_tests(ch5).
