package adt;

import java.util.ArrayList;
import java.util.List;

public class Node {	

	private int id;
	private int parent_id;
	private List<Integer> children = new ArrayList<Integer>();
	private int board_position;
	private int deep;
	private int undefined  = 77777777;	
	
	/**
	 * Method give your node id
	 * @return int
	 */
	public int getID() {	
		return id;
	}
	
	/**
	 * Method give you the deep of your node
	 * @return
	 */
	public int getDeep() {
		return deep;
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
	 * Parent ID Setter
	 * @param parentID -
	 */
	public void setParentID(int parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * Children ID Setter
	 * @param children - 
	 */
	public void setChildren(List<Integer> children) {
		this.children = children;
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
	
	@Override
	public String toString() {
		if ( board_position == undefined) {
			return  " [ID: " + id + ", ParentID: Root" + ", Deep: " + deep + ", BoardPosition: Undefine]" + "\n" ;
		}
		return " [ID: " + id + ", ParentID: " + parent_id + ", Deep: " + deep + ", BoardPosition: " + board_position + "]" + "\n";		
	}
}
















