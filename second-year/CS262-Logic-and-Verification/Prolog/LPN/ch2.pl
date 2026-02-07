% vim :set ft=prolog:

:- module(ch1, [crossword/6]).

% === Exercise 2.4

word(astante, a, s, t, a, n, t, e).
word(astoria, a, s, t, o, r, i, a).
word(baratto, b, a, r, a, t, t, o).
word(cobalto, c, o, b, a, l, t, o).
word(pistola, p, i, s, t, o, l, a).
word(statale, s, t, a, t, a, l, e).

crossword(A, B, C, D, E, F) :-
	word(A, _, A2, _, A4, _, A6, _),
	word(B, _, B2, _, B4, _, B6, _),
	word(C, _, C2, _, C4, _, C6, _),
	word(D, _, D2, _, D4, _, D6, _),
	word(E, _, E2, _, E4, _, E6, _),
	word(F, _, F2, _, F4, _, F6, _),
	A2 == D2,
	B2 == D4,
	C2 == D6,
	A4 == E2,
	B4 == E4,
	C4 == E6,
	A6 == F2,
	B6 == F4,
	C6 == F6,
	A \== B,
	A \== C,
	A \== D,
	A \== E,
	A \== F,
	B \== C,
	B \== D,
	B \== E,
	B \== F,
	C \== D,
	C \== E,
	C \== F,
	D \== E,
	D \== F,
	E \== F.
