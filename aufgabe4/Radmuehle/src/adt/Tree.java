package adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import algorithm.Assessment;
import algorithm.Minimax;
import model.Playboard;

/**
 * This class generate a tree to our playboard
 * @author foxhound
 */
public class Tree {
	
	private String player_1_token = "Red";
	private String player_2_token = "Blue";

	// struct to save of nodes
	List<Node> tree = new ArrayList<Node>();
	
	// node ids
	private int node_id = 1;
	
	private final int UNDEFINED  = 77777777;		
	
	private Node root_node = null;
	
	private final int BEST = 1000;
	
	private final int ROOT_ID = 1;
	
	// deepest node a our tree
	Node deepestNode = null;
	
	/**
	 * Constructor
	 */
	public Tree() {		
		// add the root node
		
		createAndAddRootNode();
		//root_node.setPlayboard(board);
	}

//============================================================================================================
//													TREE GETTER
//============================================================================================================
	
	/**
	 * Method get you Node Object by id
	 * @param id - node id
	 * @return Node
	 */
	public Node getNode(int id) {		
		for (Node node : tree) {			
			if ( node.getID() == id ) {
				return node;
			}			
		}	
		System.err.println("Node with ID: " + id + " was not found");
		return null;
	}
	
	public List<Node>getStruct() {
		return tree;
	}
	
	/**
	 * Method get you the deepest value of tree
	 * @return int
	 */
	public int getMaxDeep() {
		int maxDeep = 0;
		
		for (Node node : tree) {
			int currentNodeDeep = node.getDeep();
			if ( currentNodeDeep> maxDeep ) {
				maxDeep = currentNodeDeep;
			}	
		}
		return maxDeep;
	}

	/**
	 * Method get you all nodes in a deep
	 * @param deep - deep of tree
	 * @return List<Node>
	 */
	public List<Node> getNodes(int deep) {
		List<Node> result = new ArrayList<Node>();		
		for (Node node : tree) {
			if (node.getDeep() == deep) {
				result.add(node);
			}
		}		
		return result;		
	}
		
	/**
	 * Tree add a node
	 * @param parent_node_id - parent node of new node in param node
	 * @param node
	 */
	public void addNewNode(int parent_id, int board_position) {
		node_id ++ ;
		
		// set the childeren
		Node parent_node = getNode(parent_id);
		parent_node.setChildren(node_id);		
		
		Node node = createNode();		
		setAttributes(node, node_id, parent_id, getNode(parent_id).getDeep() + 1, board_position);
		tree.add(node);
	}
	
	/**
	 * Method add a node under the parent id
	 * @param parent_id
	 * @param board_position - 
	 * @param node - node some you want add
	 */
	public void add(int parent_id, int board_position, Node node) {
		node_id ++ ;
		
		// set the childeren
		Node parent_node = getNode(parent_id);
		parent_node.setChildren(node_id);
		
		setAttributes(node, node_id, parent_id, getNode(parent_id).getDeep() + 1, board_position);
		tree.add(node);
	}
	
	/**
	 * Method set the param attributes in a node
	 * @param node - 
	 * @param node_id - 
	 * @param parent_id - 
	 * @param deep - 
	 * @param board_position - 
	 */
	public void setAttributes(Node node, int node_id, int parent_id, int deep, int board_position) {
		node.setID(node_id);
		node.setParentID(parent_id);
		node.setDeep(deep);
		node.setBoardPosition(board_position);
	}
	
	/**
	 * Method create you a new node
	 * @return
	 */
	public Node createNode() {
		return new Node();
	}
	
	/**
	 * Method generate your a tree with all possible capabilities to a deep
	 * @param board - Playboard Object
	 * @param deep -  Max deep of this tree
	 */
	public List<Node> generateTree(Playboard board, int deep, String player_token_step) {		
		deep--;
		
		// edit root
		root_node.setPlayboard(board);
		int current_free_positions = board.getFreePositionCount();
		
		// current clear positions in a board
		
		int runs = 0;
		
		// player ident
		String playerToken = "";
		
		// condition for right deep of tree
		while (runs++ < deep) {					
			
			if (runs % 2 != 0) {
				playerToken = player_token_step;
			} else {
				
				if (player_token_step == player_1_token) {
					playerToken = player_2_token;
				} else {
					playerToken = player_1_token;
				}
			}
			
			// free position in board allow! 3 => 3 positions was clear!
			if ( (current_free_positions--) <= 3 ) {				
				// Jump token status				
				generateNodes(board, runs, playerToken, true);																			
			} else {
				// insert token status						
				generateNodes(board, runs, playerToken, false);			
			}
		}							
		return tree;
	}
	
