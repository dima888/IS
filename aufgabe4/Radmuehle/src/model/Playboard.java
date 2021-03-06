package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is our playboard for the game "Radmuehle"
 * seen the game under this link: http://www.saelde-und-ere.at/Hauptseite/Arbeitsgruppen/Spiel/Spielregeln/Radmuehle.html
 * 
 * This class is a composite object for the Game.java class
 * @author foxhound
 *
 */
public class Playboard {
	
	int maxTokenOnPlayBoard = 9;
	
	// bourd struct
	private Map<Integer, String> board = new HashMap<Integer, String>();
	private Map<Integer, ArrayList<Integer>> neighbor = new HashMap<Integer, ArrayList<Integer>>();
	
	public boolean specialRule = false;
	
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
	
	private String currentMessage = "";
	
	// define boards token
	private String clearToken = "clear";

	public void initializeBoard() {
		for (int i = 1; i < 10; i++) {
			board.put(i, clearToken);
		
			// set neighbors
			setNeighbor(i);
		}
	}
	
	public int getUP() {
		return UP;
	}

	public int getUP_LEFT() {
		return UP_LEFT;
	}

	public int getUP_RIGHT() {
		return UP_RIGHT;
	}

	public int getLEFT() {
		return LEFT;
	}

	public int getMIDDLE() {
		return MIDDLE;
	}

	public int getRIGHT() {
		return RIGHT;
	}

	public int getDOWN() {
		return DOWN;
	}

	public int getDOWN_LEFT() {
		return DOWN_LEFT;
	}

	public int getDOWN_RIGHT() {
		return DOWN_RIGHT;
	}
	
	/**
	 * Method get you to face id to your param
	 * @param position
	 * @return
	 */
	public int toFace(int position) {
		
		if ( position == MIDDLE || position > 9 || position < 1 ) {
			throw new IllegalArgumentException("Position: " +  position + " ist not legitim");
		}
		
		switch (position) {
		case UP: return DOWN;
		case UP_RIGHT: return DOWN_LEFT;
		case RIGHT: return LEFT;
		case DOWN_RIGHT: return UP_LEFT;
		case DOWN: return UP;
		case DOWN_LEFT: return UP_RIGHT;
		case LEFT: return RIGHT;
		case UP_LEFT: return DOWN_RIGHT;

		default:
			break;
		}
		
		return 9;
	}

	/**
	 * Getter for playboard
	 * @return Map
	 */
	public Map<Integer, String> getStruct() {
		//System.out.println(board);
		return board;
	}
	
	/**
	 * Method get you the current message of the game
	 * @return
	 */
	public String getCurrentMessage() {
		return currentMessage;
	}
	
	public void setCurrentMessage(String currentMessage) {
		this.currentMessage = currentMessage;
	}
	
	/**
	 * Method get you a list with neighbors position to a position
	 * @param position - your current position
	 * @return List<Integer>
	 */
	public List<Integer> getNeighbors(int position) {
		return neighbor.get(position);
	}
	
	/**
	 * Method give you count of free positions
	 * @return int
	 */
	public int getFreePositionCount() {		
		int count = 0;		
		for (Entry<Integer, String> elem : board.entrySet()) {
			if (elem.getValue().compareTo(clearToken) == 0)  {
				count ++;
			}
		}
		return count;
	}
	
	/**
	 * Method get the clear token 
	 * @return String
	 */
	public String getClearToken() {
		return clearToken;
	}
	
	/**
	 * Method create a copy of this object
	 * @return
	 */
	public Playboard getCopy() {
		
		Playboard result = new Playboard();
		result.initializeBoard();
		
		for (Entry<Integer, String> elem : board.entrySet()) {
			
			if (elem. getValue().compareTo(getClearToken()) != 0)  {				
				result.addToken(elem.getValue(), elem.getKey());				
			}			
		}				
		return result;
	}
	
	public void setPlayboard(Map<Integer, String> board) {
		this.board = board;
	}
	
	/**
	 * Method add a token on board
	 * @param token - your specific token
	 * @param boardPosition - 1..9 the position where you want your token
	 */
	public boolean addToken(String token, int toBoardPosition) {
		// Precondtion
		if (toBoardPosition > 9 || toBoardPosition < 1) {
			currentMessage = "A Board Position: fromBoardPosition v toBoardPosition" + " out of box! Legitim positions was 1..9";
//			System.err.println(currentMessage);
			return false;
		}
		
		if ( board.get(toBoardPosition).compareTo(clearToken) != 0 ) {
			currentMessage = "Board Position: " + toBoardPosition + " is not free";
//			System.err.println(currentMessage);
			return false;
		}
		
		board.put(toBoardPosition, token);
		
		// check for winner
		winner(token);
		return true;
	}
	
