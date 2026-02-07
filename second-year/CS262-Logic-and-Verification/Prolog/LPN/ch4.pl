% vim :set ft=prolog:

:- module(ch4, [second/2, swap12/2, listtran/2, twice/2]).

% === Exercise 4.3

second(X, [_, X | _]).

% === Exercise 4.4

swap12([X, Y | T], [Y, X | T]).

% === Exercise 4.5

tran(eins, one).
tran(zwei, two).
tran(drei, three).
tran(vier, four).
tran(fuenf, five).
tran(sechs, six).
tran(sieben, seven).
tran(acht, eight).
tran(neun, nine).

listtran([], []).
listtran([GH | GT], [EH | ET]) :-
	tran(GH, EH),
	listtran(GT, ET).

% === Exercise 4.6

twice([], []).
twice([H | T], [H, H | TT]) :-
	twice(T, TT).

:- begin_tests(ch4).

test(second) :- second(2, [1, 2, 3]).
test(second, [fail]) :- second(_, [_]).

test(swap12) :- swap12([1, 2, 3, 4], [2, 1, 3, 4]).
test(swap12) :- swap12([1, 2], [2, 1]).
test(swap12, [fail]) :- swap12([1], _).

test(listtran) :- listtran([eins, zwei, drei, vier], [one, two, three, four]).

test(twice) :- twice([1, 2, 3], [1, 1, 2, 2, 3, 3]).

:- end_tests(ch4).
