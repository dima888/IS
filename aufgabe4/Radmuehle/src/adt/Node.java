package adt;

import java.util.ArrayList;
import java.util.List;

import model.Playboard;

/**
 * This is the Node class, the Tree.java and Minimax.java use this class only!
 * @author foxhound
 *
 */
public class Node {	

	private int id;
	private int parent_id;
	private List<Integer> children = new ArrayList<Integer>();
	private int board_position;
	private int deep;
	private int undefined  = 77777777;	
	private int alpha = -999_999_999;
	private int beta = 999_999_999;	
	private int assessment = 0;
	private int best = 0;
	private String message = "";
	
	private Playboard board;
	
	/**
	 * Method give your node id
	 * @return int
	 */
	public int getID() {	
		return id;
	}
	
	public int getBest() {
		return best;
	}
	
	public void setMesseage(String message) {
		this.message = message;
	}
	
	public void setBest(int best) {
		this.best = best;
	}
	
	/**
	 * Getter for assessment
	 * @return int
	 */
	public int getAssessment() {
		return assessment;
	}
	
	/**
	 * Method give you the deep of your node
	 * @return int
	 */
	public int getDeep() {
		return deep;
	}
	
	/**
	 * Method get you alpha
	 * @return int
	 */
	public int getAlpha() {
		return alpha;
	}
	
	/**
	 * Method get you beta
	 * @return int
	 */
	public int getBeta() {
		return beta;
	}
	
	/**
	 * Method give you the saved board position in this node
	 * @return int 
	 */
	public int getBoardPosition() {
		return board_position;
	}
	
	/**
	 * Method get your parent node id
	 * @return int 
	 */
	public int getParentID() {
		return parent_id;
	}
	
	/**
	 * Method get you the playboard
	 * @return Playboard
	 */
	public Playboard getPlayboard() {
		return board;
	}
	
	/**
	 * Method get you a list with children ID's
	 * @return List -
	 */
	public List<Integer> getChildren() {
		return children;
	}

	/**
	 * ID Setter
	 * @param id -
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Setter for Alpha
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	
	/**
	 * Setter for beta
	 * @param beta
	 */
	public void setBeta(int beta) {
		this.beta = beta;
	}
	
	/**
	 * Setter for assessment
	 * @param assessment
	 */
	public void setAssessment(int assessment)  {
		this.assessment = assessment;
	}

	/**
	 * Parent ID Setter
	 * @param parentID -
	 */
	public void setParentID(int parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * Children ID Setter
	 * @param int - child id 
	 */
	public void setChildren(int id) {
		children.add(id);
	}	
	
	/**
	 * Setter for the board position
	 * @param board_position - 
	 */
	public void setBoardPosition(int board_position) {
		this.board_position = board_position;
	}
	
	
	/**
	 * Setter for the deep from this node
	 * @param deep
	 */
	public void setDeep(int deep) {
		this.deep = deep;
	}
	
	/**
	 * Setter for playboard
	 * @param board
	 */
	public void setPlayboard(Playboard board) {
		this.board = board;
	}
	
	@Override
	public String toString() {
		if ( board_position == undefined) {
			return  " [ID: " + id + ", ParentID: Root" + ", Deep: " + deep + ", Children_IDs: " + getChildren() +  ", BoardPosition: Undefine]" + ", Best: " + getBest() + ", Assessment: " + getAssessment() + ", Playboard: " + getPlayboard() +  "\n" ;
		}
		return " [ID: " + id + ", ParentID: " + parent_id + ", Deep: " + deep + ", Children_IDs: " + getChildren() + ", BoardPosition: " + board_position + "]" + ", Best: " + getBest() + ", Assessment: " + getAssessment() +  ", Playboard: " + getPlayboard() + ", message: " + message +  "\n";		
	}
	
	/**
	 * TODO: Testing
	 * We change the definition of leaf for our bot!
	 * New definition: a leaf is with a win line! win line -> haveLine method in playboard
	 * 
	 * 
	 * Method return true, if it this node a leaf node, else false
	 * @return boolean
	 */
	public boolean isLeaf() {
		
//		if (getChildren().isEmpty()) {
//			return true;
//		}		
//		if (this.getPlayboard().haveLine("Red")) {
//			return true;
//		}
//		if (this.getPlayboard().haveLine("Blue")) {
//			return true;
//		}
		
		if (getID() != 1) {
			return true;
		}
		
		return false;
	}
	
	public boolean isLeaf_2() {
		return getChildren().isEmpty();
	}
}
















