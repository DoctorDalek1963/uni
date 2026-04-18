/*
1. YES
2. YES
3. YES
4. YES
5. NO
6. YES
7. NO
8. YES
9. NO
10. NO
*/

% Large parts of this code were adapted from "First-order logic and automated
% theorem proving" by Melvin Fitting

%! member(-Item, +List:list) is det.
%
%  True if Item is a member of List.

member(X, [X | _]).
member(X, [_ | Tail]) :- member(X, Tail).

%! remove(+Item, +List:list, -NewList:list) is det.
%
%  True if NewList is the result of removing all exact occurences of Item from List.

remove(_, [], []) :- !.

remove(X, [Head | Tail], NewTail) :-
	X == Head,
	remove(X, Tail, NewTail),
	!.

remove(X, [Head | Tail], [Head | NewTail]) :-
	X \== Head,
	remove(X, Tail, NewTail).

%! copy_all(+L1:list, -L2:list) is det.
%
%  Applies `copy_term/2` to all elements of L1 and produces L2.

copy_all([], []).
copy_all([H | T], [NewH | NewT]) :-
	copy_term(H, NewH),
	copy_all(T, NewT).

%! conjunctive(+Formula:compound) is det.
%
%  True if Formula is an alpha formula.

conjunctive(and(_, _)).
conjunctive(neg(or(_, _))).
conjunctive(neg(imp(_, _))).

%! disjunctive(+Formula:compound) is det.
%
%  True if Formula is a beta formula.

disjunctive(neg(and(_, _))).
disjunctive(or(_, _)).
disjunctive(imp(_, _)).

%! unary(+Formula:compound) is det.
%
%  True if Formula is a double negation or negated constant.

unary(neg(neg(_))).
unary(neg(true)).
unary(neg(false)).

%! components(+Formula:compound, -X:compound, -Y:compound) is det.
%
%  True if X and Y are the components of Formula as defined in the alpha and beta formulae tables.

components(and(X, Y), X, Y).
components(neg(and(X, Y)), neg(X), neg(Y)).
components(or(X, Y), X, Y).
components(neg(or(X, Y)), neg(X), neg(Y)).
components(imp(X, Y), neg(X), Y).
components(neg(imp(X, Y)), X, neg(Y)).

%! component(+Formula:compound, -X:compound) is det.
%
%  True if X is the component of unary Formula.

component(neg(neg(X)), X).
component(neg(true), false).
component(neg(false), true).

%! single_step(+Old:list(list(compound)), -New:list(list(compound))) is det.
%
%  True if New is the result of applying a single step of the CNF algorithm to Old.

% We use `nonvar(_)` to allow for Prolog variables without them getting unified
% with anything. Without `nonvar(_)`, `single_step([[X]], Y)` would unify X
% with forall, or a unary term, or a disjunctive term, or a conjuctive term.
% But we want it to just be a variable, so we have to check that.

% Forall
single_step([Disjunction | Rest], New) :-
	member(Formula, Disjunction),
	nonvar(Formula),
	forall(Variable, Statement) = Formula,
	var(Variable), % assertion
	remove(Formula, Disjunction, Temp),
	New = [[Statement | Temp] | Rest].

% Unary
single_step([Disjunction | Rest], New) :-
	member(Formula, Disjunction),
	nonvar(Formula),
	unary(Formula),
	component(Formula, NewFormula),
	remove(Formula, Disjunction, Temp),
	New = [[NewFormula | Temp] | Rest].

% Beta formula
single_step([Disjunction | Rest], New) :-
	member(Beta, Disjunction),
	nonvar(Beta),
	disjunctive(Beta),
	components(Beta, BetaOne, BetaTwo),
	remove(Beta, Disjunction, Temp),
	New = [[BetaOne, BetaTwo | Temp] | Rest].

% Alpha formula
single_step([Disjunction | Rest], New) :-
	member(Alpha, Disjunction),
	nonvar(Alpha),
	conjunctive(Alpha),
	components(Alpha, AlphaOne, AlphaTwo),
	remove(Alpha, Disjunction, Temp),
	New = [[AlphaOne | Temp], [AlphaTwo | Temp] | Rest].

single_step([Disjunction | Rest], [Disjunction | NewRest]) :-
	single_step(Rest, NewRest).

%! expand_to_cnf(+Conjuction:list(list(compound)), -NewConjuction:list(list(compound))) is det.
%
%  True if NewConjuction is the result of applying `single_step/2` as many
%  times as possible, starting with Conjuction.

