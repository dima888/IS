:-consult(schaeferClan).

:-begin_tests(ancestor).
test(ancestor) :- ancestor(leo, dima).
test(ancestor, [fail]) :- ancestor(leo, baer).
test(ancestor_from) :- findall(X, ancestor(leo, X), Result), assertion(Result ==
[victoria,vironica,christina,valeria,anatoli_2,swetlana,dima,alexander,tatjana,waldimar_2,natalie_2,nicole,mark,ivan]).
:-end_tests(ancestor).

:-begin_tests(grandpa).
test(grandpa_true) :- grandpa(eugen, ivan).
test(grandpa_false, [fail]) :- grandpa(leo, atom).
:-end_tests(grandpa).

:-begin_tests(grandma).
test(grandma_true) :- grandma(erna, dima).
test(grandma_false, [fail]) :- grandma(erna, atom).
:-end_tests(grandma).


:-begin_tests(father).
test(father_true) :- findall(X, father(X, dima), Result), assertion(Result == [eugen]).
test(fater_from) :- findall(X, father(eugen, X), Result), assertion(Result == [anatoli_2, swetlana, dima, spiderman, catwoman]).
test(father_false, [fail]) :- father(swetlana, _X).
:-end_tests(father).

:-begin_tests(mother).
test(mother_from) :- findall(X, mother(lidia, X), Result), assertion(Result == [anatoli_2, swetlana, dima]).
test(mother_true) :- mother(lidia, dima).
test(mother_false, [fail]) :- mother(lidia, alexander).
:-end_tests(mother).

:-begin_tests(uncle).
test(uncle_from) :- findall(X, uncle(jakob, X), Result),  assertion(Result == [anatoli_2, swetlana, dima, alexander, tatjana, waldimar_2, natalie_2]).
test(uncle_true) :- uncle(jakob, dima), !.
test(uncle_false, [fail]) :- uncle(jakob, lidia).
:-end_tests(uncle).

:-begin_tests(aunt).
test(aunt_from) :- findall(X, aunt(maria, X), Result), assertion(Result == [victoria, vironica, christina, anatoli_2, swetlana, dima, alexander,
									  tatjana, waldimar_2, natalie_2]).
test(aunt_true) :- aunt(maria, dima), !.
test(aunt_false, [fail]) :- aunt(maria, robo).
:-end_tests(aunt).

:-begin_tests(brother).
test(brother_from) :-  findall(X, brother(dima, X), Result), assertion(Result == [anatoli_2, swetlana]).
test(brother_false) :- findall(_X, brother(swetlana, dima), Result),  assertion(Result == []).
:-end_tests(brother).

:-begin_tests(sister).
test(sister_from) :- findall(X, sister(swetlana, X), Result), assertion(Result == [anatoli_2, dima]).
test(sister_false) :- findall(_X, sister(dima, swetlana), Result), assertion(Result == []).
:-end_tests(sister).

% Run Test -> run_tests(brother).
