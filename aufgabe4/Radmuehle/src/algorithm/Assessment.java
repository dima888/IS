package algorithm;

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
	private boolean full_board = false;
	
	/**
	 * Method asses a board with our positions <br>
	 * The higher our return value, the better for player 1 with this board constellation
	 * @param board - our playboard
	 * @param player_1_token - 
	 * @param player_2_token - 
	 * @return int 
	 */
	public int toAssess(Node node, String player_1_token, String player_2_token) {
		
		
		int asses = -100;
		Playboard board = node.getPlayboard();		
		
		switch (board.getFreePositionCount()) {
			case 8: asses = assesOneSetToken(board, player_1_token, player_2_token); break;	
			case 6: asses = assesThreeSetToken(board, player_1_token, player_2_token); break;
			case 4: asses = assesfiveSetToken(board, player_1_token, player_2_token); break;
			case 3: asses = assesFullBoard(board, player_1_token, player_2_token);  break;

			default:
				//System.out.println("Heuristik not define");
				break;
			}		
		return asses;
	}
	
	

	
//==========================================================================================================================================
//																Heuristics	
//==========================================================================================================================================
	
	private int assesOneSetToken(Playboard board, String player_1_token, String player_2_token) {
		if ( board.getStruct().get(board.getMIDDLE()).compareTo(player_1_token) == 0) {
			//System.err.println("Use assesOneSetToken heuristic");
			System.out.println(board);
			return 10;
		} 
		return -10;
	}
	
	private int assesThreeSetToken(Playboard board, String player_1_token, String player_2_token) {
		int player_2_token_position = -1;
		
		// find the player 2 token position
		for (Entry<Integer, String> map : board.getStruct().entrySet()) {
			
			if ( map.getValue().compareTo(player_2_token) == 0 ) {
				player_2_token_position = map.getKey();
				
				int super_position_for_player1 = board.toFace(player_2_token_position) +1;
				
				if (super_position_for_player1 == 9) {  
					super_position_for_player1 = 1;
				}
				
				if ( board.getStruct().get(super_position_for_player1) == player_1_token ) {
					System.err.println("Use assesThreeSetToken heuristic");
					System.out.println(board);
					return 20;
				}				
			}
		}
		return -20;
	}
	
	private int assesfiveSetToken(Playboard board, String player_1_token, String player_2_token) {	
		// win situation
				for (Entry<Integer, String> map : board.getStruct().entrySet()) {
					if ( map.getValue().compareTo(player_1_token) == 0 && map.getKey() != board.getMIDDLE()) {				
						int to_face = board.toFace(map.getKey());
					  	if ( board.getStruct().get(to_face).compareTo(player_1_token) == 0 ) {
							System.out.println(board);
							return 1000;
						}
					}
				}
		 
		// second optimal situation
		for (Entry<Integer, String> map : board.getStruct().entrySet()) {			
			if (map.getValue().compareTo(player_2_token) == 0) {				
				String next = board.getStruct().get(map.getKey() + 1);
				if (next.compareTo(player_2_token) == 0) {
					String next_2 = board.getStruct().get(map.getKey() + 2);
					if ( map.getKey() + 2 == board.getMIDDLE() ) {
						return -30;
					}					
					if (next_2.compareTo(player_1_token) == 0) {
						System.err.println("Use assesfiveSetToken heuristic");
						System.out.println(board);
						return 30;						
					}
				}
			}			
		}
		return -30;
	}

	private int assesFullBoard(Playboard board, String player_1_token, String player_2_token) {
		// win situation
		if ( board.haveLine(player_1_token) ) {
			System.out.println("Board is full");
			System.out.println(board);
			return 1000;			
		}
		
		int count = 0;
		// evry token without middle, have a nighbor
		for (Entry<Integer, String> map: board.getStruct().entrySet()) {
			if (map.getKey() != board.getMIDDLE()) {
				
				// current p1 token
				if (map.getValue().compareTo(player_1_token) == 0) {
					
					// take all neighbors from current p1 token
					List<Integer> neighbors = board.getNeighbors(map.getKey());
					
					for (Integer current_neighbor_position : neighbors) {
						
						if (current_neighbor_position != board.getMIDDLE()) {
							if (board.getStruct().get(current_neighbor_position).compareTo(player_2_token) == 0) {
								count++;
							}
						} 					
					}					
				}				
			}
		}
		
		if (count >= 3) {
			System.err.println("Use assesFullBoard heuristic");
			System.out.println(board);
			return 40;
		}
		return -1;
	}
	
}





















