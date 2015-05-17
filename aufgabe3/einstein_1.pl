%There are five houses in a row, each of a different color(red, green, ivory, blue, yellow)
% and inhabited by men of different nationalities (Englishman, Spaniard,
% Ukrainian, Norwegian, Japanese), with different pets (dog, snake,
% zebra, fox horse), drinks (tea, orange juice, milk, water, coffee), and
% cigarettes (Old Gold, Kools, Chesterfield, Lucky Strike, Parliament).


% --------------------	Wissensbasis --------------------
%	House Numbers
houseNumber(1). houseNumber(2). houseNumber(3). houseNumber(4). houseNumber(5).

%	Nationalitys
nationality(englishman). nationality(spaniard). nationality(ukrainian). nationality(norwegian). nationality(japanese).

%	Pets
pet(dog). pet(snake). pet(zebra). pet(fox). pet(horse).

%	House Colors
color(red). color(green). color(ivory). color(blue). color(yellow).

%	Drinks
drink(tea). drink(orangeJuice). drink(milk). drink(water). drink(coffee).

%	Cigarettes Marks
cigarettes(oldGold). cigarettes(kools). cigarettes(chesterfield). cigarettes(luckyStrike). cigarettes(parliaments).

%	House
% rule generate a empty house
% AttributeList => [number, color, nationality, cigarettes, pet]
% Result =>  [[number, _G4573], [color, _G4582], [nationality, _G4594], [cigarettes, _G4609], [pet, _G4627]]
generateEmptyHouse(Result, AttributeList) :-
	generateEmptyHouse(Result, [], AttributeList).

% Abort rule
generateEmptyHouse(Buffer, Buffer, []).

% algorithmus rule
generateEmptyHouse(Result, Buffer, AttributeList) :-
	[Head | Tail] = AttributeList,
	append(Buffer, [[Head, _Undefine]], ModifyBuffer),
	generateEmptyHouse(Result, ModifyBuffer, Tail).

% rule: generate several houses
%  AttributeList => [number, color, nationality, cigarettes, pet]
generateEmptyHouses(Result, Count, AttributeList) :-
	generateEmptyHouses([], Count, Result, 1, AttributeList).

generateEmptyHouses(HouseList, 0, HouseList, _Number, _AttributeList).
generateEmptyHouses(HouseList, Count, Result, Number, AttributeList) :-

	% generate a house
	generateEmptyHouse(House, AttributeList),

	%setAttribute(House, number, Number),

	% increment Number
	ModifyNumber is Number + 1,

	% put house in a house list
	append(HouseList, [House], ModifyHouseList),

	% increment count
	ModifyCount is Count - 1,

	% go in the recursion
	generateEmptyHouses(ModifyHouseList, ModifyCount, Result, ModifyNumber, AttributeList).

% 1 constrain: we have 5 houses
constrain_1(HouseList, Count, AttributeList) :-
	generateEmptyHouses(HouseList, Count, AttributeList).

% Set a Constrain.
% Example: setConstrain(HouseList, nationality, englishman, color, red).
setConstrain(HouseList, Attribute_1, Value_1, Attribute_2, Value_2) :-
	member(House, HouseList),
	setConstrainForHouse(House, Attribute_1, Value_1, Attribute_2, Value_2).

% Set a Constrain.
% Example: setConstrain(HouseList, nationality, englishman, color, red).
setConstrain_2(HouseList, Attribute_1, Value_1, Attribute_2, Value_2) :-
	member(House, HouseList),
	setConstrainForHouse_2(House, Attribute_1, Value_1, Attribute_2, Value_2).


% set only, the variable is unbound
setAttribute(House, Attribute, Value) :-
	member([Attribute, Var], House),
	(
	    not(atom(Var)),
	    not(number(Var))
	),
	member([Attribute, Value], House).

% In die eine Richtung schauen
setConstrainForHouse(House, Attribute_1, Value_1, Attribute_2, Value_2) :-
	member([Attribute_2, Elem], House),
	Elem == Value_2,
	setAttribute(House, Attribute_1, Value_1).

% In die Andere Richtung schauen
setConstrainForHouse(House, Attribute_1, Value_1, Attribute_2, Value_2) :-
	member([Attribute_1, Elem], House),
	Elem == Value_1,
	setAttribute(House, Attribute_2, Value_2).

% In die eine Richtung schauen
setConstrainForHouse_2(House, Attribute_1, Value_1, Attribute_2, Value_2) :-
	member([Attribute_2, Elem], House),
	(   not(atom(Elem)), not(number(Elem))),
	setAttribute(House, Attribute_1, Value_1),
	setAttribute(House, Attribute_2, Value_2).


% In die Andere Richtung schauen
setConstrainForHouse_2(House, Attribute_1, Value_1, Attribute_2, Value_2) :-
	member([Attribute_1, Elem], House),
	(   not(atom(Elem)), not(number(Elem))),
	setAttribute(House, Attribute_2, Value_2),
	setAttribute(House, Attribute_1, Value_1).



% Exmaple: (HouseList, color, green, number, ResultValue)
getValueByIdent(HouseList, IdentAttribute, IdentValue, ResultAttribute, ResultValue) :-
	member(House, HouseList),
	member([IdentAttribute, CurrentValue], House),
	IdentValue == CurrentValue,
	member([ResultAttribute, ResultValue], House),
	(
	    atom(ResultValue);
	    number(ResultValue)
	).