expand_to_cnf(Conjuction, NewConjuction) :-
	single_step(Conjuction, Temp),
	expand_to_cnf(Temp, NewConjuction).

expand_to_cnf(Conjuction, Conjuction).

%! clauseform(+Formula:compound, -CNF:list(list(compound))) is det.
%
%  True if CNF is the conjunctive normal form of Formula.

clauseform(Formula, CNF) :-
	expand_to_cnf([[Formula]], Temp),
	copy_all(Temp, CNF).

%! resolutionstep(+Clauses:list(list(compound)), -NewClauses:list(list(compound))) is multi.
%
%  True if a single step of a resolution proof applied to Clauses yields NewClauses.

% TODO: This feels inefficient
resolutionstep(Clauses, NewClauses) :-
	member(D1, Clauses),
	member(D2, Clauses),
	copy_term(D1, D1Copy),
	copy_term(D2, D2Copy),
	member(X, D1Copy),
	member(neg(X), D2Copy),
	remove(X, D1Copy, NewD1),
	remove(neg(X), D2Copy, NewD2),
	append(NewD1, NewD2, NewClause),
	append(Clauses, [NewClause], NewClauses).

%! factor(+Clause:list(compound), -FactoredClause:list(compound)) is det.
%
%  True if FactoredClause is the result of unifying two unifiable literals of
%  Clause and dropping the duplicate.

factor(Clause, FactoredClause) :-
	member(X, Clause),
	member(Y, Clause),
	X \== Y,
	copy_term(Y, Z),
	X = Z,
	remove(Y, Clause, FactoredClause).

%! resolution(+Clauses:list(list(compound)), -Result:boolean) is multi.
%
%  Repeatedly applies `resolutionstep/2` and `factor/2` to a list of Clauses
%  until the empty clause is derived or until no new clauses can be derived.
%  Result is true or false, meaning whether resolution proof was successful.

% TODO
resolution(Clauses, Result).

%! clauseform_all(Formulas:list(compound), -CNF:list(list(compound))) is nondet.
%
%  Apply clauseform to all of the elements of Formulas and concatenate them into CNF.

clauseform_all([], []).
clauseform_all([Head | Tail], CNF) :-
	clauseform(Head, HeadCNF),
	clauseform_all(Tail, TailCNF),
	append(HeadCNF, TailCNF, CNF).

%! silent_test(+Premises:list(compound), +Conclusion:compound) is nondet.
%
%  Attempt resolution proof with the given Premises and Conclusion. Result is
%  `true` iff a proof exists, otherwise `false`.

silent_test(Premises, Conclusion, Result) :-
	clauseform_all(Premises, PremCNF),
	clauseform(neg(Conclusion), ConcCNF),
	append(PremCNF, ConcCNF, CNF),
	resolution(CNF, Result).

%! test(+Premises:list(compound), -Conclusion:compound) is nondet.
%
%  Exactly the same as `silent_test/2`, but instead prints "YES" or "NO".

test(Premises, Conclusion) :-
	silent_test(Premises, Conclusion, true),
	print("YES"),
	!.

test(Premises, Conclusion) :-
	silent_test(Premises, Conclusion, false),
	print("NO").

:- begin_tests(resolution_impl).

test(remove) :-
	remove(b, [a, b, c], X),
	X == [a, c].

test(remove, [fail]) :- remove(a, [a], [a]).

test(remove) :-
	remove(p(X), [p(X), q(Z), p(Y)], L),
	L == [q(Z), p(Y)].

test(remove) :-
	remove(p(X), [p(X), q(Z), p(X)], L),
	L == [q(Z)].

test(clauseform, [nondet]) :-
	clauseform(and(a, b), C),
	C == [[a], [b]].

test(clauseform, [nondet]) :-
	clauseform(and(_X, _Y), C),
	C = [[X2], [Y2]],
	X2 \== Y2.

test(clauseform, [nondet]) :-
	clauseform(or(a, b), C),
	C == [[a, b]].

test(clauseform, [nondet]) :-
	clauseform(neg(or(a, and(b, or(c, d)))), C),
	C == [[neg(a)], [neg(c), neg(b)], [neg(d), neg(b)]].

test(clauseform, [nondet]) :-
	clauseform(forall(X, neg(X)), C),
	C = [[neg(_)]].

test(clauseform, [nondet]) :-
	clauseform(forall(X, or(X, a)), C),
	C = [[_, a]].

test(clauseform, [nondet]) :-
	clauseform(forall(X, imp(p(X), q(X))), C),
	C = [[neg(p(Y)), q(Y)]].

