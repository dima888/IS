module(schaeferClan, [father/2, mother/2, brother/2, sister/2, grandpa/2, grandma/2, cousinM/2, cousinF/2, cousin/2, parents/2, uncle/2, aunt/2, married/2,  half_brother/2, half_sister/2, good_brohter/2,
			  good_sister/2, relationship_by_marriage/2]).


%%	 So sind die Funktionen zu lesen!
%father:
% Die Funktion ist zu lesen, param 1 ist der Vater von Param 2.
% Oder param 2 ist der Sohn oder Tocher von param 1
%
% mother:
% % Die Funktion ist zu lesen, param 1 ist die Mutter von Param 2.
% Oder param 2 ist der Sohn oder Tocher von param 1
%
% Je hoeher die Zahl hinter dem Namen einer Person, desto juenger ist
% die Person.

%%	Alle maennlichen Personen
male(leo).
male(vitalij).
male(waldimar_1).
male(eugen).
male(gera).
male(anatoli_1).
male(anatoli_2).
male(sergej_1).
male(jakob).
male(waldimar_2).
male(sergej_2).
male(jura).
male(anatoli).
male(dima).
male(alexander).
male(mark).
male(ivan).

% Fiktiven Maenner
male(spiderman).


%%	Alle weiblichen Personen
female(moder).
female(erna).
female(lila).
female(irma).
female(lidia).
female(maria).
female(natalie_1).
female(lena).
female(natalie_2).
female(marina).
female(swetlana).
female(tatjana).
female(nicole).
female(victoria).
female(vironica).
female(christina).
female(valeria).

% Fiktive Frauen
female(catwoman).

%%	Verheiratet mit einander
married_define(leo, erna).
married_define(vatalij, lila).
married_define(vitilij, lila).
married_define(waldimar_1, irma).
married_define(eugen, lidia).
married_define(sergej_1, natalie_1).
married_define(jakob, lena).
married_define(waldimar_2, marina).
married_define(sergej_2, natalie_2).
married_define(jura, swetlana).

%% Familie Nr. 1
father(eugen, anatoli_2).
father(eugen, swetlana).
father(eugen, dima).

% Fiktive Kinder / Halb geschwister von mir
father(eugen, spiderman).
father(eugen, catwoman).


%% Familie Nr. 2
father(sergej_1, alexander).
father(sergej_1, tatjana).

%% Familie Nr. 3
father(jakob, victoria).
father(jakob, vironica).
father(jakob, christina).

%% Familie Nr. 4
father(waldimar_1, waldimar_2).
father(waldimar_1, natalie_2).

%% Familie Nr. 5
father(leo, irma).
father(leo, lidia).
father(leo, maria).
father(leo, jakob).
father(leo, natalie_1).

%% Familie Nr. 6
father(vitalij, eugen).
father(vitalij, anatoli_1).
father(vitalij, gera).

%% Familie Nr. 7
father(sergej_2, nicole).
father(sergej_2, mark).

%% Familie Nr. 8
father(waldimar_2, valeria).

%% Famile Nr. 9
father(jura, ivan).

%% Familie Nr. 1
mother(lidia, anatoli_2).
mother(lidia, swetlana).
mother(lidia, dima).

%% Familie Nr. 2
mother(natalie_1, alexander).
mother(natalie_1, tatjana).

%% Familie Nr. 3
mother(lena, victoria).
mother(lena, vironica).
mother(lena, christina).

%% Familie Nr. 4
mother(irma, waldimar_2).
mother(irma, natalie_2).

%% Familie Nr. 5
mother(erna, irma).
mother(erna, lidia).
mother(erna, maria).
mother(erna, jakob).
mother(erna, natalie_1).

%% Familie Nr. 6
mother(lila, eugen).
mother(lila, anatoli_1).
mother(lila, gera).

%% Familie Nr. 7
mother(natalie_2, nicole).
mother(natalie_2, mark).

%% Familie Nr. 8
mother(marina, valeria).

%% Famile Nr. 9
mother(swetlana, ivan).

%% Familie Nr. 10
mother(moder, erna).

% Person 1 ist mit Person 2 verheiratet
% Diese Funktion ist Kommutativ
married(Person1, Person2) :-
	married_define(Person1, Person2).
married(Person1, Person2) :-
	married_define(Person2, Person1).

% Diese Funktion prueft ob zwei Personen geschwister sind
% Sprich ob ein Elternteil gleich ist.
% @retrun boolean
sibling(X, Y) :- sister(X, Y); brother(X, Y).

% Param 1 ist ein Bruder und Param zwei ein Geschwister
% @param Person - Eine Person
% @param Brother - Potentieller Bruder von Person
% @return boolean
brother(Brother, Sibling) :-
	father(Father, Brother),
	mother(Mother, Brother),

	child(Sibling, Father),
	child(Sibling, Mother),

	male(Brother),
	Sibling \== Brother.

