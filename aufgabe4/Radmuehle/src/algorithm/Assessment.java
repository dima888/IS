package algorithm;

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
	 * The higher our return value, the better for current player with this board constellation
	 * node - current node to assess
	 * current_player_token - 
	 * @return int 
	 */
	public int toAssess(Node node, String current_player_token) {
		
		if (node.getPlayboard().haveLine(current_player_token)) {
			node.setAssessment(1000);
			return 1000;
		} else {
			node.setAssessment(-1000);
			return -1000;
		}
	}	
}





















