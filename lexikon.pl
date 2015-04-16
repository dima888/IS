:-consult('aufgabe1/schaeferClan.pl').


%%	@param1: Phonologische Eigenschaft: Das wort selbst
%
%%	@param2: Semantik z.B. tante/tanten; hat/haben; Falls jemand
%	schreib gib dir die Tanten von Hans und nicht Tante von hans.
%	Damit trozdem die selbe regel verwendet wird Anhand der Semantik
%	entscheiden wir, welchen Praedikat wir aufrufen!

%%	AB Hier kommen die Morphologischen Eigenschaften! Aus Matrix
%	@param3: GENUS: Maskulinum- Femeninum - Neutrum - Plural
%	@param4: CASUS: Der Fall (Akusutiv, Nominativ, Genitiv, Dativ)
%	@param5: Numerus: Singular/Plural
%
%
%	@param6: Eigenschaften konjugierter Verben / What the fuck????
%	�bungen:
%       Ein Hund bellt.
%	Verben: bellt
%	Konjugiertes Verb: bellt, 3.Person Singular, Pr�sens

%       @param7: id/ Wortgruppe. Dies machen wir fuer die Performance
%
%       @param8 Struktur! Exmaple: artikel(der); verb(ist);

%
%

%	Verben
lex(ist, ist, _Genus, _Casus, singular, _What, verb).

%	Artikeln
lex(der, der, maskulinium, _Casus, singular, _What, artikel).
lex(die, die, femeninum, _Casus, singular, _What, artikel).
lex(das, das, neutrum, _Casus, singular, _What, artikel).

lex(eine, die, femeninum, _Casus, singular, _What, artikel).
%lex(die, die, femeninum, _Casus, plural, _What, artikel, Struktur).

%	interrogativpronomen
lex(wer, wer, _Genus, _Casus, _Numerus, _What, interrogativpronomen).

%	praeposition
lex(von, von, _Genus, _Casus, _Numerus, _What, praeposition).

%	Eigenname
lex(X, X, _Genus, _Casus, _Numerus, _What, eigenname) :- male(X).
lex(X, X, _Genus, _Casus, _Numerus, _What, eigenname) :- female(X).

%	Nomen
lex(opa, grandpa, maskulinium, _Casus, _Numerus, _What, nomen).
lex(oma, grandma, femeninum, _Casus, _Numerus, _What, nomen).
lex(vater, father, maskulinium, _Casus, _Numerus, _What, nomen).
lex(mutter, mother, femeninum, _Casus, _Numerus, _What, nomen).
lex(onkel, uncle, maskulinium, _Casus, _Numerus, _What, nomen).
lex(tante, aunt, femeninum, _Casus, _Numerus, _What, nomen).
lex(bruder, brother, maskulinium, _Casus, _Numerus, _What, nomen).
lex(schwester, sister, femeninum, _Casus, _Numerus, _What, nomen).
