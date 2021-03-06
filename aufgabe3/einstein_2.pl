:- use_module(library(clpfd)).

% http://www.swi-prolog.org/pldoc/man?section=summary-lib-clp-clpfd

%1) There are five houses in a row, each of a different
% color (red, green, ivory, blue, yellow)
% nationalty (Englishman, Spaniard, Ukrainian, Norwegian, Japanese),
% pet (dog, snake, zebra, fox horse),
% drinks (tea, orange juice, milk, water, coffee),
% cigarettes (Old Gold, Kools, Chesterfield, Lucky Strike, Parliament).
%
% 2)  The Englishman lives in the red house.
% 3)  The Spaniard owns the dog.
% 4)  Coffee is drunk in the green house.
% 5)  The Ukrainian drinks tea. %
% 6)  The green house is immediately to the right of the ivory house.
% 7)  The Old Gold smoker owns the snake.
% 8)  Kools are smoked in the yellow house.
% 9)  Milk is drunk in the middle house.
% 10) The Norwegian lives in the first house on the left.
% 11) The man who smokes Chesterfields lives in the house next to the man with the fox.
% 12) Kools are smoked in the house next to the house where the horse is kept.
% 13) The Lucky Strike smoker drinks orange juice.
% 14) The Japanese smoke Parliaments.
% 15) The Norwegian lives next to the blue house.

solve :-
	valid_domain([Red, Green, Ivory, Blue, Yellow], 1, 5),
	all_different([Red, Green, Ivory, Blue, Yellow]),

	valid_domain([Englishman, Spaniard, Ukrainian, Norwegian, Japanese], 1, 5),
	all_different([Englishman, Spaniard, Ukrainian, Norwegian, Japanese]),

	valid_domain([Dog, Snake, Zebra, Fox, Horse], 1, 5),
	all_different([Dog, Snake, Zebra, Fox, Horse]),

	valid_domain([Tea, Orange_Juice, Milk, Water, Coffee], 1, 5),
	all_different([Tea, Orange_Juice, Milk, Water, Coffee]),

	valid_domain([Old_Gold, Kools, Chesterfield, Lucky_Strike, Parliament], 1, 5),
	all_different([Old_Gold, Kools, Chesterfield, Lucky_Strike, Parliament]),

	label([Englishman, Spaniard, Ukrainian, Norwegian, Japanese]),
	%label([Dog, Snake, Zebra, Fox, Horse]),

	% 2)  The Englishman lives in the red house.
	Englishman #= Red,

	% 3)  The Spaniard owns the dog.
	Spaniard #= Dog,

	% 4)  Coffee is drunk in the green house.
	Green #= Coffee,

	% 5)  The Ukrainian drinks tea.
	Ukrainian #= Tea,

	% 6)  The green house is immediately to the
	% right of the ivory house.
	Ivory #= Green -1,

	% 7)  The Old Gold smoker owns the snake.
	Old_Gold #= Snake,

	% 8)  Kools are smoked in the yellow house.
	Yellow #= Kools,

	% 9)  Milk is drunk in the middle house.
	Milk #= 3,

	% 10) The Norwegian lives in the first house on the left.
	Norwegian #= 1,

	% 11) The man who smokes Chesterfields lives in the
	% house next to the man with the fox.
	neighbor(Chesterfield, Fox),

	% 12) Kools are smoked in the house next to the
	% house where the horse is kept.
	neighbor(Kools, Horse),

	% 13) The Lucky Strike smoker drinks orange juice.
	Lucky_Strike #= Orange_Juice,

	% 14) The Japanese smoke Parliaments.
	Japanese #= Parliament,

	% 15) The Norwegian lives next to the blue house.
	neighbor(Norwegian, Blue),

	Colors = [[Red, red], [Green, green], [Ivory, ivory], [Blue, blue], [Yellow, yellow]],
	Nationalitys = [[Englishman, engishman], [Spaniard, spaniard], [Ukrainian, ukrainian], [Norwegian, norwegian], [Japanese, japanese]],
	Pets = [[Dog, dog], [Snake, snake], [Zebra, zebra], [Fox, fox], [Horse, horse]],
	Drinks = [[Tea, tea], [Orange_Juice, orange_juice], [Milk, milk], [Water, water], [Coffee, coffe]],
	Cigarettes = [[Old_Gold, old_gold], [Kools, kools], [Chesterfield, chesterfields], [Lucky_Strike, lucky_Strike], [Parliament, parliaments]],

	build(Colors, Nationalitys, Pets, Drinks, Cigarettes, 1, House_1),
	build(Colors, Nationalitys, Pets, Drinks, Cigarettes, 2, House_2),
	build(Colors, Nationalitys, Pets, Drinks, Cigarettes, 3, House_3),
	build(Colors, Nationalitys, Pets, Drinks, Cigarettes, 4, House_4),
	build(Colors, Nationalitys, Pets, Drinks, Cigarettes, 5, House_5),

	print([House_1, House_2, House_3, House_4, House_5]).

valid_domain(List, Min, Max):-
  List ins Min..Max.

neighbor(X, Y) :-
    (X #= (Y - 1)) #\/ (X #= (Y + 1)).

build(Colors, Nationalitys, Pets, Drinks, Cigarettes, HouseNumber, Result) :-
	member([HouseNumber, Color], Colors),
	member([HouseNumber, Nationality], Nationalitys),
	member([HouseNumber, Pet], Pets),
	member([HouseNumber, Drink], Drinks),
	member([HouseNumber, Cigarette], Cigarettes),
	Result = [Color, Nationality, Pet, Drink, Cigarette].

print(HouseList) :-
	[House_1, House_2, House_3, House_4, House_5] = HouseList,
	write([color, nationality, pet, drink, cigarette]), nl,
	nl, write(House_1), nl, nl, write(House_2), nl, nl, write(House_3), nl, nl, write(House_4), nl, nl, write(House_5), nl.

