
mystery([S, E, N, D], [M, O, R, E], [M, O, N, E, Y], Result) :-
	M = 1,

	permutation([0,1,2,3,4,5,6,7,8,9], [S, E, N, D, O, R, Y, Z, Q, X]),

	S =\= 0,
	S =\= E,  S =\= N,  S =\= D,  S =\= M,  S =\= O,  S =\= Y,  S =\= R,
	E =\= S,  E =\= N,  E =\= D,  E =\= M,  E =\= O,  E =\= Y,  E =\= R,
	N =\= S,  N =\= E,  N =\= D,  N =\= M,  N =\= O,  N =\= Y,  N =\= R,
	D =\= S,  D =\= E,  D =\= N,  D =\= M,  D =\= O,  D =\= Y,  D =\= R,
	M =\= S,  M =\= E,  M =\= N,  M =\= D,  M =\= O,  M =\= Y,  M =\= R,
	O =\= S,  O =\= E,  O =\= N,  O =\= D,  O =\= M,  O =\= Y,  O =\= R,
	R =\= S,  R =\= E,  R =\= N,  R =\= D,  R =\= M,  R =\= Y,  R =\= O,
	Y =\= S,  Y =\= E,  Y =\= N,  Y =\= D,  Y =\= M,  Y =\= R,  Y =\= O,

	SM is S+ M,
	MO is M + O,
	SM =\= MO,

	DE is D + E,
	DE =\= Y,

	Send is S*1000 + E*100 + N*10 + D,
	More is M*1000 + O*100 + R*10 + E,
	Money is M*10000 + O*1000 + N*100 + E*10 + Y,
	Money =:= Send + More,
	Result = Money.


sendMoreMoney(Result) :-
	mystery([S, E, N, D], [M, O, R, E], [M, O, N, E, Y], Result).
