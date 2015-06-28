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
	
	private int win_value = 700;	
	private int dont_loose_value = 500;
	private int loose_value = -50;
	
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
				
		//System.out.println("Assesment count: " + assesment_count++);
						
		String fiend_token = getFiend(current_player_token);

		
		// selectiere alle wege die zur niederlage fueren oder zur einer halblinie vom feind!
		List<Integer> assess_list = new ArrayList<>();
		//if (looseHeuristic(node, fiend_token, assess_list )) {
		if (looseHeuristic(node, fiend_token)) {
			node.setAssessment( loose_value + getIdenticator(node.getDeep()) );
			//node.setBest( loose_value + getIdenticator(node.getDeep()) );
			node.setMesseage("looseHeuristic: " + assess_list);
			return loose_value + getIdenticator(node.getDeep());
		}
		
//		if (dont_loose_assessment(node, current_player_token, fiend_token) ) {
//			// TODO: Bewertung anpassen!!!
//			node.setAssessment(dont_loose_value - getIdenticator(node.getDeep()) );
//			node.setBest(dont_loose_value  - getIdenticator(node.getDeep()));
//			node.setMesseage("I never lose :D " );
//			return dont_loose_value - getIdenticator(node.getDeep());
//		}
		
		
			
		// we see a win
//		if (see_win(node, current_player_token)) {			
//			node.setAssessment(win_value - getIdenticator(node.getDeep()) );
//			//node.setBest(win_value  - getIdenticator(node.getDeep()));
//			node.setMesseage("see_win: " );
//			return win_value - getIdenticator(node.getDeep());
//		}
		
		if (node.getPlayboard().haveLine(fiend_token)) {
			
		}
		
		
		if ( node.getPlayboard().haveLine(current_player_token) ) {		
			if (see_win(node, fiend_token, win_value - getIdenticator(node.getDeep()))  ) {
				// sieg ohne niederlage gefunden
				node.setAssessment(win_value - getIdenticator(node.getDeep()) );
				return dont_loose_value - getIdenticator(node.getDeep());
			}
		}	

		
		// try to make a half line
//		if (try_win(node, current_player_token)) {		
//			node.setAssessment(fast_win_value - getIdenticator(node.getDeep()));
//			node.setBest(fast_win_value - getIdenticator(node.getDeep()));
//			node.setMesseage("try_win");
//			return fast_win_value - getIdenticator(node.getDeep());
//		}

	node.setAssessment(neutral_value);
	node.setBest(neutral_value);
	node.setMesseage("Found nothing! " );
	return neutral_value;
	}	
	
	private String getFiend(String current_player_token) {
		if (current_player_token == "Red") {
			return "Blue";
		}
		if (current_player_token == "Blue") {
			return "Red";
		}	
		throw new IllegalArgumentException("getFiend");
	}

	/*
	 * Heuristic Feinds nachbar ist ein Feind 
	 */
	private boolean fiend_neighbor_heuristic(Node node, String fiend_token) {
		
		if (node.getParentID() == 1) {
			return true;
		} else {
			Playboard playboard = node.getPlayboard();
			
			for (Map.Entry<Integer, String> map : node.getPlayboard().getStruct().entrySet() ) {
				
				// found a fiend
				if (map.getValue() == fiend_token) {				
					// get neighbors without the middle from current fiend
					List<Integer> neighbors_with_middle = playboard.getNeighbors(map.getKey());
					List<Integer> neighbors_without_middle = new ArrayList<>();
					for (Integer position : neighbors_with_middle) {
						if (position != node.getPlayboard().getMIDDLE()) {
							neighbors_without_middle.add(position);
						}
					}
					
					// look if neighbor from current fiend also fiend is
					int neighbor_1 = neighbors_without_middle.get(0);
					int neighbor_2 = neighbors_without_middle.get(1);
					
					if (playboard.getStruct().get(neighbor_1) == fiend_token) {
						return fiend_neighbor_heuristic(tree.getNode(node.getParentID()), fiend_token);
					}
					
					if (playboard.getStruct().get(neighbor_2) == fiend_token) {
						return fiend_neighbor_heuristic(tree.getNode(node.getParentID()), fiend_token);
					}		 
				}
				
			}
		}
		return false;
	}

	/*
	 * Heuristic: wir haben eine halb linie mit clear token am ende
	 * der nachbar von clear token sei ein fiend, wird positiver bewertet
	 */
	private boolean haveHalfLineClearNeighborIsAFiend(Node node, String current_player_token, String fiend_token) {
		
		Playboard playboard = node.getPlayboard();
		
		int clear_token_position = playboard.getClearTokenPositionInHalfLine(current_player_token);
		
		if ( clear_token_position == -1 || clear_token_position == 9) {
			return false;
		}
		
		// hold neighbors from clear token without the 9
		List<Integer> neighbors_with_middle = playboard.getNeighbors(clear_token_position);
		List<Integer> neighbors_without_middle = new ArrayList<>();
		for (Integer pos : neighbors_with_middle) {
			if  (pos != playboard.getMIDDLE() ) {
				neighbors_without_middle.add(pos);
			}
		}
		
		// check if a neighboar our player token! if yes, very good!
		int neighbor_1 = neighbors_without_middle.get(0);
		int neighbor_2 = neighbors_without_middle.get(1);
		
		if (playboard.getStruct().get(neighbor_1) == fiend_token) {
			return true;
		}
		
		if (playboard.getStruct().get(neighbor_2) == fiend_token) {
			return true;
		}		
		return false;
	}

	private boolean haveHalfLineWithFiendHeuristic(Node node, String current_player_token, String fiend_token) {
		if ( node.getPlayboard().haveHalfLineWithFiend(current_player_token, fiend_token)) {
			return true;
		}
		
		if (node.getID() == 1) {
			return false;
		} else {
			return haveHalfLineWithFiendHeuristic(tree.getNode(node.getParentID()), current_player_token,  fiend_token);
		}
	}

	private boolean clear_have_neighboar(Node node, String current_player_token) {
		
		System.out.println("clear_have_neighboar node: " + node);
		
		Playboard playboard = node.getPlayboard();
		
		int clear_token_position = playboard.getClearTokenPositionInHalfLine(current_player_token);
		
		if ( clear_token_position == -1 || clear_token_position == 9) {
			return false;
		}
		
		// hold neighbors from clear token without the 9
		List<Integer> neighbors_with_middle = playboard.getNeighbors(clear_token_position);
		List<Integer> neighbors_without_middle = new ArrayList<>();
		for (Integer pos : neighbors_with_middle) {
			if  (pos != playboard.getMIDDLE() ) {
				neighbors_without_middle.add(pos);
			}
		}
		
		// check if a neighboar our player token! if yes, very good!
		int neighbor_1 = neighbors_without_middle.get(0);
		int neighbor_2 = neighbors_without_middle.get(1);
		
		if (playboard.getStruct().get(neighbor_1) == current_player_token) {
			return true;
		}
		
		if (playboard.getStruct().get(neighbor_2) == current_player_token) {
			return true;
		}		
		return false;
	}

	/**
	 * If true -> you loose
	 * @param node
	 * @return
//	 */
//	public boolean looseHeuristic(Node node, String fiend_token, List<Integer> assesses_of_the_way) {
//		//if ( node.getPlayboard().haveLine(fiend_token) ||  node.getPlayboard().haveHalfLine(fiend_token)) {
//		if (node.getPlayboard().haveHalfLine(fiend_token)) {
//		//if (node.getPlayboard().haveLine(fiend_token)) {
//			assesses_of_the_way.add(node.getAssessment());
//			return true;
//		}
//		
//		assesses_of_the_way.add(node.getAssessment());
//		
//		if (node.getID() == 1) {
//			return false;
//		} else {
//			return looseHeuristic(tree.getNode(node.getParentID()), fiend_token, assesses_of_the_way);
//		}
//	}
	
