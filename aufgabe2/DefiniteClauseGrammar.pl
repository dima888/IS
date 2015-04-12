%% Familienstammbaum einbinden
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


%%	[wer,ist,der,onkel,von,kevin] v [wer,ist,onkel,von,kevin]
% s(Semantik, Struktur, [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition, Eigenname], []) :-
%	 ergaenzungsfragen(_SemantikVerb, _SemantikNominalphrase,
%	 _SemantikPraepositionphrase, _SemantikEigenname,
%	 [Interrogativpronomen, Verb, Artikel, Nomen, Praeposition,
%	 Eigenname], []),
%	 uncle(Onkel, Eigenname),
%	 Semantik = uncle(Onkel, Eigenname),
%	 is(Struktur, 7*7).

%%	[ist,martha,die,tante,von,kevin]  v [ist,martha,eine,tante,von,kevin]
s(Semantik, Struktur, [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2], []) :-
	entscheidungsfragen(_SemantikVerb, _SemantikEigenname,
			    _SemantikNomen, _SemantikPraepositionalphrase, _SemantikPraepositionalphraseEigenname,
			    [Verb, Eigenname_1, Artikel, Nomen, Praeposition, Eigenname_2], []).


%%	Define Clause Grammar / DCG
ergaenzungsfragen(SemantikVerb, SemantikNominalphrase, SemantikPraepositionphrase, SemantikEigenname) -->
	interrogativpronomen(_Semantik), verphrase(SemantikVerb, SemantikNominalphrase),
	praepositionalphrase(SemantikPraepositionphrase, SemantikEigenname).

entscheidungsfragen(SemantikVerb, SemantikEigenname, SemantikNomen, SemantikPraepositionalphrase, SemantikPraepositionalphraseEigenname) -->
	verb(SemantikVerb), eigenname(SemantikEigenname), artikel, nomen(SemantikNomen), praepositionalphrase(SemantikPraepositionalphrase, SemantikPraepositionalphraseEigenname).

interrogativpronomen(Semantik) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, interrogativpronomen)}.
verb(Semantik) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _Whatever, verb)}.
artikel --> [Wort], {lex(Wort, _Semantik, _Genus, _Casus, _Numerus, _What, artikel)}.
nomen(Semantik) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, nomen)}.
praeposition(Semantik) --> [Wort], {lex(Wort, Semantik, _Genus, _Casus, _Numerus, _What, praeposition)}.
eigenname(Semantik) --> [Eigenname], {lex(Eigenname, Semantik, _Genus, _Casus, _Numerus, _What, eigenname)}.

verphrase(SemantikVerb, SemantikNominalphrase)--> verb(SemantikVerb), nominalphrase(SemantikNominalphrase).
nominalphrase(SemantikArtikel, SemantikNomen) --> (artikel(SemantikArtikel), nomen(SemantikNomen)) ; nomen(SemantikNomen).
praepositionalphrase(SemantikPraeposition, SemantikEigenname) --> praeposition(SemantikPraeposition), eigenname(SemantikEigenname).