	/**
	 * Method move a token from a position to a other position on board
	 * @param token - your specific token
	 * @param boardPosition - 1..9 the position where you want your token
	 */
	public boolean moveToken(int fromBoardPosition, int toBoardPosition) {
		/*
		 * Preconditions
		 */
		if (fromBoardPosition > 9 || fromBoardPosition < 1 || toBoardPosition > 9 || toBoardPosition < 1) {
			currentMessage = "A Board Position: fromBoardPosition v toBoardPosition" + " out of box! Legitim positions was 1..9";
//			System.err.println(currentMessage);
			return false;
		}
		
		if (toBoardPosition > 9 || toBoardPosition < 1) {
			currentMessage = "To Board Position: " + toBoardPosition + " out of box! Legitim positions was 1..9";
//			System.err.println(currentMessage);
			return false;
		}
		
		if ( board.get(fromBoardPosition).compareTo(clearToken) == 0 ) {
			currentMessage = "Board Position: " + fromBoardPosition + " have none token!";
//			System.err.println("Board Position: " + fromBoardPosition + " have none token!");
			return false;
		}
		
		if ( board.get(toBoardPosition).compareTo(clearToken) != 0 ) {
			currentMessage = "Board Position: " + toBoardPosition + " is not free";
//			System.err.println(currentMessage);
			return false;
		}
		
		// neighbor check
		if (!isNeighbor(fromBoardPosition, toBoardPosition)) {
			currentMessage = "fromBoardPosition and toBoardPosition + (" + fromBoardPosition + ", " + toBoardPosition + ",)  was not neighbors!!!!";
			//System.err.println(currentMessage);
			specialRule = true;
			return false;
		}
		
		// constitudet token
		String token = board.get(fromBoardPosition);
		 
		// clear old position of token
		board.put(fromBoardPosition, clearToken);
		
		// block new this of token
		board.put(toBoardPosition, token);
		
		// check for winner
		winner(token);
		return true;
	}
	
	/**
	 * Method checked if the player with this token win
	 * if player win, then give this Method your a system output "you win .... player token"
	 * @param token - player token
	 */
	private void winner(String token) {
		String winnerText = "Player with'" + token +"' token is the winner!!!!!!!!!!!!!!!!!!!";		
		if (haveLine(token)) {
			currentMessage = winnerText;
			//System.err.println(currentMessage);
		}		
	}
	
	/**
	 * Method check of line in playboard
	 * @param token
	 * @return
	 */
	public boolean haveLine(String token) {
		if ( board.get(UP).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN).compareTo(token) == 0) {
			return true;
		}
		
		if ( board.get(UP_LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_RIGHT).compareTo(token) == 0) {
			return true;
		}
		
		if ( board.get(UP_RIGHT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_LEFT).compareTo(token) == 0) {
			return true;
		}
		
