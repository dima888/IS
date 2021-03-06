%% Familienstammbaum einbinden
:-consult('../lexikon.pl').
%=========================================================================================================
%						 FRAGEN / DER DISPATCHER
%=========================================================================================================
%	Ergaenzungsfragen, Beispiel: Wer ist der Onkel von Peter
s(Semantik, s(StrukturInterrogativpronomen, StrukturVerphrase, StrukturPraepositionalphrase),
  [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname], []) :-

		% SATZBAU LAUT UNSERER SKIZZE
	interrogativpronomen(StrukturInterrogativpronomen, [Interrogativpronomen], []),
	verphrase(StrukturVerphrase, _ArtikelWort, _NomenWort, _Genus, [Verb, Artikel, Nomen], []),
	praepositionalphrase(StrukturPraepositionalphrase, [Praeposition, Eigenname], []),
	generateSemantic(Nomen, _Result, Eigenname, Semantik).



%	Ergaenzungsfragen, Beispiel: Wer ist Onkel von Peter
s(Semantik, s(StrukturInterrogativpronomen, StrukturVerphrase, StrukturPraepositionalphrase),
  [Interrogativpronomen, Verb, Nomen, Praeposition, Eigenname], []) :-

		% SATZBAU LAUT UNSERER SKIZZE
	interrogativpronomen(StrukturInterrogativpronomen, [Interrogativpronomen], []),
	verphrase(StrukturVerphrase, _ArtikelWort, _NomenWort, _Genus, [Verb, Nomen], []),
	praepositionalphrase(StrukturPraepositionalphrase, [Praeposition, Eigenname], []),
	generateSemantic(Nomen, _Result, Eigenname, Semantik).


% Entscheidungsfragen: ist maria die tante von dima
s(Semantik, s(StrukturVerb, StrukturEigenname, StrukturNominalphrase, StrukturPraepositionalphrase),
	      [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2 ], []) :-

  verb(StrukturVerb, [Verb], []),
  eigenname(StrukturEigenname, _Genus, [Eigenname_1], []),
  nominalphrase(StrukturNominalphrase, _ArtikelWort, _NomenWort,_Genus, [Artikel, Nomen], []),
  praepositionalphrase(StrukturPraepositionalphrase, [Praeposition, Eigenname_2], []),

  generateSemantic(Nomen, Eigenname_1, Eigenname_2, Semantik).

%=========================================================================================================
%						 HILFS REGEL FUER DIE
%						 AUSWERTUNG VON DER
%						 SEMANTIK
%=========================================================================================================
generateSemantic(Nomen, Param_1, Param_2, Result) :-
	lex(Nomen, Semantik, _, _, _, _, nomen),
	Result =.. [Semantik, Param_1, Param_2].

% Die im Praktikum gestellte Aufgabe
a(Semantik, _, Antwort, []) :-
      antwort(Semantik, Antwort, []).

%=========================================================================================================
%	                               Define Clause Grammar / DCG
%=========================================================================================================

antwort(Semantik) -->
	eigenname((Eigenname_1_Struktur), Genus),
	verphrase(verphrase(_StrukturVerb, _StrukturNominalphrase), _ArtikelWort, NomenWort, Genus),
	praepositionalphrase(praepositionalphrase(_StrukturPraeposition, Eigenname_2_Struktur)),        {
        arg(1, Eigenname_1_Struktur, Eigenname_1),
	arg(1, Eigenname_2_Struktur, Eigenname_2),
	lex(NomenWort, Praedikat, _Genus, _Casus, _Numerus, _What, nomen),
	Semantik =.. [Praedikat, Eigenname_1, Eigenname_2]
	}.

interrogativpronomen(interrogativpronomen(Semantik)) -->
	[Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, interrogativpronomen)}.

verb(verb(Semantik)) --> [Wort],
	{lex(Wort, Semantik, _Genus, _Casus, _Numerus, _Whatever, verb)}.

artikel(artikel(Semantik), Wort, Genus) -->
	[Wort], {lex(Wort, Semantik, Genus, _Casus, _Numerus, _What, artikel)}.

nomen(nomen(Wort), Wort, Genus) -->
	[Wort], {lex(Wort, _Semantik, Genus, _Casus, _Numerus, _What, nomen)}.

praeposition(praeposition(Semantik)) -->
	[Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, praeposition)}.

eigenname(eigenname(Eigenname), Genus) -->
	[Eigenname], {lex(Eigenname, _Semantik, Genus, _Casus, _Numerus, _What, eigenname)}.

verphrase(verphrase(StrukturVerb, StrukturNominalphrase), ArtikelWort, NomenWort, Genus) -->
	verb(StrukturVerb),
	nominalphrase(StrukturNominalphrase, ArtikelWort, NomenWort, Genus).

nominalphrase(nominalphrase(StrukturArtikel, StrukturNomen), ArtikelWort, NomenWort, Genus) -->
	nomen(StrukturNomen, NomenWort, Genus);
	artikel(StrukturArtikel, ArtikelWort, Genus), nomen(StrukturNomen, NomenWort, Genus).

praepositionalphrase(praepositionalphrase(StrukturPraeposition, StrukturEigenname)) -->
	praeposition(StrukturPraeposition),
	eigenname(StrukturEigenname, _Genus).

