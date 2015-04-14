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
%	Übungen:
%       Ein Hund bellt.
%	Verben: bellt
%	Konjugiertes Verb: bellt, 3.Person Singular, Präsens

%       @param7: id/ Wortgruppe. Dies machen wir fuer die Performance
%
%       @param8 Struktur! Exmaple: artikel(der); verb(ist);

%
%

%	Verben
lex(ist, ist, _Genus, _Casus, singular, _What, verb, Struktur) :- giveStruct(verb, ist, Struktur).

%	Artikeln
lex(der, der, maskulinium, _Casus, singular, _What, artikel, Struktur) :- giveStruct(artikel, der, Struktur).
lex(die, die, femeninum, _Casus, singular, _What, artikel, Struktur) :- giveStruct(artikel, die, Struktur).
lex(das, das, neutrum, _Casus, singular, _What, artikel, Struktur) :- giveStruct(artikel, das, Struktur).

lex(eine, die, femeninum, _Casus, singular, _What, artikel, Struktur) :- giveStruct(artikel, eine, Struktur).
%lex(die, die, femeninum, _Casus, plural, _What, artikel, Struktur).

%	interrogativpronomen
lex(wer, wer, _Genus, _Casus, _Numerus, _What, interrogativpronomen, Struktur) :- giveStruct(interrogativpronomen, wer, Struktur).

%	praeposition
lex(von, von, _Genus, _Casus, _Numerus, _What, praeposition, Struktur) :- giveStruct(praeposition, von, Struktur).

%	Eigenname
lex(X, X, _Genus, _Casus, _Numerus, _What, eigenname, Struktur) :- male(X), giveStruct(eigenname, X, Struktur).
lex(X, X, _Genus, _Casus, _Numerus, _What, eigenname, Struktur) :- female(X), giveStruct(eigenname, X, Struktur).

%	Nomen
lex(opa, grandpa, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, opa, Struktur).
lex(oma, grandma, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, oma, Struktur).
lex(vater, father, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, vater, Struktur).
lex(mutter, mother, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, mutter, Struktur).
lex(onkel, uncle, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, onkel, Struktur).
lex(tante, aunt, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, tante, Struktur).
lex(bruder, brother, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, bruder, Struktur).
lex(schwester, sister, _Genus, _Casus, _Numerus, _What, nomen, Struktur) :- giveStruct(nomen, schwester, Struktur).

%	Lexikon fuer praepositionalphrase
lex(StrukturVerb, StrukturPraeposition, praepositionalphrase, Struktur) :-
	giveStruct_2(praepositionalphrase, StrukturVerb, StrukturPraeposition, Struktur).

%	Lexikon fuer verphrase Prototyp
lex(StrukturVerb, StrukturNominalphrase, verphrase, Struktur) :- giveStruct_2(verphrase, StrukturVerb, StrukturNominalphrase, Struktur).

%	Lexikon fuer nominalphrase
lex(StrukturArtikel, StrukturNomen, nominalphrase, Struktur) :- giveStruct_2(nominalphrase, StrukturArtikel, StrukturNomen, Struktur).


% Hilfsregel Atomare Elemente der deutschen Grammatik
giveStruct(Id, Word, Struct) :- Struct =.. [Id, Word].

% Hilfsregel fuer nicht Automare Elemente der deutschen Grammatik
giveStruct_2(Id, StructVerb, StructPraeposition, Struct) :-
	Struct =.. [Id, StructVerb, StructPraeposition].


