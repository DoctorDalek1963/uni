% vim :set ft=prolog:

:- module(ch6, [zebra/1, sublist/2]).

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
	sublist([house(_, _, snail), house(japanese, _, _)], S),
	sublist([house(_, _, snail), house(_, blue, _)], S).


house(english, red, Pet) :-
	is_pet(Pet).
house(spanish, Col, jaguar) :-
	is_col(Col).
house(Nat, Col, Pet) :-
	is_nat(Nat),
	is_col(Col),
	is_pet(Pet).

zebra(Nationality) :-
	street(S),
	member(house(Nationality, _, zebra), S).
