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

%! conjunctive(+Formula:compound) is det.
%
%  True if Formula is an alpha formula.

conjunctive(and(_, _)).
conjunctive(neg(or(_, _))).
conjunctive(neg(imp(_, _))).

%! disjunctive(+Formula:compound) is det.
%
%  True if Formula is an beta formula.

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

%! clauseform(+Formula:compound, -CNF:list(list(compound))) is det.
%
%  True if CNF is the conjunctive normal form of Formula.

clauseform(Formula, CNF).

%! resolutionstep(+Clauses:list(list(compound)), -NewClauses:list(list(compound))) is multi.
%
%  True if a single step of a resolution proof applied to Clauses yields NewClauses.

resolutionstep(Clauses, NewClauses).

%! factor(+Clause:list(compound), -FactoredClause:list(compound)) is det.
%
%  True if FactoredClause is the result of unifying two unifiable literals of
%  Clause and dropping the duplicate.

factor(Clause, FactoredClause).

%! resolution(+Clauses:list(list(compound)), -Result:boolean) is multi.
%
%  Repeatedly applies `resolutionstep/2` and `factor/2` to a list of Clauses
%  until the empty clause is derived or until no new clauses can be derived.
%  Result is true or false, meaning whether resolution proof was successful.

resolution(Clauses, Result).

%! silent_test(+Premises:list(compound), +Conclusion:compound) is nondet.
%
%  True if Conclusion can be reached from Premises using resolution refutation.

silent_test(Premises, Conclusion).

%! test(+Premises:list(compound), -Conclusion:compound) is nondet.
%
%  Exactly the same as `silent_test/2`, but instead prints "YES" or "NO".

test(Premises, Conclusion).

:- begin_tests(resolution).

test(clauseform) :-
	clauseform(forall(X, imp(p(X), q(X))), C),
	C == [[neg(p(X)), q(X)]].

% NOTE: Do we always want to keep clauses after expansion?
test(resolutionstep) :-
	resolutionstep([[neg(p(X)), q(X)], [p(a)]], C),
	C == [[neg(p(X)), q(X)], [p(a)], [q(a)]].

test(factor) :-
	factor([p(X), q(Z), p(Y)], C),
	C == [p(X), q(Z)].

% NOTE: These are assigned test cases. Some of them will fail! I just don't yet know which.
% TODO: Sometimes the premises don't affect the conclusion. Optimise for this.
test(silent_test) :- silent_test(
	[
		forall(X, imp(human(X), mortal(X))),
		human(socrates)
	],
	mortal(socrates)
).

test(silent_test) :- silent_test(
	[
		forall(X, imp(p(X), q(X))),
		forall(X, imp(q(X), r(X))),
		p(a)
	],
	r(a)
).

test(silent_test) :- silent_test(
	[forall(X, imp(p(X), q(X)))],
	q(a)
).

test(silent_test) :- silent_test(
	[
		forall(X, forall(Y, imp(parent(X, Y), ancestor(X, Y)))),
		parent(alice, bob)
	],
	ancestor(alice, bob)
).

test(silent_test, [fail]) :- silent_test(
	[
		forall(X, imp(student(X), smart(X))),
		smart(john)
	],
	student(john)
).

test(silent_test) :- silent_test(
	[
		forall(X, imp(and(p(X), q(X)), r(X))),
		p(a),
		q(a)
	],
	r(a)
).

test(silent_test, [fail]) :- silent_test(
	[forall(X, forall(Y, imp(loves(X, Y), knows(X, Y))))],
	knows(alice, bob)
).

test(silent_test) :- silent_test(
	[
		forall(X, imp(p(X), or(q(X), r(X)))),
		p(a),
		neg(q(a))
	],
	r(a)
).

test(silent_test, [fail]) :- silent_test(
	[forall(X, imp(friend(X, X), trusts(X, X)))],
	trusts(alice, alice)
).

test(silent_test, [fail]) :- silent_test(
	[],
	% (pa -> (pb -> pc)) -> ((pa -> pb) -> pc)
	% Associativity of implication
	imp(
		imp(p(a), imp(p(b), p(c))),
		imp(imp(p(a), p(b)), p(c))
	)
).

:- end_tests(resolution).
