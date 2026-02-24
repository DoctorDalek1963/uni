% vim :set ft=prolog:

/*
TODO: Put actual answers here
1. NO
2. NO
3. NO
4. NO
5. NO
6. NO
7. NO
8. NO
9. NO
10. NO
*/

:- op(140, fy, neg).
:- op(160, yfx, [and, or, imp, revimp, uparrow, downarrow, notimp, notrevimp]).
:- op(180, yfx, [equiv, notequiv]). % TODO: Implement

%! conjunctive(+Formula) is det.
%
%  True if Formula is an alpha formula.

conjunctive(_ and _).
conjunctive(neg(_ or _)).
conjunctive(neg(_ imp _)).
conjunctive(neg(_ revimp _)).
conjunctive(neg(_ uparrow _)).
conjunctive(_ downarrow _).
conjunctive(_ notimp _).
conjunctive(_ notrevimp _).

%! disjunctive(+Formula) is det.
%
%  True if Formula is an beta formula.

disjunctive(neg(_ and _)).
disjunctive(_ or _).
disjunctive(_ imp _).
disjunctive(_ revimp _).
disjunctive(_ uparrow _).
disjunctive(neg(_ downarrow _)).
disjunctive(neg(_ notimp _)).
disjunctive(neg(_ notrevimp _)).

%! unary(+Formula) is det.
%
%  True if Formula is a double negation or negated constant.

unary(neg neg _).
unary(neg true).
unary(neg false).

%! components(+Formula, -X, -Y) is det.
%
%  True if X and Y are the components of Formula as defined in the alpha and beta formulae tables.

components(X and Y, X, Y).
components(neg(X and Y), neg X, neg Y).
components(X or Y, X, Y).
components(neg(X or Y), neg X, neg Y).
components(X imp Y, neg X, Y).
components(neg(X imp Y), X, neg Y).
components(X revimp Y, X, neg Y).
components(neg(X revimp Y), neg X, Y).
components(X uparrow Y, neg X, neg Y).
components(neg(X uparrow Y), X, Y).
components(X downarrow Y, neg X, neg Y).
components(neg(X downarrow Y), X, Y).
components(X notimp Y, X, neg Y).
components(neg(X notimp Y), neg X, Y).
components(X notrevimp Y, neg X, Y).
components(neg(X notrevimp Y), X, neg Y).

%! component(+Formula, -X) is det.
%
%  True if X is the component of unary Formula.

component(neg neg X, X).
component(neg true, false).
component(neg false, true).

%! clauseform(+Formula, -CNF) is det.
%
%  True if CNF is the conjunctive normal form of Formula.

clauseform(Formula, CNF).

%! resolve(+Premises, +Consequence) is nondet.
%
%  True if Consequence is a direct logical result of the Premises.

resolve(Prems, Conseq).

%! test(+Premises, +Consequence) is nondet.
%
%  Like `resolve/2` but also prints "YES" or "NO" respectively.

test(Prems, Conseq) :-
	resolve(Prems, Conseq).

:- begin_tests(resolution).

% test(clauseform) :-
% 	clauseform(a imp b, Y),
% 	Y == [[neg a, b]].
% test(clauseform) :-
% 	clauseform(neg (a uparrow b), Y),
% 	Y == [[a], [b]].

% NOTE: These are assigned test cases. Some of them will fail! I just don't yet know which.
% test(resolve) :- resolve([x imp y, x], y).
% test(resolve) :- resolve([neg x imp y], neg (x notimp y) imp y).
% test(resolve) :- resolve([x imp y, y imp z], neg neg z or neg x).
% test(resolve) :- resolve([], (x imp (y imp z)) equiv ((x imp y) imp z)).
% test(resolve) :- resolve([x notequiv y], y notequiv x).
% test(resolve) :- resolve([], (x notequiv (y notequiv z)) equiv ((x notequiv y) notequiv z)).
% test(resolve) :- resolve([neg (x uparrow y)], neg x downarrow neg y).
% test(resolve) :- resolve([(z notrevimp u) or (u uparrow neg v)], neg (neg x revimp neg y)). % NOTE: Prems don't affect conseq. Optimise for this.
% test(resolve) :- resolve([x or y, neg (neg y notrevimp z)], neg neg (z equiv x) notrevimp y).
% test(resolve) :- resolve([neg (z notrevimp y) revimp x], (x or w) imp ((y imp z) or w)).

:- end_tests(resolution).
