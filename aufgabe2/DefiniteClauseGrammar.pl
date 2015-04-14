%% Familienstammbaum einbinden
:-consult('../lexikon.pl').


%=========================================================================================================
%						 DIE ANFRAGE
%=========================================================================================================
s(Semantik, Struktur, Frage, []) :-
	frage(Semantik, Struktur, Frage, []).


%=========================================================================================================
%						 DER DISPATCHER
%=========================================================================================================
% Das ist eine Entscheidungsfrage! Z.B. ist petra die tante von kevin
frage(Semantik, Struktur, [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2], []) :-

	entscheidungsfragen(_SemantikVerb, StrukturVerb, _SemantikEigenname_1, StrukturEigenname_1,
			    StrukturNominalphrase, _StrukturArtikel, SemantikNomen, _StrukturNomen,
			    StrukturPraepositionalphrase, _SemantikPraeposition, _StrukturPraeposition, _SemantikEigenname_2, _StrukturEigenname_2,
			    [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2], []),

	% Jetzt Brauche ich den Nomen, Eigenname und das Praedikat -> SemantikNomen
	% Daraus bauen wir die gewuenschte Semantik zusammen
	giveSemanticSyntax(SemantikNomen, Eigenname_1, Eigenname_2, Semantik),

	% Jetzt bauen wir die gewuenschte Struktur zusammen
	giveStructSyntax(StrukturVerb, StrukturEigenname_1, StrukturNominalphrase,
			   StrukturPraepositionalphrase, Struktur).

% Das ist eine Ergaenzungsfrage! Z.B. wer ist der onkel von hans
frage(Semantik, Struktur, [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname], []) :-


	ergaenzungsfragen(_SemantikInterrogativpronomen, StrukturInterrogativpronomen, StrukturVerphrase,
			  _StrukturNominalphrase, _SemantikVerb, _StrukturVerb, _StrukturArtikel,
			  SemantikNomen, _StrukturNomen, StrukturPraepositionalphrase, _SemantikPraeposition,
			  _StrukturPraeposition, _SemantikEigenname, _StrukturEigenname,
			  [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname], []),

	giveSemanticSyntax_2(SemantikNomen, _Antwort, Eigenname, Semantik),
	giveStructSyntax(StrukturInterrogativpronomen, StrukturVerphrase, StrukturPraepositionalphrase, Struktur).

% Das ist eine Ergaenzungsfrage! Z.B. wer ist onkel von hans
frage(Semantik, Struktur, [Interrogativpronomen, Verb, Nomen, Praeposition, Eigenname], []) :-


	ergaenzungsfragen(_SemantikInterrogativpronomen, StrukturInterrogativpronomen, StrukturVerphrase,
			  _StrukturNominalphrase, _SemantikVerb, _StrukturVerb, _StrukturArtikel,
			  SemantikNomen, _StrukturNomen, StrukturPraepositionalphrase, _SemantikPraeposition,
			  _StrukturPraeposition, _SemantikEigenname, _StrukturEigenname,
			  [Interrogativpronomen, Verb, _Artikel, Nomen, Praeposition, Eigenname], []),

	giveSemanticSyntax_2(SemantikNomen, _Antwort, Eigenname, Semantik),
	giveStructSyntax(StrukturInterrogativpronomen, StrukturVerphrase, StrukturPraepositionalphrase, Struktur).



%=========================================================================================================
%						 DIE FRAGEN
%=========================================================================================================
% Wer ist der vater von klaus
ergaenzungsfragen(SemantikInterrogativpronomen, StrukturInterrogativpronomen, StrukturVerphrase,
		  StrukturNominalphrase, SemantikVerb, StrukturVerb, StrukturArtikel,
		  SemantikNomen, StrukturNomen, StrukturPraepositionalphrase, SemantikPraeposition,
		  StrukturPraeposition, SemantikEigenname, StrukturEigenname) -->

	% SATZBAU LAUT UNSERER SKIZZE
	interrogativpronomen(SemantikInterrogativpronomen, StrukturInterrogativpronomen),

	verphrase(StrukturVerphrase, SemantikVerb, StrukturVerb, StrukturNominalphrase,
		  SemantikNomen, StrukturNomen, StrukturArtikel),

	praepositionalphrase(StrukturPraepositionalphrase, SemantikPraeposition, StrukturPraeposition, SemantikEigenname, StrukturEigenname).


