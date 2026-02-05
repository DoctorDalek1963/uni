% vim :set ft=prolog:

% :- module(hanoi, [hanoi/4]).

:- use_module(library(clpfd)).

% hanoi(N, From, To, Store) :-
% 	range(N, L),
% 	hanoi_move(L, [], X, Y).

%% range(+N, -L) is det.
%
% L is the list [1, 2, ..., N].

range(N, L) :-
	length(L, N),
	counting(L, 1).

counting([], _).
counting([X | L], X) :-
	Y #= X + 1,
	counting(L, Y).

%% hanoi_move(+FromStack, +ToStack, -NewFromStack, -NewToStack) is det.

hanoi_move([F | FRest], [T | TRest], FRest, [F | [T | TRest]]) :-
	F #< T.
