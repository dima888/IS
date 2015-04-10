%% Familienstammbaum einbinden
:-consult('../aufgabe1/schaeferClan.pl').
:-consult('../lexikon.pl').

% wiki bespiel
%sentence --> noun_phrase, verb_phrase, kuh.
% noun_phrase --> det, noun.
% verb_phrase --> verb, noun_phrase.
% det --> [the].
% det --> [a].
% noun --> [cat].
% noun --> [bat].
% verb --> [eats].
% kuh --> [kuh].

%%	 DAS SIND HARD CODIERTE FRAGEN ZUM TESTEN FUER UNS
frage([Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname]) :-
	        ergaenzungsfragen(Nomen, Eigenname, [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname], []),
		uncle(Nomen, Eigenname).

frage([Interrogativpronomen, Verb, Nomen, Praeposition, Eigenname]) :-
		ergaenzungsfragen(Nomen, Eigenname, [Interrogativpronomen, Verb, Nomen, Praeposition, Eigenname], []),
		uncle(Nomen, Eigenname).

frage([Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2]) :-
	entscheidungsfragen(Eigenname_1, Nomen, Eigenname_2, [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2], []),
	aunt(Eigenname_1, Eigenname_2).

%%	[wer,ist,der,onkel,von,kevin] v [wer,ist,onkel,von,kevin]
s(Semantik, Struktur, [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname], []) :-
	 ergaenzungsfragen(Nomen, Eigenname, [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname], []),
	 uncle(Onkel, Eigenname),
	 Semantik = uncle(Onkel, Eigenname),
	 is(Struktur, 7*7).

%%	[ist,martha,die,tante,von,kevin]  v [ist,martha,eine,tante,von,kevin]
s(Semantik, Struktur, [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2], []) :-
	entscheidungsfragen(Eigenname_1, Nomen, Eigenname_2, [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2], []),
	Semantik = aunt(Eigenname_1, Eigenname_2),
	is(Struktur, 10123).

%%	Define Clause Grammar / DCG
ergaenzungsfragen(Nomen, Eigenname) -->
	interrogativpronomen, verphrase(Nomen),
	praepositionalphrase(Eigenname).

entscheidungsfragen(Eigenname_1, Nomen, Eigenname_2) -->
	verb, eigenname(Eigenname_1), artikel, nomen(Nomen), praepositionalphrase(Eigenname_2).

interrogativpronomen --> [wer].
verb --> [X], {lex(X, verb)}.
artikel --> [der]; [die]; [das].
nomen(Nomen) --> [Nomen].
praeposition --> [von].
eigenname(Eigenname) --> [Eigenname].

verphrase(Nomen)--> verb, nominalphrase(Nomen).
nominalphrase(Nomen) --> (artikel, nomen(Nomen)) ; nomen(Nomen).
praepositionalphrase(Eigenname) --> praeposition, eigenname(Eigenname).




