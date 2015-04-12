:-consult('aufgabe1/schaeferClan.pl').


%%	@param1: Phonologische Eigenschaft: Das wort selbst
%
%%	@param2: Semantik z.B. tante/tanten; hat/haben; Falls jemand
%	schreib gib dir die Tanten von Hans und nicht Tante von hans.
%	Damit trozdem die selbe regel verwendet wird Anhand der Semantik
%	entscheiden wir, welchen Praedikat wir aufrufen!

%%	AB Hier kommen die Morphologischen Eigenschaften! Aus Matrix
%	@param3: GENUS: Maskulinum- Femeninum - Neutrum - Plural
%	@param4: CASUS: What the fuck????
%	@param5: Numerus: Singular/Plural
%
%
%	@param6: Eigenschaften konjugierter Verben / What the fuck????
%	Übungen:
%       Ein Hund bellt.
%	Verben: bellt
%	Konjugiertes Verb: bellt, 3.Person Singular, Präsens

%       @param7: id/ Wortgruppe. Dies machen wir fuer die Performance

%
%

%	Verben
lex(ist, ist, _Genus, _Casus, singular, _What, verb).

%	Artikeln
lex(der, der, maskulinium, _Casus, singular, _What, artikel).
lex(die, die, femeninum, _Casus, singular, _What, artikel).
lex(die, die, femeninum, _Casus, plural, _What, artikel).
lex(das, das, neutrum, _Casus, singular, _What, artikel).

%	interrogativpronomen
lex(wer, wer, _Genus, _Casus, _Numerus, _What, interrogativpronomen).

%	praeposition
lex(von, von, _Genus, _Casus, _Numerus, _What, praeposition).

%	Eigenname
lex(X, X, _Genus, _Casus, _Numerus, _What, eigenname) :- male(X).
lex(X, X, _Genus, _Casus, _Numerus, _What, eigenname) :- female(X).

%	Nomen
lex(opa, grandpa, _Genus, _Casus, _Numerus, _What, nomen).
lex(oma, grandma, _Genus, _Casus, _Numerus, _What, nomen).
lex(vater, father, _Genus, _Casus, _Numerus, _What, nomen).
lex(mutter, mother, _Genus, _Casus, _Numerus, _What, nomen).
lex(onkel, uncle, _Genus, _Casus, _Numerus, _What, nomen).
lex(tante, aunt, _Genus, _Casus, _Numerus, _What, nomen).
lex(bruder, brother, _Genus, _Casus, _Numerus, _What, nomen).
lex(schwester, sister, _Genus, _Casus, _Numerus, _What, nomen).