	/**
	 * Helper Method for generateTree
	 * Method add nodes in our tree
	 * @param status - if true -> move status else insert status
	 * @return List<Node> - 
	 */
	private void generateNodes(Playboard board, int currentDeep, String playerToken, boolean status) {		

		// run over all nodes in a deep
		List<Node> nodeList = getNodes(currentDeep);		
		for (Node node : nodeList) {
			
			// allocate all positions in board 			
			if (status) {		
				allocateAllPositionsWithMove(node, playerToken);								
			} else {
				allocateAllPositions(node, playerToken);
			}						
		}
	}
	
	/**
	 * Helper for generateNodes move status
	 * @param node
	 * @param playerToken
	 */
	private void allocateAllPositionsWithMove(Node node, String playerToken) {
		
		// positions of my tokens
		List<Integer> fromMeSetTokenPositions = new ArrayList<Integer>();		
		
		// run over the board from current node and fill fromMeSetTokenPositions
		for (Entry<Integer, String> elem : node.getPlayboard().getStruct().entrySet()) {
			
			// suplly from me set tokens
			if ( elem.getValue() == playerToken ) {
				fromMeSetTokenPositions.add(elem.getKey());				
			}			
		}
				
		for (Integer current_board_position : fromMeSetTokenPositions) {
			// suplly neighbors to current_board_position
			List<Integer> allNeighbors = node.getPlayboard().getNeighbors(current_board_position);
			
			// suplly legitim neighbors to current_board_position
			List<Integer> legitimNeighbors = new ArrayList<Integer>();
			
			// filter for only accessible/legitim positions
			for (Integer current_neighbor_position : allNeighbors) {
				
				if (node.getPlayboard().getStruct().get(current_neighbor_position).compareTo(node.getPlayboard().getClearToken()) == 0) {
					legitimNeighbors.add(current_neighbor_position);
				}
			}
			
			// create new nodes, how we have neighbors to a current_board_position
			for (Integer currentNeighbor : legitimNeighbors) {
				
				// initialize new board for new node
				Playboard modifyBoard = node.getPlayboard().getCopy();
				
				// Move the token on other, but legitim position
				modifyBoard.moveToken(current_board_position, currentNeighbor);
				
				// create legitim nodes
				Node currentNode = createNode();
				currentNode.setDeep(node.getDeep() + 1);					
				currentNode.setPlayboard(modifyBoard);
				currentNode.setParentID(node.getID());
				
				// set deepest node
				deepestNode = currentNode;
									
				// add a node in our tree
				add(node.getID(), currentNeighbor, currentNode);				
			}
		}		
	}

	/**
	 * Helper Method for generateNodes insert status 
	 * @param node - 
	 * @param board - 
	 * @param token - 
	 */
	private void allocateAllPositions(Node node, String token) {		
		
		// not allowed position list
		List<Integer> visitPositions = new ArrayList<Integer>();				
		
		// run over the board from current node
		for (Entry<Integer, String> elem : node.getPlayboard().getStruct().entrySet()) {
			
			// modifiy board only, if this position was clear!
			if (elem. getValue().compareTo(node.getPlayboard().getClearToken()) == 0)  {
				
				if ( (!visitPositions.contains(elem.getKey()) ) ) {
					
					// initialize new board
					Playboard modifyBoard = node.getPlayboard().getCopy();											
					
					// add token and to mark this step
					modifyBoard.addToken(token, elem.getKey());
					
					// save visited position
					visitPositions.add(elem.getKey());
																	
					// create legitim nodes
					Node currentNode = createNode();
					currentNode.setDeep(node.getDeep() + 1);					
					currentNode.setPlayboard(modifyBoard);
					currentNode.setParentID(node.getID());
					
					// set deepest node
					deepestNode = currentNode;
					
					// add a node in our tree
					add(node.getID(), elem.getKey(), currentNode);
				}											
			}
		}
	}
			
	/**
	 * Create a root node an add him in tree
	 */
	private void createAndAddRootNode() {		
		Node rootNode = createNode();		
		rootNode.setDeep(1);		
		rootNode.setParentID(UNDEFINED);		
		//rootNode.setChildren(-1);
		rootNode.setID(node_id);		
		rootNode.setBoardPosition(UNDEFINED);		
		tree.add(rootNode);		
		root_node = rootNode;		
		deepestNode = root_node;
	}
	
