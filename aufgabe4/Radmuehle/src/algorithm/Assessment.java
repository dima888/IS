package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import model.Playboard;
import view.PlayboardView;
import adt.Node;
import adt.Tree;

/**
 * This class is to asses of a node.
 * We define here our heuristics
 * @author foxhound
 *
 */
public class Assessment {
	
	public static int win_value = 700;	
	private int dont_loose_value = 500;
	public static int loose_value = -170;
	
	private int neutral_value = 50;
	
	
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
		String fiend_token = getFiend(current_player_token);
		
		// selectiere alle wege die zur niederlage fueren oder zur einer halblinie vom feind!
		List<Integer> assess_list = new ArrayList<>();
		if (looseHeuristic(node, fiend_token)) {
			node.setAssessment( loose_value + getIdenticator(node.getDeep()) );
			node.setMesseage("looseHeuristic: " + assess_list);
			return loose_value + getIdenticator(node.getDeep());
		}
		
		// Sieg erkennen und positiv bewerten 
		if ( node.getPlayboard().haveLine(current_player_token) ) {		
			if (see_win(node, fiend_token, win_value - getIdenticator(node.getDeep()))  ) {
				// sieg ohne niederlage gefunden
				node.setAssessment(win_value - getIdenticator(node.getDeep()) );
				return dont_loose_value - getIdenticator(node.getDeep());
			}
		}	

	// Neutrale Bewertung! 
	node.setAssessment(neutral_value);
	node.setBest(neutral_value);
	node.setMesseage("Found nothing! " );
	return neutral_value;
	}	
	
	
	
	
	
	
	
//====================================================================================================================================
//													PRIVATE METHODEN
//====================================================================================================================================	
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
		
	// we see here a win situation
	private boolean see_win(Node node, String current_player_token, int currentMaxAssess) {
							
		if ( node.getPlayboard().haveLine(current_player_token) ) {			
			return false;
		}		
		
		if (node.getParentID() == 1) {						
			
			if (node.getAssessment() < currentMaxAssess) {
				node.setAssessment(currentMaxAssess);
			}
			
			return true;
		} else {
			return see_win(tree.getNode(node.getParentID()), current_player_token, currentMaxAssess);
		}	
	}
	
	/**
	 * Gibt den Feid zum Aktuellen Spieler 
	 * @param current_player_token - Aktueller Spieler 
	 * @return String 
	 */
	private String getFiend(String current_player_token) {
		if (current_player_token == "Red") {
			return "Blue";
		}
		if (current_player_token == "Blue") {
			return "Red";
		}	
		throw new IllegalArgumentException("getFiend");
	}

	/**
	 * Deep identificator 
	 * @param deep - current deep
	 * @return int
	 */
	private int getIdenticator(int deep) {
		switch (deep) {
		case 1: return 75;
		case 2: return 100;
		case 3: return 200;
		case 4: return 300;
		case 5: return 400;
		case 6: return 500;
		case 7: return 600;
		}
		throw new IllegalArgumentException("oO getIdenticator");
	}	
}





















