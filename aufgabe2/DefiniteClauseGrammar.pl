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
	verphrase(StrukturVerphrase, [Verb, Artikel, Nomen], []),
	praepositionalphrase(StrukturPraepositionalphrase, [Praeposition, Eigenname], []),
	generateSemantic(Nomen, _Result, Eigenname, Semantik).



%	Ergaenzungsfragen, Beispiel: Wer ist Onkel von Peter
s(Semantik, s(StrukturInterrogativpronomen, StrukturVerphrase, StrukturPraepositionalphrase),
  [Interrogativpronomen, Verb, Nomen, Praeposition, Eigenname], []) :-

		% SATZBAU LAUT UNSERER SKIZZE
	interrogativpronomen(StrukturInterrogativpronomen, [Interrogativpronomen], []),
	verphrase(StrukturVerphrase, [Verb, Nomen], []),
	praepositionalphrase(StrukturPraepositionalphrase, [Praeposition, Eigenname], []),
	generateSemantic(Nomen, _Result, Eigenname, Semantik).


% Entscheidungsfragen: ist maria die tante von dima
s(Semantik, s(StrukturVerb, StrukturEigenname, StrukturNominalphrase, StrukturPraepositionalphrase),
	      [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2 ], []) :-

  verb(StrukturVerb, [Verb], []),
  eigenname(StrukturEigenname, [Eigenname_1], []),
  nominalphrase(StrukturNominalphrase, [Artikel, Nomen], []),
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


%=========================================================================================================
%	                               Define Clause Grammar / DCG
%=========================================================================================================
interrogativpronomen(interrogativpronomen(Semantik)) -->
	[Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, interrogativpronomen)}.

verb(verb(Semantik)) --> [Wort],
	{lex(Wort, Semantik, _Genus, _Casus, _Numerus, _Whatever, verb)}.

artikel(artikel(Semantik), Genus) -->
	[Wort], {lex(Wort, Semantik, Genus, _Casus, _Numerus, _What, artikel)}.

nomen(nomen(Semantik), Genus) -->
	[Wort], {lex(Wort, Semantik, Genus, _Casus, _Numerus, _What, nomen)}.

praeposition(praeposition(Semantik)) -->
	[Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, praeposition)}.

eigenname(eigenname(Eigenname)) -->
	[Eigenname], {lex(Eigenname, _Semantik, _Genus, _Casus, _Numerus, _What, eigenname)}.


verphrase(verphrase(StrukturVerb, StrukturNominalphrase)) -->
	verb(StrukturVerb),
	nominalphrase(StrukturNominalphrase).

nominalphrase(nominalphrase(StrukturArtikel, StrukturNomen)) -->
	(artikel(StrukturArtikel, Genus), nomen(StrukturNomen, Genus)), !
	;
	nomen(StrukturNomen, Genus).

praepositionalphrase(praepositionalphrase(StrukturPraeposition, StrukturEigenname)) -->
	praeposition(StrukturPraeposition),
	eigenname(StrukturEigenname).

