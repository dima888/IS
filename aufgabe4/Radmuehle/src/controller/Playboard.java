package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is our playboard for the game "Radmuehle"
 * seen the game under this link: http://www.saelde-und-ere.at/Hauptseite/Arbeitsgruppen/Spiel/Spielregeln/Radmuehle.html
 * @author foxhound
 *
 */
public class Playboard {
	
	int maxTokenOnPlayBoard = 9;
	
	// bourd struct
	private Map<Integer, String> board = new HashMap<Integer, String>();
	private Map<Integer, ArrayList<Integer>> neighbor = new HashMap<Integer, ArrayList<Integer>>();
	
	// define my board positions
	private final int UP = 1;
	private final int UP_LEFT = 8;
	private final int UP_RIGHT = 2;
	
	private final int LEFT = 7;
	private final int MIDDLE = 9;
	private final int RIGHT = 3;
	
	private final int DOWN = 5;
	private final int DOWN_LEFT = 6;
	private final int DOWN_RIGHT = 4;
	
	// define boards token
	private String clearToken = "clear";

	public void initializeBoard() {
		for (int i = 1; i < 10; i++) {
			board.put(i, clearToken);
		
			// set neighbors
			setNeighbor(i);
		}
	}
	
	/**
	 * Method add a token on board
	 * @param token - your specific token
	 * @param boardPosition - 1..9 the position where you want your token
	 */
	public void addToken(String token, int toBoardPosition) {
		// Precondtion
		if (toBoardPosition > 9 || toBoardPosition < 1) {
			throw new IllegalArgumentException("A Board Position: fromBoardPosition v toBoardPosition" + " out of box! Legitim positions was 1..9");
		}
		
		if ( board.get(toBoardPosition).compareTo(clearToken) != 0 ) {
			throw new IllegalArgumentException("Board Position: " + toBoardPosition + " is not free");
		}
		
		board.put(toBoardPosition, token);
		
		// check for winner
		winner(token);
	}
	
	/**
	 * Method move a token from a position to a other position on board
	 * @param token - your specific token
	 * @param boardPosition - 1..9 the position where you want your token
	 */
	public void moveToken(String token, int fromBoardPosition, int toBoardPosition) {
		/*
		 * Preconditions
		 */
		if (fromBoardPosition > 9 || fromBoardPosition < 1 || toBoardPosition > 9 || toBoardPosition < 1) {
			throw new IllegalArgumentException("A Board Position: fromBoardPosition v toBoardPosition" + " out of box! Legitim positions was 1..9");
		}
		
		if (toBoardPosition > 9 || toBoardPosition < 1) {
			throw new IllegalArgumentException("To Board Position: " + toBoardPosition + " out of box! Legitim positions was 1..9");
		}
		
		if ( board.get(fromBoardPosition).compareTo(clearToken) == 0 ) {			
			throw new IllegalArgumentException("Board Position: " + fromBoardPosition + " have none token!");			
		}
		
		if ( board.get(toBoardPosition).compareTo(clearToken) != 0 ) {
			throw new IllegalArgumentException("Board Position: " + toBoardPosition + " is not free");
		}
		
		// neighbor check
		if (!isNeighbor(fromBoardPosition, toBoardPosition)) {
			throw new IllegalArgumentException("fromBoardPosition and toBoardPosition + (" + fromBoardPosition + ", " + toBoardPosition + ",)  was not neighbors!!!!");
		}
		 
		// clear old position of token
		board.put(fromBoardPosition, clearToken);
		
		// block new this of token
		board.put(toBoardPosition, token);
		
		// check for winner
		winner(token);
	}
	
	/**
	 * Method checked if the player with this token win
	 * if player win, then give this Method your a system output "you win .... player token"
	 * @param token - player token
	 */
	private void winner(String token) {
		String winnerText = "Player with token: " + token + " is the winner!!!!!!!!!!!!!!!!!!!";
		
		if ( board.get(UP).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN).compareTo(token) == 0) {
			System.out.println(winnerText);
		}
		
		if ( board.get(UP_LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_RIGHT).compareTo(token) == 0) {
			System.out.println(winnerText);
		}
		
		if ( board.get(UP_RIGHT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_LEFT).compareTo(token) == 0) {
			System.out.println(winnerText);
		}
		
		if ( board.get(LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(RIGHT).compareTo(token) == 0) {
			System.out.println(winnerText);
		}
		
	}
	
	/**
	 * Method give you true back, if p_a and _b was neighbors
	 * @param position_a
	 * @param position_b
	 */
	private boolean isNeighbor(int position_a, int position_b) {		
		ArrayList<Integer> neighborList = neighbor.get(position_a);
		ArrayList<Integer> neighborList_2 = neighbor.get(position_b);
		
		if (neighborList.contains(position_b) || neighborList_2.contains(position_a)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method define the neighbors in bourd
	 * @param position - position from we look
	 */
	private void setNeighbor(int position) {
		if ( position == UP ) {
			neighbor.put(UP, new ArrayList<>(Arrays.asList(UP_LEFT, UP_RIGHT, MIDDLE)));
		}
				
		if ( position == UP_LEFT ) {
			neighbor.put(UP_LEFT, new ArrayList<>(Arrays.asList( UP, LEFT, MIDDLE)));
		}
		
		if ( position == UP_RIGHT ) {
			neighbor.put(UP_RIGHT, new ArrayList<>(Arrays.asList(UP, RIGHT, MIDDLE)));
		}
		
		if ( position == MIDDLE ) {
			neighbor.put(MIDDLE, new ArrayList<>(Arrays.asList(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)));
		}

		if ( position == DOWN ) {
			neighbor.put(DOWN, new ArrayList<>(Arrays.asList(DOWN_LEFT, DOWN_RIGHT, MIDDLE)));
		}

		if ( position == DOWN_LEFT ) {
			neighbor.put(DOWN_LEFT, new ArrayList<>(Arrays.asList(LEFT, DOWN, MIDDLE)));
		}

		if ( position == DOWN_RIGHT ) {
			neighbor.put(DOWN_RIGHT,new ArrayList<>(Arrays.asList(DOWN, RIGHT, MIDDLE)));
		}

		if ( position == LEFT ) {
			neighbor.put(LEFT, new ArrayList<>(Arrays.asList(UP_LEFT, DOWN_LEFT, MIDDLE)));
		}
		
		if ( position == RIGHT ) {
			neighbor.put(RIGHT, new ArrayList<>(Arrays.asList(DOWN_RIGHT, UP_RIGHT, MIDDLE)));
		}
	}
	
	public static void main(String[] args) {
		Playboard p = new Playboard();
		p.initializeBoard();
		System.out.println(p.board);
		
		p.addToken("X", 1);
		p.addToken("Y", 8);
		p.addToken("Y", 4);
		p.addToken("Y", 9);
		
		p.addToken("X", 3);
		
		System.out.println(p.board);
		
		//p.moveToken("X", 3, 4);
		
		
		
		System.out.println(p.board);
		System.out.println(p.neighbor);
	}
}