% Entscheidungsfrage: ist maria die tante von dima
entscheidungsfragen(SemantikVerb, StrukturVerb, SemantikEigenname_1, StrukturEigenname_1,
		    StrukturNominalphrase,
		    StrukturArtikel, SemantikNomen, StrukturNomen,
		    StrukturPraepositionalphrase, SemantikPraeposition, StrukturPraeposition, SemantikEigenname_2, StrukturEigenname_2) -->

	% SATZBAU LAUT UNSERER SKIZZE
	verb(SemantikVerb, StrukturVerb),

	eigenname(SemantikEigenname_1, StrukturEigenname_1),

	nominalphrase(StrukturNominalphrase, StrukturArtikel, SemantikNomen, StrukturNomen),

	praepositionalphrase(StrukturPraepositionalphrase, SemantikPraeposition, StrukturPraeposition, SemantikEigenname_2, StrukturEigenname_2).



%=========================================================================================================
%						 HILFSREGELN
%=========================================================================================================
%Fuer Entscheidungsfragen
giveSemanticSyntax(Praedikat, Eigenname_1, Eigenname_2, Result) :-
	Result =.. [Praedikat, Eigenname_1, Eigenname_2].

% Fuer Ergaenzungsfragen
giveSemanticSyntax_2(Praedikat, Eigenname_1, Eigenname_2, Result) :-
	Result =.. [Praedikat, Eigenname_1, Eigenname_2].

% TODO:
%giveStructSyntax(List, Result) :- Result = List.
giveStructSyntax(StrukturInterrogativpronomen, StrukturVerphrase, StrukturPraepositionalphrase, Result) :-
	Result =.. [s, StrukturInterrogativpronomen, StrukturVerphrase, StrukturPraepositionalphrase].

giveStructSyntax(StrukturVerb, StrukturEigenname, StrukturNominalphrase, StrukturPraepositionalphrase, Result) :-
	Result =.. [s, StrukturVerb, StrukturEigenname, StrukturNominalphrase, StrukturPraepositionalphrase].



%=========================================================================================================
%	                               Define Clause Grammar / DCG
%=========================================================================================================
interrogativpronomen(Semantik, Struktur) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, interrogativpronomen, Struktur)}.
verb(Semantik, Struktur) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _Whatever, verb, Struktur)}.
artikel(Struktur) --> [Wort], {lex(Wort, _Semantik, _Genus, _Casus, _Numerus, _What, artikel, Struktur)}.
nomen(Semantik, Struktur) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, nomen, Struktur)}.
praeposition(Semantik, Struktur) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, praeposition, Struktur)}.
eigenname(Semantik, Struktur) --> [Eigenname], {lex(Eigenname, Semantik, _Genus, _Casus, _Numerus, _What, eigenname, Struktur)}.


verphrase(StrukturVerphrase, SemantikVerb, StrukturVerb, StrukturNominalphrase, SemantikNominalphraseNomen,
	  StrukturNominalphraseNomen, StrukturNominalphraseArtikel) -->
	verb(SemantikVerb, StrukturVerb),
	nominalphrase(StrukturNominalphrase, StrukturNominalphraseArtikel, SemantikNominalphraseNomen, StrukturNominalphraseNomen),
	{lex(StrukturVerb, StrukturNominalphrase, verphrase, StrukturVerphrase)}.

nominalphrase(StrukturNominalphrase, StrukturArtikel, SemantikNomen, StrukturNomen) -->
	(artikel(StrukturArtikel), nomen(SemantikNomen, StrukturNomen)),
	{lex(StrukturArtikel, StrukturNomen, nominalphrase, StrukturNominalphrase)}, !
	;
	nomen(SemantikNomen, StrukturNomen),
	{lex(StrukturArtikel, StrukturNomen, nominalphrase, StrukturNominalphrase)}.

praepositionalphrase(StrukturPraepositionalphrase, SemantikPraeposition, StrukturPraeposition, SemantikEigenname, StrukturEigenname) -->
	praeposition(SemantikPraeposition, StrukturPraeposition),
	eigenname(SemantikEigenname, StrukturEigenname),
	{lex(StrukturPraeposition, StrukturEigenname, praepositionalphrase, StrukturPraepositionalphrase)}.

