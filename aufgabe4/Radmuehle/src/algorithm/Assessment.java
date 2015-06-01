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

	private  String message = "";
	
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
		
		//System.out.println("In Asses: " + board.getStruct());
		
		
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
		if (!message.isEmpty()) {
			System.out.println(message);
			message = "";
		}
		
		
		return asses;
	}
	
	

	
//==========================================================================================================================================
//																Heuristics	
//==========================================================================================================================================
	
	private int assesOneSetToken(Playboard board, String player_1_token, String player_2_token) {
		//
		if ( board.getStruct().get(board.getMIDDLE()).compareTo(player_1_token) == 0) {
			System.err.println("Use assesOneSetToken heuristic");
			message = Integer.toString(board.getMIDDLE()); 
			return 10;
		} 
		return -10;
	}
	
	private int assesThreeSetToken(Playboard board, String player_1_token, String player_2_token) {		
		if ( board.getStruct().get(board.getLEFT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getUP_RIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN_RIGHT()).compareTo(player_1_token) == 0 ) ) {
			//System.out.println("Use assesThreeSetToken heuristic");
			message = "if Blue: " + board.getLEFT() + " then click on " + Integer.toString(board.getUP_RIGHT()) + " or " + Integer.toString(board.getDOWN_RIGHT()); 
				return 20;											
		}		
	
		if ( board.getStruct().get(board.getRIGHT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getUP_LEFT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN_LEFT()).compareTo(player_1_token) == 0 ) ) {
			//System.out.println("Use assesThreeSetToken heuristic");
			message = "if Blue: " + board.getRIGHT() + " then click on " + Integer.toString(board.getUP_LEFT()) + " or " + Integer.toString(board.getDOWN_LEFT());
			return 20;											
		}
		
		if ( board.getStruct().get(board.getUP()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getDOWN_RIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN_LEFT()).compareTo(player_1_token) == 0 ) ) {
			//System.out.println("Use assesThreeSetToken heuristic");
			message  = "if Blue: " + board.getUP() + " then click on " + Integer.toString(board.getDOWN_RIGHT()) + " or " + Integer.toString(board.getDOWN_LEFT());
			return 20;											
		}
		
		if ( board.getStruct().get(board.getDOWN()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getUP_RIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getUP_LEFT()).compareTo(player_1_token) == 0 ) ) {
			//System.out.println("Use assesThreeSetToken heuristic");
			message = "if Blue: " + board.getDOWN() + " then click on " + Integer.toString(board.getUP_RIGHT()) + " or " + Integer.toString(board.getUP_LEFT());
			return 20;											
		}
		
		if ( board.getStruct().get(board.getUP_LEFT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getDOWN()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getRIGHT()).compareTo(player_1_token) == 0 ) ) {
			//ystem.out.println("Use assesThreeSetToken heuristic");
			message = "if Blue: " + board.getUP_LEFT() + " then click on " + Integer.toString(board.getDOWN()) + " or " + Integer.toString(board.getRIGHT());
			return 20;											
		}
		
		// Fehler korrigieren
		if ( board.getStruct().get(board.getUP_RIGHT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getLEFT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getDOWN()).compareTo(player_1_token) == 0 ) ) {
			//System.out.println("Use assesThreeSetToken heuristic");
			message = "if Blue: " + board.getUP_RIGHT() + " then click on " + Integer.toString(board.getLEFT()) + " or " + Integer.toString(board.getDOWN());
			return 20;											
		}
		
		if ( board.getStruct().get(board.getDOWN_RIGHT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getLEFT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getUP()).compareTo(player_1_token) == 0 ) ) {
			//System.out.println("Use assesThreeSetToken heuristic");
			message = "if Blue: " + board.getDOWN_RIGHT() + " then click on " + Integer.toString(board.getLEFT()) + " or " + Integer.toString(board.getUP());
			return 20;											
		}
		
		if ( board.getStruct().get(board.getDOWN_LEFT()).compareTo(player_2_token) == 0 && ( board.getStruct().get(board.getRIGHT()).compareTo(player_1_token) == 0 || board.getStruct().get(board.getUP()).compareTo(player_1_token) == 0 ) ) {
			//System.out.println("Use assesThreeSetToken heuristic");
			message = "if Blue: " + board.getDOWN_LEFT() + " then click on " + Integer.toString(board.getRIGHT()) + " or " + Integer.toString(board.getUP());
			return 20;											
		}
		return -20;
	}
	
	private int assesfiveSetToken(Playboard board, String player_1_token, String player_2_token) {
		
		
		List<Integer> player_2_positions = new ArrayList<Integer>();
		
		// Take all player 2 token
		for (Entry<Integer, String> elem : board.getStruct().entrySet()) {
			
			if ( elem.getValue().compareTo(player_2_token)  == 0) {
				
				// look from blue the neighbors
				List<Integer> neighbor_from_blue = board.getNeighbors(elem.getKey());
				neighbor_from_blue.remove(board.getMIDDLE());
				
				for (Integer current_neighbor_from_blue_id : neighbor_from_blue) {
					
					List<Integer> neighbor_from_blue_neighbor = board.getNeighbors(current_neighbor_from_blue_id);
					neighbor_from_blue_neighbor.remove(board.getMIDDLE());
					
					for (Integer current_neighbor_from_blue_neighbor_id : neighbor_from_blue_neighbor) {
						
					}
					
				}
				
				
				
				// look the neighbors from blues neighbors 
				
			}
			
		}
		
		// look on player 2 neighbors
		
		
		return -1;
	}
	
	private int assesSevenSetToken(Playboard board, String player_1_token, String player_2_token) {
		return -1;
	}
	
	private int assesFullBoard(Playboard board, String player_1_token, String player_2_token) {
		return -1;
	}
	
}





