% TODO: Beispiele zum testen baun
half_brother(Half_Brother, Sibling) :-
	father(Father, Half_Brother),
	not(mother(_Mother, Half_Brother)),
	child(Sibling, Father),
	male(Half_Brother),
        Half_Brother \== Sibling.

half_brother(Half_Brother, Sibling) :-
	not(father(_Father, Half_Brother)),
	mother(Mother, Half_Brother),
	child(Sibling, Mother),
	male(Half_Brother),
        Half_Brother \== Sibling.


% TODO: Beispiele dafuer bauen und testen
half_sister(Half_Sister, Person) :-
	not(father(_Father, Half_Sister)),
	mother(Mother, Half_Sister),
	child(Person, Mother),
	female(Half_Sister),
        Half_Sister \== Person.


half_sister(Half_Sister, Person) :-
	father(Father, Half_Sister),
	not(mother(_Mother, Half_Sister)),
	child(Person, Father),
	female(Half_Sister),
        Half_Sister \== Person.


half_sibling(X, Y) :- half_sister(X, Y) ; half_brother(X, Y).

% Schwager von Person
good_brother(Good_Brother, Person) :-
	sister(Sister, Person),
	married(Sister, Good_Brother).

good_sister(Good_Sister, Person) :-
	brother(Brother, Person),
	married(Brother, Good_Sister).


relationship_by_marriage(Relationship_By_Marriage, Person) :-
	good_brother(Relationship_By_Marriage, Person) ;
	good_sister(Relationship_By_Marriage, Person).


% Erster Parameter ist die Schwester und zweiter ein Geschwister
% @param Person - Eine Person
% @param Sister - Die Potentielle Schwester von Person
% @retrun boolean
sister(Sister, Sibling) :-
        father(Father, Sister),
	mother(Mother, Sister),

	child(Sibling, Father),
	child(Sibling, Mother),

	female(Sister),
	Sibling \== Sister.

% Gib Die Eltern zum Kind
% @param Child - Das Kind
% @param Parent - Das Elternteil
% @return Parent
parent(Parent, Child) :- father(Parent, Child); mother(Parent, Child).
child(Child, Parent) :- parent(Parent, Child).

% male(Cousin) der Cousin von jemanden
% @param Person - Eine Person
% @param Cousin - Potentieller Cousin von Person
% @return boolean
cousinM(Cousin, Person) :-
	parent(ParentFromPerson, Person),
	parent(ParentFromCousin, Cousin),
	sibling(ParentFromPerson, ParentFromCousin),
	male(Cousin).
% female(Cousin) die Cousine von Jemanden
cousinF(Cousin, Person) :-
	parent(ParentFromPerson, Person),
	parent(ParentFromCousin, Cousin),
	sibling(ParentFromPerson, ParentFromCousin),
	female(Cousin).

cousin(Cousin, Person) :-
	cousinM(Person, Cousin) ; cousinF(Person, Cousin).

% Lieft einen Grossvater oder Grossmutter zur einer Person
% @param Grandpa v Grandma - Der Potentielle Opa oder Oma
% @param Person - Die Person, von den wir den Opa oder Oma haben
% moechten
grandpa(Grandpa, Grandchild) :-
	parent(X, Grandchild), parent(Grandpa, X), male(Grandpa).

grandma(Grandma, Grandchild) :-
	parent(X, Grandchild), parent(Grandma, X), female(Grandma).

grandparent(Grandparent, Grandchild) :-
	grandpa(Grandparent, Grandchild) ; grandma(Grandparent, Grandchild).

% Lieft einen Blutverwandten Onkel zurueck oder
% die Neffen zum Onkel.
% @param Uncle - Ein Onkel
% @param Nephew - Ein Neffe/Neffin
uncle(Uncle, Nephew) :-
	parent(Parent, Nephew),
	brother(Uncle, Parent).

% Lieft eine Blutverwandte Tante zurueck oder
% die Neffen zur Tante
% @para Aunt - Eine Tante
% @param Nephew - Ein Neffe/Neffin
aunt(Aunt, Nephew) :-
	parent(Parent, Nephew),
	sister(Aunt, Parent).

% Vorfahre erster Parameter von Person zweiten Parameter
ancestor(Ancestor, Person) :-
	parent(Parent, Person),
	ancestorHelper(Ancestor, Parent).
% -------- Rekursion Ende ----------
ancestorHelper(Vorfahre, Person) :-
	parent(Vorfahre, Person).
% --------- Rekursion ---------------
ancestorHelper(Vorfahre, Person) :-
	parent(Parent, Person),
	ancestorHelper(Vorfahre, Parent).


schwippschwapp(Schwippschwager, Person) :-
sibling_all(Sibling, Person),
married(Schwager, Sibling),
sibling_all(Schwager, Schwippschwager).

schwippschwapp(Schwippschwager, Person) :-
married(MannFrau, Person),
married(MannFrau2, Schwippschwager),

sibling_all(MannFrau, MannFrau2).
