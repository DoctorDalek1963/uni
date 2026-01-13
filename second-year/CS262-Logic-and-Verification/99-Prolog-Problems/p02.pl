% vim :set ft=prolog:

%% penultimate(-Elem, +List) is det.
%
% Finds the penultimate element of a list.

penultimate(X, [X, _]) :- !.
penultimate(X, [_ | L]) :- penultimate(X, L).

:- begin_tests(p02).

test(penultimate) :- penultimate(c, [a, b, c, d]).
test(penultimate, [fail]) :- penultimate(a, [a, b, c, d]).
test(penultimate, [fail]) :- penultimate(e, [a, b, c, d]).

:- end_tests(p02).
