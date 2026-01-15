% vim :set ft=prolog:

:- module(p07, [my_flatten/2]).

%% my_flatten(+List, -FlatList) is det.
%
% _FlatList_ is the flattened version of _List_.

my_flatten([], []).

:- begin_tests(p07).

test(my_flatten) :- my_flatten([a, [b, [c, d], e]], [a, b, c, d, e]).
test(my_flatten) :- my_flatten([a, [b, [[[[[c]]]], d], e]], [a, b, c, d, e]).

:- end_tests(p07).
