package adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
	
	private int undefined  = 77777777;		
	
	private Node root_node = null;
	
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
	public List<Node> generateTree(Playboard board, int deep) {		
		deep--;
		
		// edit root
		root_node.setPlayboard(board);
		
		int runs = 0;
		
		// player ident
		String playerToken = "";
		
		// condition for right deep of tree
		while (runs++ < deep) {
						
			
			if (runs % 2 != 0) {
				playerToken = player_1_token;
			} else {
				playerToken = player_2_token;
			}
			
			// free position in board allow! 3 => 3 positions was clear!
			//if ( deepestNode.getPlayboard().getFreePositionCount() <= 3 ) {
			if ( board.getFreePositionCount() <= 3 ) {
				// Jump token status
				
				generateNodes(board, runs, playerToken, true);
				
				
								
				System.err.println("hier? JETZT WIRD GESCHOBEN1111111111111111111111111");
				
				
			} else {
				// insert token status						
				
				// generate nodes
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
		rootNode.setParentID(undefined);		
		//rootNode.setChildren(-1);
		rootNode.setID(node_id);		
		rootNode.setBoardPosition(undefined);		
		tree.add(rootNode);		
		root_node = rootNode;		
		deepestNode = root_node;
	}
	
	
	public static void main(String[] args) {
		Tree tree = new Tree();

		
		
		Playboard p = new Playboard();
		p.initializeBoard();
	
		//tree.generateTree(p, 2);
		
		p.addToken("Blue", 1);
		
		p.addToken("Blue", 3);
		
		p.addToken("Blue", 4);
		
		p.addToken("Red", 5);
		
		p.addToken("Red", 9);
		
		p.addToken("Red", 8);
		
		
		List<Node> n = tree.generateTree(p ,2);
		
		System.out.println(n);
		
		//{1=Blue, 2=clear, 3=Blue, 4=Blue, 5=Red, 6=clear, 7=clear, 8=Red, 9=Red}
//		
////		tree.addNewNode(1, -1); // a
////		tree.addNewNode(1, -1); // b
////		
////		tree.addNewNode(2, -1); // a + c
////		tree.addNewNode(2, -1);	// a + d			
////		
////		tree.addNewNode(3, -1); // b + e
////		tree.addNewNode(3, -1); // b + f
////		
////		tree.addNewNode(4, -1); // c + g
////		tree.addNewNode(4, -1); // c + h
////		
////		tree.addNewNode(5, -1); // d + i
////		tree.addNewNode(5, -1); // d + j
//		
//		System.out.println(tree.tree);
//	
//		Minimax m = new Minimax(tree);
//		
//		
//		int res = m.maxAB(tree.getNode(1), tree.getNode(1).getAlpha(), tree.getNode(1).getBeta());
//		System.out.println(tree.tree);
//		//int res2 = m.minAB(tree.getNode(1),tree.getNode(1).getAlpha(), tree.getNode(1).getBeta());
//		
//		System.out.println(res); 
//		//System.out.println(res2);
	}
}








































