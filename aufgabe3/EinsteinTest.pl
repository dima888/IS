% 1) There are five houses in a row, each of a different color
% (red, green, ivory, blue, yellow)
% and inhabited by men of different nationalities
% (Englishman, Spaniard, Ukrainian, Norwegian, Japanese),
% with different pets (dog, snake, zebra, fox horse),
% drinks (tea, orange juice, milk, water, coffee), and
% cigarettes (Old Gold, Kools, Chesterfield, Lucky Strike, Parliament).

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


% Das Raetsel
% [color, nationality, pet, drink cigarettes]
solveStatic(HouseList) :-

  % 5 Haeuser!
  HouseList = [_,_,_,_,_],

  % 2) The Englishman lives in the red house.
  member([red, englishman,_,_,_], HouseList),

  % 3) The Spaniard owns the dog
  member([_, spaniard, dog,_,_], HouseList),

  % 4) Coffee is drunk in the green house.
  member([green,_,_,coffee,_], HouseList),

  % 5) The Ukrainian drinks tea.
  member([_, ukrainian,_,tea,_], HouseList),

  % 6) The green house is immediately to the right of the ivory house.
  twoHouses([ivory,_,_,_,_],[green,_,_,_,_], HouseList),

  % 7) The Old Gold smoker owns the snake
  member([_,_,snake,_,oldGold], HouseList),

  % 8) Kools are smoked in the yellow house.
  member([yellow,_,_,_,kools], HouseList),

  % 9) Milk is drunk in the middle house
  middle([_,_,_,milk,_], HouseList),

  % 10) The Norwegian lives in the first house on the left
  firstHouse([_, norwegian,_,_,_], HouseList),

  % 11) The man who smokes Chesterfields lives in the house next to the man with the fox.
  neighbour([_,_,_,_,chesterfields],[_,_,fox,_,_], HouseList),

  % 12) Kools are smoked in the house next to the house where the horse is kept.
  neighbour([_,_,horse,_,_],[_,_,_,_,kools], HouseList),

  % 13) The Lucky Strike smoker drinks orange juice
  member([_,_,_,orangeJuice,luckyStrike], HouseList),

  % 14) The Japanese smoke Parliaments.
  member([_, japanese,_,_,parliaments], HouseList),

  % 15) The Norwegian lives next to the blue house
  neighbour([_,norwegian,_,_,_],[blue,_,_,_,_], HouseList).