% get a house
getHouseByIdent(HouseList, Attribute, Value, House):-
	member(House, HouseList),
	member([Attribute, Value], House).

% TODO: Analysieren!!!
leftOf([House_A, House_B |_], House_A, House_B).
leftOf([_| HouseList], House_A, House_B) :-
	leftOf(HouseList, House_A, House_B).

nextTo(HouseList, House_A, House_B) :-
	leftOf(HouseList, House_A, House_B); leftOf(HouseList, House_B, House_A).

% Change position in the house list
neighbour(HouseList, House_1, House_2) :-
	leftOf(HouseList, House_1, House_2).

% House in the middle: TODO: Generisch!!!
middleHouse([_, _, House, _, _], House).

% Das grüne Haus ist direkt rechts vom weißen Haus.
%----------------------------------------------------
% Inet Variante: Das grüne Haus ist direkt links vom weißen Haus.
% Verfolge erstmal die Inet Variante, fuer besseren Loesungsvergleich!
setConstrainOnNeighbour(HouseList) :-

	% House Nummer des linken houses beschaffen
	getValueByIdent(HouseList, color, green, number, GreenHouseNumber),

	% ivory house number
	IvoryHouseNumber is GreenHouseNumber + 1,

	% Nummer oder Farbe fuer das rechte Haus setzten
	setConstrain(HouseList, color, ivory, number, IvoryHouseNumber).

% Umgekehrte Richtung, falls es oben nicht geklappt hat!
setConstrainOnNeighbour(HouseList) :-

	% House Nummer des linken houses beschaffen
	getValueByIdent(HouseList, color, ivory, number, IvoryHouseNumber),

	% ivory house number
	GreenHouseNumber is IvoryHouseNumber -1,

	% Nummer oder Farbe fuer das rechte Haus setzten
	setConstrain(HouseList, color, green, number, GreenHouseNumber ),





printOutput([]).
printOutput(HouseList) :-
	[Head | Tail] = HouseList,
	write(Head), format('\n', []), format('\n', ["----------------"]),
	printOutput(Tail).

% TODO: Gesetzte Parameter durch das Constrain, duerfen nicht
% umgeschrieben werden!!!
%1 .. 15 constrains
test(HouseList) :-

	% 1) 5 houses, attributes ->  [number, color, nationality, drink cigarettes, pet]
	constrain_1(HouseList, 5, [number, color, nationality, drink, cigarettes, pet]),

	% 2) The Englishman lives in the red house.
	setConstrain_2(HouseList, nationality, englishman, color, red),

	% 3) The Spaniard owns the dog.
	setConstrain_2(HouseList, nationality, spaniard, pet, dog),

	% 4) Coffee is drunk in the green house.
	setConstrain_2(HouseList, color, green, drink, coffee),

	% 5) The Ukrainian drinks tea.
	setConstrain_2(HouseList, nationality, ukrainian, drink, tea),

        % TODO: 6) The green house is immediately to the right of the ivory house.
	% Inet Variante: Das grüne Haus ist direkt links vom weißen Haus.
	% Das grüne Haus ist direkt rechts vom weißen Haus.
	% Inet: das grüne Haus ist direkt links vom weißen Haus
	getHouseByIdent(HouseList, color, green, House_Left),
	getHouseByIdent(HouseList, color, ivory, House_Right),
	leftOf(HouseList, House_Left, House_Right),

	% 7) The Old Gold smoker owns the snake.
	setConstrain_2(HouseList, cigarettes, oldGold, pet, snake),

	% 8) Kools are smoked in the yellow house.
	setConstrain_2(HouseList, cigarettes, kools, color, yellow),

	% Milk is drunk in the middle/3 house.
	%setConstrain_2(HouseList, number, 3, drink, milk),
	getHouseByIdent(HouseList, drink, milk, House_Middle),
	middleHouse(HouseList, House_Middle),

	% 10) The Norwegian lives in the first house on the left.
	setConstrain_2(HouseList, nationality, nowegian, number, 1),

	% TODO: 11) The man who smokes Chesterfields lives in the house next to the man with the fox.
	% Der Mann, der Chesterfields raucht, wohnt neben dem Mann mit dem Fuchs.
	getHouseByIdent(HouseList, cigarettes, chesterfield, House_From_Chesterfield),
	getHouseByIdent(HouseList, pet, fox, House_From_Fox),
	neighbour(HouseList, House_From_Chesterfield, House_From_Fox),

	% TODO: 12) Kools are smoked in the house next to the house where the horse is kept.
	% Die Marke Kools wird geraucht im Haus neben dem Haus mit dem Pferd.
	getHouseByIdent(HouseList, cigarettes, kools, House_From_Kools),
	getHouseByIdent(HouseList, pet, horse, House_Horse),
	neighbour(HouseList, House_From_Kools, House_Horse),

	% 13) The Lucky Strike smoker drinks orange juice.
	setConstrain_2(HouseList, cigarettes, luckyStrike, drink, orangeJuice),

	% 14) The Japanese smoke Parliaments.
	setConstrain_2(HouseList, cigarettes, parliaments, nationality, japanese),

	% 15)TODO:  The Norwegian lives next to the blue house.
	getHouseByIdent(HouseList, nationality, norwegian, House_Nor),
	getHouseByIdent(HouseList, color, blue, House_Blue),
	nextTo(HouseList, House_Nor, House_Blue).
