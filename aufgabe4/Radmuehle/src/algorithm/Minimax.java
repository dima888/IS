package algorithm;

import java.util.ArrayList;
import java.util.List;

import adt.Node;
import adt.Tree;

/**
 * 
 * This class implement the MiniMax with alpha / beta - cut algorithm
 * @author foxhound
 *
 *    An jedem Blattknoten wird die Bewertung berechnet.
 *	- Für jeden Maximumknoten wird während der Suche der aktuell größteWert der bisher
 *	  traversierten Nachfolger in alpha gespeichert.
 *	- Für jeden Minimumknoten wird während der Suche der aktuell kleinsteWert der bisher
 *	  traversierten Nachfolger in beta gespeichert.
 *	- Ist an einem Minimumknoten k der aktuelle Wert beta <= alpha, so kann die Suche unter k
 *	  beendet werden. Hierbei ist alpha der größte Wert eines Maximumknotens im Pfad von
 *	  derWurzel zu k.
 *	- Ist an einem Maximumknoten l der aktuelle Wert alpha >= beta, so kann die Suche unter l
 *	  beendet werden. Hierbei ist beta der kleinste Wert eines Minimumknotens im Pfad von
 *	  derWurzel zu l.
 *
 */
public class Minimax {

	private Assessment assesment = new Assessment();
	private Tree tree;
	
	int infinity_max = 1;
	int infinity_min = -1;
	
	public Minimax(Tree tree) {
		this.tree = tree;
	}
	
	public void setTree(Tree tree) {
		this.tree = tree;
	}
	
	/**
	 * Max
	 * @param node
	 * @param alpha
	 * @param beta 
	 */
	public int maxAB(Node node, int alpha, int beta, String current_player_token) {		
		if ( node.isLeaf() ) {			
			return assesment.toAssess(node, current_player_token, tree);
		}
		
		int best = infinity_min;
		//node.setBest(best);				
			
		// forall (S := Succ(node))
		List<Integer> allSuccessorNodeIDs = succ(node);
		for (Integer current_node_id : allSuccessorNodeIDs) {
			
			// current node
			Node current_node = tree.getNode(current_node_id);
			
			if (best > alpha) {
				alpha = best;
				current_node.setAlpha(alpha);
			}
			
			int val = minAB(current_node, node.getAlpha(), node.getBeta(), current_player_token);
			
			if ( val > best ) {
				best = val;
				node.setBest(val);
			}
			
			if ( best >= beta ) {
				return best;
			}
		}
		return best;
	}
	
	/**
	 * Min
	 * @param node
	 * @param alpha
	 * @param beta
	 */
	public int minAB(Node node, int alpha, int beta, String current_player_token) {
		if ( node.isLeaf() ) {
			return assesment.toAssess(node, current_player_token, tree);
		}
		
		int best = infinity_max;
		//node.setBest(best);			
		
		// forall (S := Succ(node))
		List<Integer> allSuccessorNodeIDs = succ(node);
		for (Integer current_node_id : allSuccessorNodeIDs) {
			
			// current node
			Node current_node = tree.getNode(current_node_id);
			
			if ( best > beta ) {
				current_node.setBeta(best);				
				beta = best;
			}
			
			int val = maxAB(tree.getNode(current_node_id), node.getAlpha(), node.getBeta(), current_player_token);
			
			if ( val < best ) {
				current_node.setBest(val);
				best = val;
			}
			
			if ( alpha >= best ) {
				return best;
			}		
		}		
		return best;
	}
	
	
	/**
	 * Method get all successor ID's from a Node
	 * @param node - Our Node
	 * @return List<Integer>
	 */
	private List<Integer> succ(Node node) {
		List<Integer> result = new ArrayList<Integer>();		
		succ(node, result);								
		return result;		
	}
	
	/**
	 * Helper Method for succ/1
	 * @param node
	 * @param result
	 * @return
	 */
	private List<Integer> succ(Node node, List<Integer> result) {		
		// recursion break;
		if ( node.isLeaf_2() ) {			
			return result;
		}
		
		List<Integer> childrens = node.getChildren();
		
		// add all childs // success nodes
		result.addAll(childrens);
		
		for (Integer id : childrens) {
			Node current_node = tree.getNode(id);
			succ(current_node, result);
		}				
		return result;		
	}
}




