test(clauseform, [nondet]) :-
	clauseform(forall(X, and(p(X), q(X))), C),
	C = [[p(Y)], [q(Z)]],
	Y \== Z.

test(clauseform, [nondet]) :-
	clauseform(forall(X, forall(Y, or(p(X), q(Y)))), C),
	C = [[p(X2), q(Y2)]],
	X2 \== Y2.

test(clauseform, [nondet]) :-
	clauseform(forall(X, forall(Y, and(p(X), q(Y)))), C),
	C = [[p(X2)], [q(Y2)]],
	X2 \== X,
	Y2 \== Y.

% NOTE: Do we always want to keep clauses after expansion?
test(resolutionstep, [nondet]) :-
	resolutionstep([[neg(p(X)), q(X)], [p(a)]], C),
	C == [[neg(p(X)), q(X)], [p(a)], [q(a)]].

test(resolutionstep, [nondet]) :-
	resolutionstep([[neg(p(X)), q(Y)], [p(a)]], C),
	C = [[neg(p(X)), q(Y)], [p(a)], [q(Y2)]],
	Y2 \== Y.

test(resolutionstep, [nondet]) :-
	resolutionstep([
		[neg(human(X)), mortal(X)],
		[human(socrates)],
		[neg(mortal(socrates))]
	], [
		[neg(human(X)), mortal(X)],
		[human(socrates)],
		[neg(mortal(socrates))],
		[mortal(socrates)]
	]).

test(resolutionstep, [nondet]) :-
	resolutionstep([
		[neg(human(X)), mortal(X)],
		[human(socrates)],
		[neg(mortal(socrates))]
	], [
		[neg(human(X)), mortal(X)],
		[human(socrates)],
		[neg(mortal(socrates))],
		[neg(human(socrates))]
	]).

test(factor, [nondet]) :-
	factor([p(X), q(Z), p(_Y)], C),
	C == [p(X), q(Z)].

test(factor, [nondet]) :-
	factor([neg(p(U)), neg(p(_V))], C),
	C == [neg(p(U))].

test(factor, [nondet]) :- factor(
	[p(W), p(_X), neg(p(Y)), neg(p(Z))],
	[p(W), neg(p(Y)), neg(p(Z))]
).

test(factor, [nondet]) :- factor(
	[p(W), p(X), neg(p(Y)), neg(p(_Z))],
	[p(W), p(X), neg(p(Y))]
).

:- end_tests(resolution_impl).

:- begin_tests(resolution).

/*
test(resolution, [nondet]) :-
	resolutionstep([
		[neg(human(X)), mortal(X)],
		[human(socrates)],
		[neg(mortal(socrates))]
	], true).

% TODO: Sometimes the premises don't affect the conclusion. Optimise for this.
test(silent_test) :- silent_test(
	[
		forall(X, imp(human(X), mortal(X))),
		human(socrates)
	],
	mortal(socrates),
	true
).

test(silent_test) :- silent_test(
	[
		forall(X, imp(p(X), q(X))),
		forall(X, imp(q(X), r(X))),
		p(a)
	],
	r(a),
	true
).

test(silent_test) :- silent_test(
	[forall(X, imp(p(X), q(X)))],
	q(a),
	true
).

test(silent_test) :- silent_test(
	[
		forall(X, forall(Y, imp(parent(X, Y), ancestor(X, Y)))),
		parent(alice, bob)
	],
	ancestor(alice, bob),
	true
).

test(silent_test) :- silent_test(
	[
		forall(X, imp(student(X), smart(X))),
		smart(john)
	],
	student(john),
	false
).

test(silent_test) :- silent_test(
	[
		forall(X, imp(and(p(X), q(X)), r(X))),
		p(a),
		q(a)
	],
	r(a),
	true
).

test(silent_test) :- silent_test(
	[forall(X, forall(Y, imp(loves(X, Y), knows(X, Y))))],
	knows(alice, bob),
	false
).

test(silent_test) :- silent_test(
	[
		forall(X, imp(p(X), or(q(X), r(X)))),
		p(a),
		neg(q(a))
	],
	r(a),
	true
).

test(silent_test) :- silent_test(
	[forall(X, imp(friend(X, X), trusts(X, X)))],
	trusts(alice, alice),
	false
).

test(silent_test) :- silent_test(
	[],
	% (pa -> (pb -> pc)) -> ((pa -> pb) -> pc)
	% Associativity of implication
	imp(
		imp(p(a), imp(p(b), p(c))),
		imp(imp(p(a), p(b)), p(c))
	),
	false
).
*/

:- end_tests(resolution).
