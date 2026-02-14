% vim :set ft=prolog:

:- module(ch8, []).

s(s(Np, Vp)) --> np(Np, subject, Plur), vp(Vp, Plur).

np(np(Det, N), _Pos, Plur) --> det(Det, Plur), n(N, Plur).
np(np(N), _Pos, plural) --> n(N, plural).
np(np(Pro), Pos, Plur) --> pro(Pro, Pos, Plur).

vp(vp(V, NP), Plur) --> v(V, Plur), np(NP, object, _).
vp(vp(V), Plur) --> v(V, Plur).

det(det(Word), Plur) -->
	[Word],
	{lex(Word, det, _, Plur)}.

n(n(Word), Plur) -->
	[Word],
	{lex(Word, noun, _, Plur)}.

pro(pro(Word), Pos, Plur) -->
	[Word],
	{lex(Word, pronoun, Pos, Plur)}.

v(v(Word), Plur) -->
	[Word],
	{lex(Word, verb, _, Plur)}.

lex(the, det, _, _).
lex(a, det, _, singular).

lex(man, noun, _, singular).
lex(woman, noun, _, singular).
lex(men, noun, _, plural).
lex(women, noun, _, plural).
lex(apple, noun, _, singular).
lex(apples, noun, _, plural).
lex(pear, noun, _, singular).
lex(pears, noun, _, plural).

lex(he, pronoun, subject, singular).
lex(she, pronoun, subject, singular).
lex(they, pronoun, subject, plural).
lex(it, pronoun, _, singular).
lex(him, pronoun, object, singular).
lex(her, pronoun, object, singular).
lex(them, pronoun, object, plural).

% Plurality applies to subject
lex(shoot, verb, _, plural).
lex(shoots, verb, _, singular).
lex(eat, verb, _, plural).
lex(eats, verb, _, singular).

:- begin_tests(ch8).

test(dcg_plurals, [nondet]) :- s(_, [the, men, eat], []).
test(dcg_plurals, [nondet]) :- s(_, [the, man, eats], []).
test(dcg_plurals, [nondet]) :- s(_, [a, woman, shoots, him], []).
test(dcg_plurals, [nondet]) :- s(_, [they, eat, the, women], []).
test(dcg_plurals, [nondet]) :- s(_, [it, eats], []).
test(dcg_plurals, [nondet]) :- s(_, [women, eat, them], []).

test(dcg_plurals, [fail]) :- s(_, [the, man, eat], []).
test(dcg_plurals, [fail]) :- s(_, [the, men, eats], []).
test(dcg_plurals, [fail]) :- s(_, [a, women, shoots, him], []).
test(dcg_plurals, [fail]) :- s(_, [them, eats, the, women], []).
test(dcg_plurals, [fail]) :- s(_, [it, eat], []).
test(dcg_plurals, [fail]) :- s(_, [woman, eat, them], []).

:- end_tests(ch8).
