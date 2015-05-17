%There are five houses in a row, each of a different color(red, green, ivory, blue, yellow)
% and inhabited by men of different nationalities (Englishman, Spaniard,
% Ukrainian, Norwegian, Japanese), with different pets (dog, snake,
% zebra, fox horse), drinks (tea, orange juice, milk, water, coffee), and
% cigarettes (Old Gold, Kools, Chesterfield, Lucky Strike, Parliament).

% House
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

	% set automatic a house number
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

% Example: setConstrain(HouseList, nationality, englishman, color, red).
setConstrain(HouseList, Attribute_1, Value_1, Attribute_2, Value_2, House) :-
	member(House, HouseList),
	member([Attribute_1, Value_1], House),
	member([Attribute_2, Value_2], House).

setConstrain(HouseList, Attribute_1, Value_1, Attribute_2, Value_2, House) :-
	member(House, HouseList),
	member([Attribute_2, Value_2], House),
	member([Attribute_1, Value_1], House).

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

% get a house by ident
% exmaple getHouseByIdent(HouseList, color, blue, YourHouse)
getHouseByIdent(HouseList, Attribute, Value, House):-
	member(House, HouseList),
	member([Attribute, Value], House).

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
	setConstrain(HouseList, color, green, number, GreenHouseNumber ).

% Constrain, house nr, must deferent about 1
setHouseTupleConstrain(HouseList, LeftHouseAttribute_1, LeftHouseValue_1, RightHouseAttribute_2, RightHouseValue_2) :-

	% Exmaple: (HouseList, color, green, number, ResultValue)
	getValueByIdent(HouseList, LeftHouseAttribute_1, LeftHouseValue_1, number, LeftHouseNumber),
	getValueByIdent(HouseList, RightHouseAttribute_2, RightHouseValue_2, number, RightHouseNumber),

	% Constrain
	Equal is LeftHouseNumber + 1,
	Equal == RightHouseNumber.



% Der Mann, der Chesterfields raucht, wohnt neben dem Mann mit dem Fuchs.
% Die Marke Kools wird geraucht im Haus neben dem Haus mit dem Pferd.
% 15)TODO:  The Norwegian lives next to the blue house.
setNeighbourContrain(HouseList, Attribute_1, Value_1, Attribute_2, Value_2) :-
		(
		setHouseTupleConstrain(HouseList, Attribute_1, Value_1, Attribute_2, Value_2) ;
		setHouseTupleConstrain(HouseList, Attribute_2, Value_2, Attribute_2, Value_2)
		).


twoHouses(LeftHouse, RightHouse, [LeftHouse, RightHouse|_]).
twoHouses(LeftHouse, RightHouse,[_|HouseList]) :- twoHouses(LeftHouse, RightHouse, HouseList).

% A steht neben B
neighbour(House1, House2, HouseList) :- twoHouses(House1, House2, HouseList); twoHouses(House2, House1, HouseList).

% A ist das erste Haus
firstHouse(House, [House|_]).

% A steht in der Mitte
middle(House, [_,_,House,_,_]).

printOutput([]).
printOutput(HouseList) :-
	[Head | Tail] = HouseList,
	write(Head), format('\n', []), format('\n', ["----------------"]),
	printOutput(Tail).


%1 .. 15 constrains
solveDyn(HouseList) :-

	% 1) 5 houses, attributes ->  [color, nationality, drink cigarettes, pet]
	constrain_1(HouseList, 5, [color, nationality, drink, cigarettes, pet]),

	% 2) The Englishman lives in the red house.
	setConstrain(HouseList, nationality, englishman, color, red, _),

	% 3) The Spaniard owns the dog.
	setConstrain(HouseList, nationality, spaniard, pet, dog, _),

	% 4) Coffee is drunk in the green house.
	setConstrain(HouseList, color, green, drink, coffee, _),

	% 5) The Ukrainian drinks tea.
	setConstrain(HouseList, nationality, ukrainian, drink, tea, _),

        % The green house is immediately to the right of the ivory house.
	setConstrain(HouseList, color, ivory, _, _, _),
	getHouseByIdent(HouseList, color, green, GreenHouse),
	getHouseByIdent(HouseList, color, ivory, IvoryHouse),
	twoHouses(IvoryHouse, GreenHouse, HouseList),

	% 7) The Old Gold smoker owns the snake.
	setConstrain(HouseList, cigarettes, oldGold, pet, snake, _),

	% 8) Kools are smoked in the yellow house.
	setConstrain(HouseList, cigarettes, kools, color, yellow, KoolsHouse),

	% Milk is drunk in the middle/3 house.
	setConstrain(HouseList, drink, milk, number, 3, MilkHouse),
	middle(MilkHouse, HouseList),

	% 10) The Norwegian lives in the first house on the left.
	setConstrain(HouseList, nationality, norwegian, number, 1, NorwegianHouse),
	firstHouse(NorwegianHouse, HouseList),

	% The man who smokes Chesterfields lives in the house	next to the man with the fox.
	% Der Mann, der Chesterfields raucht, wohnt neben dem Mann mit dem Fuchs.
	setConstrain(HouseList, cigarettes, chesterfields, _, _, ChesterFieldHouse),
	setConstrain(HouseList, pet, fox, _, _, FoxHouse),
	neighbour(ChesterFieldHouse,FoxHouse,HouseList),

	% Kools are smoked in the house next to the house where the horse is kept.
	% Die Marke Kools wird geraucht im Haus neben dem Haus mit dem Pferd.
	setConstrain(HouseList, pet, horse, _, _, HorseHouse),
	neighbour(HorseHouse, KoolsHouse, HouseList),

	% 13) The Lucky Strike smoker drinks orange juice.
	setConstrain(HouseList, cigarettes, luckyStrike, drink, orangeJuice, _),

	% 14) The Japanese smoke Parliaments.
        setConstrain(HouseList, cigarettes, parliaments, nationality, japanese, _),

	% 15) The Norwegian lives next to the blue house.
	setConstrain(HouseList, color, blue, _, _, BlueHouse),
	neighbour(NorwegianHouse, BlueHouse, HouseList).


