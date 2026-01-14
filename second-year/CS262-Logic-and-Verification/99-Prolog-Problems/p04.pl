% vim :set ft=prolog:

:- module(p04, [num_elements/2]).

%% num_elements(-Count, +List) is det.
%
% Counts the number of elements in a list.

num_elements(0, []).
num_elements(X, [_ | L]) :-
	num_elements(Y, L),
	X is Y + 1.

:- begin_tests(p04).

test(num_elements) :- num_elements(3, [a, b, c]).
test(num_elements) :- num_elements(10, [a, b, c, d, e, f, g, h, i, j]).

:- end_tests(p04).
