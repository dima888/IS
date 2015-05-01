:-consult(definiteClauseGrammar).


:-begin_tests(dcg).

test(ergaenzungsfrage) :-
	s(Semantik, Struktur, [wer, ist, der, onkel, von, dima], []),
	Semantik.

test(ergaenzungsfrage, [fail]) :-
	s(Semantik, Struktur, [wer, ist, die, onkel, von, dima], []),
	Semantik.

test(entscheidungsfrage) :-
	s(Semantik, Struktur, [ist, maria, die, tante, von, dima], []),
	Semantik.

test(entscheidungsfrage, [fail]) :-
	s(Semantik, Struktur, [ist, champieMamp, die, tante, von, dima], []),
	Semantik.


:-end_tests(dcg).


% Run Test -> run_tests(dcg).
