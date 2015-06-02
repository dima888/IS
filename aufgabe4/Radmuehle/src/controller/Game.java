package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import adt.Tree;
import algorithm.Minimax;
import model.Playboard;

/**
 * In this class we define the rules of the game Muehle
 * This class is the controller for the view
 * @author foxhound
 *
 */
public class Game {
	
	private Playboard board;
	private Minimax minimax;
	private Tree tree;
	
	// old color
	private Color old_color;
	
	// max set on chips in a game
	private int setTokenLimit = 6;
	
	private int player_1_tokens = 3;
	private int player_2_tokens = 3;
	
	// verschiebungen
	private int deferrals_count = 0;
	
	private String player_1_token = "";
	private String player_2_token = "";
	
	Color color_p1 = Color.RED;
	Color color_p2 = Color.CYAN;
	
	List<JButton> jButtonList = new ArrayList<JButton>();
	
	public Game(String player_1_color, String player_2_color) {
		board = new Playboard();
		board.initializeBoard();
						
		tree = new Tree();
		minimax = new Minimax(tree);				
		tree.generateTree(board, 2);
		
		//System.out.println(minimax.maxAB(tree.getNode(1), tree.getNode(1).getAlpha(), tree.getNode(1).getBeta()));
		minimax.maxAB(tree.getNode(1), tree.getNode(1).getAlpha(), tree.getNode(1).getBeta());
		
		
		// set the player tokens
		player_1_token = player_1_color;
		player_2_token = player_2_color;
	}
	
	/**
	 * Method get Info of player tokens and token limit
	 * @return
	 */
	public String getInfo() {
		return board.getCurrentMessage();
	}
	
	/**
	 * if true -> player 1; else player 2
	 * @return boolean
	 */
	public boolean getCurrentPlayerSetToken() {						
		if (player_2_tokens == player_1_tokens) {
			return true;			
		} 		
		return false;
	}
	
	/**
	 * if true -> player 1; else player 2
	 * @return boolean
	 */
	public boolean getCurrentPlayerMoveToken() {
		if (deferrals_count % 2 == 0) {
			return true;			
		} 		
		return false;
	}
	
	
	public boolean doStep(JButton jButton) {
		
		// Precondition
		if (setTokenLimit-- < 1) {
			
			// Move Token Phase			
			//System.err.println("Move token Phase \n");
			if (!moveToken(jButton)) {
				return false;
			}			
			
		} else {
			
			//System.err.println("Set Token Phase \n");
			// Set Token Phase			
			if (!setToken(jButton)) {				
				setTokenLimit++;
				return false;
			}
		}
		
		// change deep
		int need_deep = 0;		
		// move
		if ( board.getFreePositionCount() == 3 ) {			
			need_deep = 2;
			// set
		} else {
			need_deep = 2;
		}	
		
		//deferrals_count++;
		System.out.println(deferrals_count);
		
		if (getCurrentPlayerMoveToken()) {
			tree = new Tree();
			tree.generateTree(board, need_deep);	
			minimax.setTree(tree);
			int best_value = minimax.maxAB(tree.getNode(1), tree.getNode(1).getAlpha(), tree.getNode(1).getBeta());
		}
		
				
		return true;
	}
	
	/**
	 * Add a token in on the board
	 * @param token
	 * @param toBoardPosition
	 */
	public boolean setToken(JButton jButton) {
								
		int toBoardPosition = giveBoardPosition(jButton);				
							
		if (getCurrentPlayerSetToken()) {
			// player 1 code
			if (!board.addToken("Red", toBoardPosition)) {
				// exception, do nothing
				return false;
			}
			jButton.setBackground(color_p1);
			
			player_1_tokens --;		

		} else {
			// player 2 code
			if (!board.addToken("Blue", toBoardPosition)) {
				// exception, do nothing
				return false;
			}
			jButton.setBackground(color_p2);
			
			player_2_tokens--;
		}							
		return true;
	}
	
	private int giveBoardPosition(JButton jButton) {		
		String button_value = jButton.getText();
		
		if ( jButton.getText().length() > 1 ) {
			button_value = Character.toString(jButton.getText().charAt(0));
		}		
		return Integer.parseInt(button_value);
	}
	
	/**
	 * Method move tokens 
	 * @param jButton
	 * @return
	 */
	public boolean moveToken(JButton jButton) {
		
		jButtonList.add(jButton);
		
		if (jButtonList.size() == 2) {
			
			// move visual			
			if (getCurrentPlayerMoveToken()) {
				
				// precondtion
				if (old_color != color_p1) {
					board.setCurrentMessage("Its not your move!!!");
					jButtonList.get(0).setBackground(old_color);
					
					// false neighbor rule
					if (board.specialRule) {
						jButtonList.get(1).setBackground(Color.WHITE);
						board.specialRule = false;
					}
					
					jButtonList = new ArrayList<JButton>();	
					return false;
				} 				
				jButton.setBackground(color_p1);
				
			} else {
				
				if (old_color != color_p2) {
					board.setCurrentMessage("Its not your move!!!");
					jButtonList.get(0).setBackground(old_color);
					
					// false neighbor rule
					if (board.specialRule) {
						jButtonList.get(1).setBackground(Color.WHITE);
						board.specialRule = false;
					}
					
					jButtonList = new ArrayList<JButton>();	
					return false;
				} 
				jButton.setBackground(color_p2);
			}
			
			
			
			
			// move in struct
			//if (!board.moveToken(Integer.parseInt(jButtonList.get(0).getText()), Integer.parseInt(jButtonList.get(1).getText()))) {
			if (!board.moveToken(giveBoardPosition(jButtonList.get(0)), giveBoardPosition(jButtonList.get(1)))) {
				// // exception! set white color back of 
				jButtonList.get(0).setBackground(old_color);
				
				// false neighbor rule
				if (board.specialRule) {
					jButtonList.get(1).setBackground(Color.WHITE);
					board.specialRule = false;
				}
				
				jButtonList = new ArrayList<JButton>();	
				return false;
			}						
													
			// clear jButtonList
			jButtonList = new ArrayList<JButton>();	
			deferrals_count++;
			return true;
		}		
		
		// Save the old color
		old_color = jButton.getBackground();
		
		jButton.setBackground(Color.WHITE);
		return true;
	}
	
	/**
	 * Getter for playboard
	 * @return
	 */
	public Map getPlayboard() {
		return board.getStruct();
	}
}
