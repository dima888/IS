package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import model.Playboard;
import adt.Node;

/**
 * This class is to asses of a node.
 * We define here our heuristics
 * @author foxhound
 *
 */
public class Assessment {

	
	/**
	 * Method asses a board with our positions <br>
	 * The higher our return value, the better for player 1 with this board constellation
	 * @param board - our playboard
	 * @param player_1_token - 
	 * @param player_2_token - 
	 * @return int 
	 */
	public int toAssess(Node node, String player_1_token, String player_2_token) {
		
		Playboard board = node.getPlayboard();		
		List<Integer> assesList = new ArrayList<Integer>();
		
		System.out.println("In Asses: " + board.getStruct());
		
		
		switch (node.getPlayboard().getFreePositionCount()) {
		case 8: assesList.add(assesOneSetToken(board, player_1_token, player_2_token));	
		case 6: assesList.add(assesThreeSetToken(board, player_1_token, player_2_token)); 
		case 4: assesList.add(assesfiveSetToken(board, player_1_token, player_2_token)); 
		case 2: assesList.add(assesSevenSetToken(board, player_1_token, player_2_token));  
		case 0: assesList.add(assesFullBoard(board, player_1_token, player_2_token)); 

		default:
			//System.out.println("Heuristik not define");
			break;
		}
		
		if (assesList.isEmpty()) {
			return -777777777;
		}
		
		Collections.sort(assesList);				
		int asses = assesList.get(assesList.size() - 1);		
		node.setAssessment(asses);	
		
		// say player here, were he was to click
		
		return asses;
	}
	
	

	
//==========================================================================================================================================
//																Heuristics	
//==========================================================================================================================================
	
	private int assesOneSetToken(Playboard board, String player_1_token, String player_2_token) {
		//
		if ( board.getStruct().get(board.getMIDDLE()).compareTo(player_1_token) == 0) {
			System.err.println("Use assesOneSetToken heuristic");
			return 10;
		} 
		return -10;
	}
	
	private int assesThreeSetToken(Playboard board, String player_1_token, String player_2_token) {		
		
		if ( board.getStruct().get(board.getLEFT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getUP_RIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN_RIGHT()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
				return 20;											
		}		
		
		
		if ( board.getStruct().get(board.getRIGHT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getUP_LEFT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN_LEFT()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
			return 20;											
		}
		
		if ( board.getStruct().get(board.getUP()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getDOWN_RIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN_LEFT()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
			return 20;											
		}
		
		if ( board.getStruct().get(board.getDOWN()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getUP_RIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getUP_LEFT()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
			return 20;											
		}
		
		if ( board.getStruct().get(board.getUP_LEFT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getDOWN()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getRIGHT()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
			return 20;											
		}
		
		if ( board.getStruct().get(board.getRIGHT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getLEFT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN_LEFT()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
			return 20;											
		}
		
		if ( board.getStruct().get(board.getDOWN_RIGHT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getLEFT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getUP()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
			return 20;											
		}
		
		if ( board.getStruct().get(board.getDOWN_LEFT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getRIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getUP()).compareTo(player_1_token) == 0 ) ) {
			System.out.println("Use assesThreeSetToken heuristic");
			return 20;											
		}
		
		
		return -20;
	}
	
	private int assesfiveSetToken(Playboard board, String player_1_token, String player_2_token) {
		return -1;
	}
	
	private int assesSevenSetToken(Playboard board, String player_1_token, String player_2_token) {
		return -1;
	}
	
	private int assesFullBoard(Playboard board, String player_1_token, String player_2_token) {
		return -1;
	}
	
}





















