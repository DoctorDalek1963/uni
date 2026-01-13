% vim :set ft=prolog:

%% my_last(-Elem, +List) is det.
%
% Finds the last element of a list.

my_last(X, [X]) :- !.
my_last(X, [_ | L]) :- my_last(X, L).

:- begin_tests(p01).

test(my_last) :- my_last(d, [a, b, c, d]).
test(my_last, [fail]) :- my_last(a, [a, b, c, d]).
test(my_last, [fail]) :- my_last(e, [a, b, c, d]).

:- end_tests(p01).
