% vim :set ft=prolog:

:- module(p05, [my_reverse/2]).

%% my_reverse(-Reversed, +List) is det.
%% my_reverse(+Reversed, -List) is det.
%
% Reverses a list.

my_reverse([], []).
my_reverse([X | L], L2) :-
	my_reverse(L, ReversedL),
	my_append(ReversedL, [X], L2).

%% my_append(+L1, +L2, -Joined) is det
%
% _Joined_ is the concatenation of _L1_ and _L2_.

my_append([], L, L).
my_append([H | T], L2, [H | L3]) :- my_append(T, L2, L3).

%% without_last(+List, -ShorterList) is det.
%
% Remove the last element of the list in the first argument.

without_last([_], []) :- !.
without_last([X | L], [X | L2]) :- without_last(L, L2).

:- begin_tests(p05).

test(my_reverse) :- my_reverse([a, b, c], [c, b, a]).
test(my_reverse) :- my_reverse([a, 1, b, 4, d, 51, s], [s, 51, d, 4, b, 1, a]).

test(without_last) :- without_last([1, 2, 3], [1, 2]).

:- end_tests(p05).
