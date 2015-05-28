package adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Playboard;

/**
 * This class generate a tree to our playboard
 * @author foxhound
 */
public class Tree {

	// struct to save of nodes
	List<Node> tree = new ArrayList<Node>();
	
	// node ids
	private int node_id = 1;
	
	private int undefined  = 77777777;				
	
	/**
	 * Constructor
	 */
	public Tree() {		
		// add the root node
		createAndAddRootNode();
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
	
	/**
	 * Method get you the deepest value of tree
	 * @return
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
	 * Tree add a node
	 * @param parent_node_id - parent node of new node in param node
	 * @param node
	 */
	public void addNewNode(int parent_id, int board_position) {
		node_id ++ ;
		
		Node node = createNode();		
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
		
		// to get the struct of board
		Map<Integer, String> board_struct = board.getPlayboard();
		
		if ( board.getFreePositionCount() >= 6 ) {
			// Jump token status
			
		} else {
			// insert token status
			
			int nodesCount = board.getFreePositionCount();	
		}
		
		return tree;
	}
	
	/**
	 * Method give you count of free positions
	 * @return List<Node>
	 */
	private List<Node> generateNodes(Playboard board, int parentNodeID) {
		
		List<Node> result = new ArrayList<Node>();
		Map<Integer, String> board_struct = board.getPlayboard();
				
		for (Entry<Integer, String> elem : board_struct.entrySet()) {
			if (elem. getValue().compareTo(board.getClearToken()) == 0)  {
				
				// max deep of tree
				int maxDeep = getMaxDeep();
				
				// create legitim nodes
				Node currentNode = createNode();
				
			}
		}
		return result;
	}
			
	/**
	 * Create a root node an add him in tree
	 */
	private void createAndAddRootNode() {
		Node rootNode = createNode();
		rootNode.setDeep(1);
		rootNode.setParentID(undefined);
		rootNode.setChildren(null);
		rootNode.setID(node_id);
		rootNode.setBoardPosition(undefined);
		tree.add(rootNode);
	}
	
	
	public static void main(String[] args) {
		Tree tree = new Tree();
		
		tree.addNewNode(1, 2);
		tree.addNewNode(1, 7);
		tree.addNewNode(2, 3);
		tree.addNewNode(4, 4);
		
		System.out.println(tree.tree);
	}
}







