		if ( board.get(LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(RIGHT).compareTo(token) == 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Method check of half line in playboard
	 * Exmaple: 1->Red, 9->Red, 5->clear
	 * @param token
	 * @return
	 */
	public boolean haveHalfLine(String token) {
		
		// 1
		if ( board.get(UP).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN).compareTo(clearToken) == 0) {
			return true;
		}		
		if ( board.get(UP).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(DOWN).compareTo(token) == 0) {
			return true;
		}		
		if ( board.get(UP).compareTo(clearToken) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN).compareTo(token) == 0) {
			return true;
		}
		
		// 2
		if ( board.get(UP_LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_RIGHT).compareTo(clearToken) == 0) {
			return true;
		}		
		if ( board.get(UP_LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(DOWN_RIGHT).compareTo(token) == 0) {
			return true;
		}		
		if ( board.get(UP_LEFT).compareTo(clearToken) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_RIGHT).compareTo(token) == 0) {
			return true;
		}
		
		// 3
		if ( board.get(UP_RIGHT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_LEFT).compareTo(clearToken) == 0) {
			return true;
		}
		if ( board.get(UP_RIGHT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(DOWN_LEFT).compareTo(token) == 0) {
			return true;
		}
		if ( board.get(UP_RIGHT).compareTo(clearToken) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_LEFT).compareTo(token) == 0) {
			return true;
		}
		
		// 4
		if ( board.get(LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(RIGHT).compareTo(clearToken) == 0) {
			return true;
		}
		if ( board.get(LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(RIGHT).compareTo(token) == 0) {
			return true;
		}
		if ( board.get(LEFT).compareTo(clearToken) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(RIGHT).compareTo(token) == 0) {
			return true;
		}		
		return false;
	}
	
	public boolean haveHalfLineWithFiend(String current_player_token, String fiend_token) {
		
		// 1
		if ( board.get(UP).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(DOWN).compareTo(fiend_token) == 0) {
			return true;
		}		
		if ( board.get(UP).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(fiend_token) == 0 && board.get(DOWN).compareTo(current_player_token) == 0) {
			return true;
		}		
		if ( board.get(UP).compareTo(fiend_token) == 0 && board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(DOWN).compareTo(current_player_token) == 0) {
			return true;
		}
		
		// 2
		if ( board.get(UP_LEFT).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(DOWN_RIGHT).compareTo(fiend_token) == 0) {
			return true;
		}		
		if ( board.get(UP_LEFT).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(fiend_token) == 0 && board.get(DOWN_RIGHT).compareTo(current_player_token) == 0) {
			return true;
		}		
		if ( board.get(UP_LEFT).compareTo(fiend_token) == 0 &&	board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(DOWN_RIGHT).compareTo(current_player_token) == 0) {
			return true;
		}
		
		// 3
		if ( board.get(UP_RIGHT).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(DOWN_LEFT).compareTo(fiend_token) == 0) {
			return true;
		}
		if ( board.get(UP_RIGHT).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(fiend_token) == 0 && board.get(DOWN_LEFT).compareTo(current_player_token) == 0) {
			return true;
		}
		if ( board.get(UP_RIGHT).compareTo(fiend_token) == 0 && board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(DOWN_LEFT).compareTo(current_player_token) == 0) {
			return true;
		}
		
		// 4
		if ( board.get(LEFT).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(RIGHT).compareTo(fiend_token) == 0) {
			return true;
		}
		if ( board.get(LEFT).compareTo(current_player_token) == 0 && board.get(MIDDLE).compareTo(fiend_token) == 0 && board.get(RIGHT).compareTo(current_player_token) == 0) {
			return true;
		}
		if ( board.get(LEFT).compareTo(fiend_token) == 0 && board.get(MIDDLE).compareTo(current_player_token) == 0 && board.get(RIGHT).compareTo(current_player_token) == 0) {
			return true;
		}		
		return false;
	}
	
	public int getClearTokenPositionInHalfLine(String token) {
		
		// 1
		if ( board.get(UP).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN).compareTo(clearToken) == 0) {
			return DOWN;
		}		
		if ( board.get(UP).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(DOWN).compareTo(token) == 0) {
			return MIDDLE;
		}		
		if ( board.get(UP).compareTo(clearToken) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN).compareTo(token) == 0) {
			return UP;
		}
		
		// 2
		if ( board.get(UP_LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_RIGHT).compareTo(clearToken) == 0) {
			return DOWN_RIGHT;
		}		
		if ( board.get(UP_LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(DOWN_RIGHT).compareTo(token) == 0) {
			return MIDDLE;
		}		
		if ( board.get(UP_LEFT).compareTo(clearToken) == 0 &&	board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_RIGHT).compareTo(token) == 0) {
			return UP_LEFT;
		}
		
		// 3
		if ( board.get(UP_RIGHT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_LEFT).compareTo(clearToken) == 0) {
			return DOWN_LEFT;
		}
		if ( board.get(UP_RIGHT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(DOWN_LEFT).compareTo(token) == 0) {
			return MIDDLE;
		}
		if ( board.get(UP_RIGHT).compareTo(clearToken) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(DOWN_LEFT).compareTo(token) == 0) {
			return UP_RIGHT;
		}
		
		// 4
		if ( board.get(LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(RIGHT).compareTo(clearToken) == 0) {
			return RIGHT;
		}
		if ( board.get(LEFT).compareTo(token) == 0 && board.get(MIDDLE).compareTo(clearToken) == 0 && board.get(RIGHT).compareTo(token) == 0) {
			return MIDDLE;
		}
		if ( board.get(LEFT).compareTo(clearToken) == 0 && board.get(MIDDLE).compareTo(token) == 0 && board.get(RIGHT).compareTo(token) == 0) {
			return LEFT;
		}		
		
		return -1;
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
	
	@Override
	public String toString() {
		return board.toString();
	}
}
