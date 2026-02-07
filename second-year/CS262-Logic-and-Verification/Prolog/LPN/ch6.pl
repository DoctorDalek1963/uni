% vim :set ft=prolog:

:- module(ch6, [doubled/1, palindrome/1, toptail/2, swapfl/2, zebra/1, sublist/2]).

:- use_module(library(clpfd)).

% === Exercise 6.1

%% doubled(+List) is det.
%
% True if the first half of List is equal to the second half.

doubled(L) :-
	length(L, Len),
	N #= div(Len, 2),
	take(L, N, FirstHalf),
	append(FirstHalf, FirstHalf, L).

take(_, 0, []) :- !.
take([X | Xs], N, [X | Ys]) :-
	M #= N - 1,
	take(Xs, M, Ys).

% === Exercise 6.2

%% palindrome(+List) is det.
%
% True if List is a palindrome.

% Doesn't work properly
% palindrome([]).
% palindrome([_]).
% palindrome([X | Xs]) :-
% 	last(Xs, X),
% 	length(Xs, Len),
% 	LenMinusOne #= Len - 1,
% 	take(Xs, LenMinusOne, Middle),
% 	palindrome(Middle).

palindrome(L) :-
	reverse(L, L).

reverse([], []).
reverse([H | T], R) :-
	reverse(T, TRev),
	append(TRev, [H], R).

% This accummulator solution doesn't allow palindrome to work backwards
% reverse(List, Rev) :-
% 	reverse_acc(List, [], Rev).

% reverse_acc([H | T], Acc, Rev) :-
% 	reverse_acc(T, [H | Acc], Rev).
% reverse_acc([], Acc, Acc).

% === Exercise 6.3

%% toptail(+List, -ShortList) is det.
%
% True when ShortList is List without the first and last elements.

toptail([_ | T], S) :-
	length(T, Len),
	SLen #= Len - 1,
	take(T, SLen, S).

% === Exercise 6.4

%% last(+List, -Element) is det.
%
% True when Element is the last element of List.

last([X], X) :- !.
last([_ | L], X) :-
	last(L, X).

% === Exercise 6.5

%% swapfl(+List1, +List2) is det.
%
% True when List2 is the same as List1 but with the first and last elements swapped.

% FIXME: Doesn't work backwards
swapfl([], []).
swapfl([X], [X]) :- !.
swapfl([X, Y], [Y, X]) :- !.
swapfl([X | Xs], [Y | Ys]) :-
	last(Xs, Y),
	last(Ys, X),
	equal_until_last(Xs, Ys).

equal_until_last([], []).
equal_until_last([_], [_]) :- !.
equal_until_last([_ | Xs], [_ | Ys]) :-
	equal_until_last(Xs, Ys).

% === Exercise 6.6

is_nat(Nat) :- member(Nat, [english, spanish, japanese]).
is_col(Col) :- member(Col, [red, blue, green]).
is_pet(Pet) :- member(Pet, [snail, jaguar, zebra]).

sublist([], _).
sublist([S | SRest], [S | LRest]) :-
	sublist(SRest, LRest).
sublist(S, [_ | LRest]) :-
	sublist(S, LRest).

street(S) :-
	S = [house(N1, C1, P1), house(N2, C2, P2), house(N3, C3, P3)],
	% This is very verbose. Can it be improved?
	house(N1, C1, P1),
	house(N2, C2, P2),
	house(N3, C3, P3),
	is_nat(N1),
	is_nat(N2),
	is_nat(N3),
	is_col(C1),
	is_col(C2),
	is_col(C3),
	is_pet(P1),
	is_pet(P2),
	is_pet(P3),
	N1 \== N2,
	N1 \== N3,
	N2 \== N3,
	C1 \== C2,
	C1 \== C3,
	C2 \== C3,
	P1 \== P2,
	P1 \== P3,
	P2 \== P3,
	sublist([house(_, _, snail), house(japanese, _, _)], S),
	sublist([house(_, _, snail), house(_, blue, _)], S).

house(english, red, Pet) :-
	is_pet(Pet).
house(spanish, Col, jaguar) :-
	is_col(Col).
house(japanese, Col, Pet) :-
	% is_nat(Nat),
	is_col(Col),
	is_pet(Pet).

zebra(Nationality) :-
	street(S),
	member(house(Nationality, _, zebra), S).

:- begin_tests(ch6).

test(doubled) :- doubled([1, 2, 3, 1, 2, 3]).
test(doubled, [fail]) :- doubled([1, 2, 3, 1, 2]).

test(palindrome) :- palindrome([]).
test(palindrome) :- palindrome([1, 2, 2, 1]).
test(palindrome) :- palindrome([1, 2, 3, 2, 1]).

test(toptail) :- toptail([1, 2, 3, 4], [2, 3]).
test(toptail) :- toptail([1, 2], []).
test(toptail, [fail]) :- toptail([1], _).
test(toptail, [fail]) :- toptail([], _).

test(last) :- last([1, 2, 3, 4], 4).
test(last, [fail]) :- last([], _).

test(swapfl) :- swapfl([1, 2], [2, 1]).
test(swapfl) :- swapfl([1, 2, 3, 4], [4, 2, 3, 1]).

% It's okay to be nondet because there are multiple valid street
% configurations, but all five japanese as the answer
test(zebra, [nondet]) :-
	zebra(X),
	X == japanese.

:- end_tests(ch6).