//	private boolean looseHeuristic(Node current_node, String fiend_token, List<Integer> assesses_of_the_way) {
//		
////		if (current_node.getParentID() == 1) {
////			if ( current_node.getPlayboard().haveLine(fiend_token) ) {			
////			return true;
////			}
////			return false;
////		}
//		
//		if ( current_node.getPlayboard().haveHalfLine(fiend_token) ) {
//			return true;
//		}
//		return false;
//
////		if (current_node.getID() == 1) {
////			return false;
////		} else {
////			return looseHeuristic(tree.getNode(current_node.getParentID()), fiend_token, assesses_of_the_way);
////		}
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
	
	public boolean looseHeuristic(Node node, String fiend_token, Tree tree) {
		//if ( node.getPlayboard().haveLine(fiend_token) ||  node.getPlayboard().haveHalfLine(fiend_token)) {
		if (node.getPlayboard().haveHalfLine(fiend_token)) {
		//if (node.getPlayboard().haveLine(fiend_token)) {
			return true;
		}
		
		if (node.getID() == 1) {
			return false;
		} else {
			return looseHeuristic(tree.getNode(node.getParentID()), fiend_token, tree);
		}
	}
	
	
	// das vorletzte element, genau das kind von root darf keine halb linie haben!
	private boolean dont_loose_assessment(Node current_node, String current_player_token, String fiend_token) {
		
		// wir chillen in der mitte, ist gut! 
//		if (current_node.getPlayboard().getStruct().get(9) == current_player_token) {
//			return true;
//		}
		
		// feind hat keine line, ist gut! 
		if (!current_node.getPlayboard().haveHalfLine(fiend_token) && (!tree.neighborWasMyTokenHeuristic(new ArrayList<>(Arrays.asList(current_node)), current_player_token)) ) {
			return true;
		} 
		
//		if (!current_node.getPlayboard().haveLine(fiend_token)) {
//			return true;
//		}
		
		// meiner hat keinen eigenen nachbar ist gut
//		if (!tree.neighborWasMyTokenHeuristic(new ArrayList<>(Arrays.asList(current_node)), current_player_token)) {
//			return true;
//		}
//		
//		// der Feind hat einen eigenen Nachbar, ist gut! 
//		if (tree.neighborWasMyTokenHeuristic(new ArrayList<>(Arrays.asList(current_node)), fiend_token)) {
//			return true;
//		}
		return false;
	}

	// we see here a win situation
	public boolean see_win(Node node, String current_player_token, int currentMaxAssess) {
							
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
		
		
		
//		if ( node.getPlayboard().haveLine(current_player_token) ) {			
//		return true;
//		}	
//		return false;
	}
	
	public boolean try_win(Node node, String current_player_token) {
		if ( node.getPlayboard().haveHalfLine(current_player_token) ) {			
			return true;
		}
		
		// loose of the way to win
//		if (looseHeuristic(node, getFiend(current_player_token), new ArrayList<Integer>() )) {
//			return false;
//		}
		
		if (node.getAssessment() < 0) {
			return false;
		}
		
		if (tree.neighborWasMyTokenHeuristic(new ArrayList<>(Arrays.asList(node)), current_player_token)) {
			return false;
		}
		
		if (tree.nonNeighborHeuristic(new ArrayList<>(Arrays.asList(node)), current_player_token)) {
			return false;
		}
		
		if (node.getID() == 1) {
			return false;
		} else {
			return try_win(tree.getNode(node.getParentID()), current_player_token);
		}	
	}
	
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





















