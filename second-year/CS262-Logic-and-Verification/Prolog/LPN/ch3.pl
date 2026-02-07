% vim :set ft=prolog:

:- module(ch3, [in/2, trainFromTo/2, greater_than/2, swap/2]).

% === Exercise 3.2

directlyIn(katarina, olga).
directlyIn(olga, natasha).
directlyIn(natasha, irina).

in(X, Y) :-
	directlyIn(X, Y),
	!.
in(X, Y) :-
	directlyIn(X, Z),
	in(Z, Y).

% === Exercise 3.3

directTrain(saarbruecken, dudweiler).
directTrain(forbach, saarbruecken).
directTrain(freyming, forbach).
directTrain(stAvold, freyming).
directTrain(fahlquemont, stAvold).
directTrain(metz, fahlquemont).
directTrain(nancy, metz).

trainFromTo(X, Y) :-
	directTrain(X, Y),
	!.
trainFromTo(X, Y) :-
	directTrain(X, Z),
	trainFromTo(Z, Y).

% === Exercise 3.4

greater_than(succ(_), 0).
greater_than(succ(X), succ(Y)) :-
	greater_than(X, Y).

% === Exercise 3.5

swap(leaf(X), leaf(X)).
swap(tree(X, Y), tree(SY, SX)) :-
	swap(X, SX),
	swap(Y, SY).

:- begin_tests(ch3).

test(in) :- in(katarina, natasha).
test(in, [fail]) :- in(olga, katarina).

test(trainFromTo) :- trainFromTo(nancy, saarbruecken).
test(trainFromTo) :- trainFromTo(stAvold, dudweiler).
test(trainFromTo, [fail]) :- trainFromTo(forbach, metz).

test(greater_than) :- greater_than(succ(succ(0)), 0).
test(greater_than) :- greater_than(succ(succ(0)), succ(0)).
test(greater_than, [fail]) :- greater_than(succ(succ(0)), succ(succ(0))).

test(swap) :- swap(tree(tree(leaf(1), leaf(2)), leaf(4)), tree(leaf(4), tree(leaf(2), leaf(1)))).

:- end_tests(ch3).
