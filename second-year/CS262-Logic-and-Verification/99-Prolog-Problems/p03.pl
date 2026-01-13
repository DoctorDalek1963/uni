% vim :set ft=prolog:

%% element_at(-Elem, +List, +Index) is det.
%
% Finds the element at the desired index of the given list. Indices start at 1.

element_at(X, [X | _], 1) :- !.
element_at(X, [_ | L], N) :- M is N - 1, element_at(X, L, M).

:- begin_tests(p03).

test(element_at) :- element_at(c, [a, b, c, d], 3).
test(element_at) :- element_at(a, [a, b, c, d], 1).
test(element_at, [fail]) :- element_at(_, [a, b, c, d], 0).
test(element_at, [fail]) :- element_at(_, [a, b, c, d], 5).

:- end_tests(p03).
