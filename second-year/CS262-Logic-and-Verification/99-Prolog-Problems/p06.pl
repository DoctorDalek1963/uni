% vim :set ft=prolog:

:- module(p06, [palindrome/1]).

%% palindrome(+List) is det.
%
% Checks if the list is a palindrome.

palindrome(X) :- p05:my_reverse(X, X).

:- begin_tests(p06).

test(palindrome) :- palindrome([]).
test(palindrome) :- palindrome([1, 2, 2, 1]).
test(palindrome) :- palindrome([1, 2, 3, 2, 1]).

:- end_tests(p06).