	/**
	 * Method print you the next best way
	 */
	public Node printNextBestStep(int best) {
				
		int pos_back = 2;
		
		try {
			List<Node> node_list = getWay(best);		
			System.err.println("----------------------------- Step Beginn -----------------------------------");
			Thread.sleep(50);
			//System.out.println(node_list.get(node_list.size() -pos_back));
			System.out.println(node_list);
			Thread.sleep(50);
			System.err.println("----------------------------- Step End -----------------------------------");			
			return node_list.get(node_list.size() -pos_back);
			
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public void printAllBestWays(int best) {
		// take all leafs from tree
		List<Node> leafs = getAllLeafs();
		
		List<Node> best_node_list = new ArrayList<Node>();
		
		// search here the best nodes
		for (Node node : leafs) {
			if ( node.getAssessment() == best ) {	
				best_node_list.add(node);
			}
		}
	
		for (Node node : best_node_list) {
			
			// save running way from node
			List<Node> running_way = getWayHelper(node);
			
			// set assess on next node step
			setRightAssessOnNextNode(running_way);
			
//			System.err.println("----------------------------- Possible Way Start -----------------------------------");
//			try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
//			System.out.println(running_way);
//			try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
//			System.err.println("----------------------------- Possible Way End -----------------------------------\n");			
		}
	}
	
	
	private void setRightAssessOnNextNode(List<Node> runnig_ways) {
			
		int sum = 0;
		for (Node node : runnig_ways) {						
			
			if (node.getParentID() == 1) {
				
				if ( sum > node.getAssessment() ) {
					node.setAssessment(sum);
				}
				
				// leave the iteration
				break;
				
			}						
			sum += node.getAssessment();
		}
		
	}
	
	
	/**
	 * Method give you the best step
	 * the last elem in the list is the next best step!
	 * @return List<Node>
	 */
	private List<Node> getWay(int best) {
		
		// take all leafs from tree
		List<Node> leafs = getAllLeafs();
		
		// define best node
		Node best_node = null;
		
		List<Node> best_node_list = new ArrayList<Node>();
		
		// search here the best nodes
		for (Node node : leafs) {
			if ( node.getAssessment() == best ) {	
				best_node_list.add(node);
			}
		}
		
		System.err.println("Best: \n" + best_node_list);
		
		//int shortest_node_id = 999_999_999;
		// here we take the shortes way to the win
//		for (Node node : best_node_list) {
//			int current_node_id = node.getID(); 
//			if (current_node_id < shortest_node_id) {
//				shortest_node_id = current_node_id;
//			}
//		}
		
		//System.err.println(best_node_list);
		//System.out.println("best leaf size: " + best_node_list.size());
		
		// TODO: Experements
		
		// shortest win situation
		//best_node = getNode(shortest_node_id);
		
		// TODO: verhindert sieg
		// longest win situationn		
		best_node = best_node_list.get(best_node_list.size() -1);
		
		// TODO: verhindert niederlage
		//best_node = best_node_list.get(0);
		
		// save here the best way
		return getWayHelper(best_node);	
	}
	
	/**
	 * Method get you all nodes without children (Leafs)
	 * @return
	 */
	public List<Node> getAllLeafs() {
		List<Node> result = new ArrayList<Node>();
		
		for (Node node : tree) {
			if (node.isLeaf()) {
				result.add(node);
			}
		}		
		return result;
	}
	
	/**
	 * rekursion head
	 * @param node
	 * @return
	 */
	private List<Node> getWayHelper(Node node)  {
		List<Node> result = new ArrayList<Node>();
		getWayHelper2(node, result, 0);		
		return result;		
	}
	
	/**
	 * rekursion heart
	 * @param node
	 * @param result
	 * @return
	 */
	private List<Node> getWayHelper2(Node node,  List<Node> result, int assess_count) {		
		if ( node.getParentID() == UNDEFINED ) {
			result.add(node);
			// recursion end
			return result;
			
		} else {			
			
			// TODO:
//			if (node.getParentID() == 1) {
//				node.setAssessment(assess_count);
//			}
			
			assess_count = assess_count + (node.getAssessment());
			result.add(node);
			getWayHelper2(getNode(node.getParentID()), result, assess_count);
		}		
		return result;			
	}
	
	/**
	 * Heuristic meiner hat einen eigenen Nachbar!
	 * @param node_list
	 * @param current_player_token
	 * @return
	 */
	public boolean neighborWasMyTokenHeuristic(List<Node> node_list, String current_player_token) {
		for (Node node : node_list) {
			
			if (node.getPlayboard().getFreePositionCount() > 3) {
				return false;
			}
		
			List<Integer> node_positions = getMyTokenPositionsWithoutMiddle(node, current_player_token);
			
			for (Integer my_position : node_positions) {
				
				// have a self neighbor
				if ( haveSelfNeighbor(node, my_position, current_player_token) ) {
					System.out.println("neighborWasMyTokenHeuristic erkannt: " + node);
					return true;					
				}
			}			
		}
		return false;
	}
	
	/**
	 * Heuristic: Meiner, hat keine Nachbarn! 
	 * @param node_list
	 * @param current_player_token
	 * @return
	 */
	public boolean nonNeighborHeuristic(List<Node> node_list, String current_player_token) {
		for (Node node : node_list) {
			
			if (node.getPlayboard().getFreePositionCount() > 3) {
				return false;
			}
			
			List<Integer> node_positions = getMyTokenPositionsWithoutMiddle(node, current_player_token);
			
			for (Integer my_position : node_positions) {
				
				// have he a neighbor?
				if (haveClearNeighbors(node, my_position)) {
					System.out.println("nonNeighborHeuristic: " + node);
					return true;					
				}
			}
		}		
		return false;
	}
	
	/**
	 * Method give true, if neighbors from this position was clear, without the middle!!!
	 * @param my_position
	 * @return
	 */
	private boolean haveClearNeighbors(Node node, Integer my_position) {
		
		List<Integer> neighbors_with_middle = node.getPlayboard().getNeighbors(my_position);
		List<Integer> neighbors_without_middle = new ArrayList<>();
		
		// filter
		for (Integer pos : neighbors_with_middle) {
			if  (pos != node.getPlayboard().getMIDDLE() ) {
				neighbors_without_middle.add(pos);
			}
		}
		
		int clear_count = 0;
		for (Integer pos : neighbors_without_middle) {
			if (node.getPlayboard().getStruct().get(pos) == "clear" ) {
				clear_count++;
			}
		}
		
		if (clear_count > 1) {
			return true;
		}
		return false;
	}
	
	private boolean haveSelfNeighbor(Node node, Integer my_position, String current_player_token) {
		
		List<Integer> neighbors_with_middle = node.getPlayboard().getNeighbors(my_position);
		List<Integer> neighbors_without_middle = new ArrayList<>();
		
		// filter
		for (Integer pos : neighbors_with_middle) {
			if  (pos != node.getPlayboard().getMIDDLE() ) {
				neighbors_without_middle.add(pos);
			}
		}
		
		for (Integer pos : neighbors_without_middle) {
			if (node.getPlayboard().getStruct().get(pos) == current_player_token ) {
				return true;
			}
		}
		
		return false;
	}

/**
 * Method give you token positions without the middle
 * @param node_list
 * @param current_player_token
 * @return
 */
	private List<Integer> getMyTokenPositionsWithoutMiddle(Node node, String current_player_token) {
		List<Integer> my_positions = new ArrayList<>();
		
		for(Map.Entry<Integer, String> map : node.getPlayboard().getStruct().entrySet()) {
			// jump over the middle
			if (map.getKey() == 9) {
				continue;
			}
			if (map.getValue() == current_player_token) {
				my_positions.add(map.getKey());
			}
		}
		return my_positions;
	}
	
	public Node getMaxAssessmentNode(List<Node> node_list) {
		Node node = null;
		
		List<Integer> max_assmentList = new ArrayList<>();
		for (Node current_node : node_list) {
			max_assmentList.add(current_node.getAssessment());			
		}
		
		int max_assment = Collections.max(max_assmentList);
		
		for (Node current_node : node_list) {
			if (current_node.getAssessment() == max_assment) {
				node = current_node;
			}
		}
		
		return node;
	}
	
	public static void main(String[] args) {
		Tree tree = new Tree();		
		Assessment assesment = new Assessment();
		
		Playboard board = new Playboard();
		board.initializeBoard();
		
		board.addToken("Red", 9);
		board.addToken("Blue", 7);
		
		board.addToken("Red", 8);		
		board.addToken("Blue", 5);
		
		board.addToken("Red", 3);
		board.addToken("Blue", 4);
		

	
		Minimax minimax = new Minimax(tree);
		
		List<Node> n = tree.generateTree(board ,9, "Blue");
		int access = minimax.maxAB(tree.getNode(1), tree.getNode(1).getAlpha(), tree.getNode(1).getBeta(), "Blue");	
		
		System.out.println(tree.getStruct());
		
		System.out.println("access: " + access);
		
		System.out.println(tree.printNextBestStep(access));
		
		
//		System.out.println(tree.getStruct());		
		//System.err.println(tree.getAllLeafs());

		Node node = tree.printNextBestStep(access);
		System.err.println("node:" + node);
		
		System.out.println(tree.getMyTokenPositionsWithoutMiddle(node, "Blue"));
		
//		System.err.println("Leaf size: " + tree.getAllLeafs().size());
//		System.out.println("access: ---    " + access);
	}
}








//[ID: 3, ParentID: 1, Deep: 2, Children_IDs: [10, 11, 12, 13], BoardPosition: 6], Best: 0, Assessment: 0, Playboard: {1=Blue, 2=Blue, 3=clear, 4=Red, 5=Red, 6=Blue, 7=clear, 8=clear, 9=Red}































