package algorithm;

import adt.Node;
import adt.Tree;

/**
 * This class is to asses of a node.
 * We define here our heuristics
 * @author foxhound
 *
 */
public class Assessment {
	
	private int win_value = 1000;
	private int loose_value = -100000;
	private int in_next_step_loose_value = -50000;
	private int neutral_value = 500;
	private int assesment_count = 0;
	
	private int current_best = 0;
	
	
	private Tree tree;
	
	/**
	 * Method asses a board with our positions <br>
	 * The higher our return value, the better for current player with this board constellation
	 * node - current node to assess
	 * current_player_token - 
	 * @return int 
	 */
	public int toAssess(Node node, String current_player_token, Tree tree) {
		
		this.tree = tree;		
				
		//System.out.println("Assesment count: " + assesment_count++);
						
		String fiend = "";
		if (current_player_token == "Red") {
			fiend = "Blue";
		}
		if (current_player_token == "Blue") {
			fiend = "Red";
		}				
		
		
		// if you can win now? -> win!!!
		if (canWin(node, current_player_token)) {
			node.setAssessment(win_value);
			node.setBest(win_value);			
			return win_value;
		}		
		
		// asses loose situation
		//current_best = node.getBest();
		if (looseHeuristic(node, fiend)) {			
			node.setAssessment(loose_value);
			return loose_value;
		}
		
		// TODO: asses lose situation in a step
//		if (looseInAStepHeuristic(node, fiend)) {
//			node.setAssessment(in_next_step_loose_value);
//			node.setBest(in_next_step_loose_value);
//			return in_next_step_loose_value;
//		}

		node.setAssessment(neutral_value);
		node.setBest(neutral_value);
		return neutral_value;
	}	
	
	private boolean canWin(Node node, String current_player_token) {
		
		if (node.getParentID() == 1) {
			if ( node.getPlayboard().haveLine(current_player_token) ) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * If true -> you loose
	 * @param node
	 * @return
//	 */
//	private boolean looseHeuristic(Node node, String fiend_token) {
//		if ( node.getPlayboard().haveLine(fiend_token) ) {
//			return true;
//		}
//		
//		if (node.getID() == 1) {
//			return false;
//		} else {
//			return looseHeuristic(tree.getNode(node.getParentID()), fiend_token);
//		}
//	}
	
	
	// das vorletzte element, genau das kind von root darf keine halb linie haben!
	private boolean looseHeuristic(Node current_node, String fiend_token) {
		
		if (current_node.getParentID() == 1) {
			if ( current_node.getPlayboard().haveHalfLine(fiend_token) ) {			
			return true;
			}
		}
		
		if ( current_node.getPlayboard().haveLine(fiend_token) ) {
			return true;
		}

		if (current_node.getID() == 1) {
			return false;
		} else {
			return looseHeuristic(tree.getNode(current_node.getParentID()), fiend_token);
		}
		
	
	}
	
	/**
	 * Work good, wrong recursiv call	
	 * If true -> you loose in next step
	 * @param node
	 * @param fiend_token
	 * @return
	 */
//	public boolean looseInAStepHeuristic(Node node, String fiend_token) {
//		if ( node.getPlayboard().haveHalfLine(fiend_token) ) {			
//			return true;
//		}
//		
//		if (node.getID() == 1) {
//			return false;
//		} else {
//			return looseHeuristic(tree.getNode(node.getParentID()), fiend_token);
//		}		
//	}
	
	public boolean looseInAStepHeuristic(Node node, String fiend_token) {
	if ( node.getPlayboard().haveHalfLine(fiend_token) ) {			
		return true;
	}
	
	if (node.getID() == 1) {
		return false;
	} else {
		return looseInAStepHeuristic(tree.getNode(node.getParentID()), fiend_token);
	}		
}
}





















