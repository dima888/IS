package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.event.EventListenerList;

import view.PlayboardView;
import adt.Node;
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
	
	private int deep = 6;
	private int max_deep = 6;
	
	Color color_p1 = Color.RED;
	Color color_p2 = Color.CYAN;
	
	boolean step_not_found = false;
	
	Bot bot;
	

	boolean bot_1 = false;
	boolean bot_2 = true;
	
	List<JButton> jButtonList = new ArrayList<JButton>();
	private PlayboardView view;
	
	public Game(String player_1_color, String player_2_color) {		
		
		board = new Playboard();
		board.initializeBoard();
						
		tree = new Tree();
		minimax = new Minimax(tree);		
		
		initializeView();
		
		bot = new Bot(view);
		

				
		// player 1
		if (getCurrentPlayerMoveToken()) {						
			giveTip("Red", bot_1);
		} else {
			// player 2
			giveTip("Blue", bot_2);
		}		
		
	
	}
	
	private void initializeView() {
		view = new PlayboardView(this);
		view.setVisible(true);				
        view.setSize(800, 800);
        setViewButtonPositions();
	}

	private void setViewButtonPositions() {
        view.jButton1.setLocation(350, 100);  
        
        view.jButton9.setLocation(350, 300);                
        view.jButton5.setLocation(350, 500);
        
        view.jButton7.setLocation(150, 300);
        view.jButton3.setLocation(550, 300);
        
        view.jButton2.setLocation(500, 200);
        view.jButton8.setLocation(200, 200);
        
        view.jButton4.setLocation(500, 400);
        view.jButton6.setLocation(200, 400);         
        
        view.infoLabel.setLocation(50, 600);		
	}

	public void setView(PlayboardView view) {
		this.view = view;
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
	private boolean getCurrentPlayerMoveToken() {
		if (deferrals_count % 2 == 0) {
			return true;			
		} 		
		return false;
	}	
	
	public boolean doStep(JButton jButton) {			
		
		// Precondition
		if (setTokenLimit-- < 1) {		
			// Move Token Phase			
			if (!moveToken(jButton)) {
				return false;
			}			
			
		} else {			
			// Set Token Phase			
			if (!setToken(jButton)) {				
				setTokenLimit++;
				return false;
			}
		}
				
		// player 1
		if (getCurrentPlayerMoveToken()) {	
			giveTip("Red", bot_1);
		} else {
			// player 2
			giveTip("Blue", bot_2);
		}
		return true;
	}
	
	/**
	 * Get tip
	 * @param bot
	 */
	private void giveTip(String player, boolean bot) {		

		// bot can not found the way
		if (step_not_found) {
			// start bot back
			step_not_found = false;
			return;
		}
		
		tree = new Tree();
		int best_assessment = 0;
		Node futureNode;		
		
		if (bot) {		
			tree.generateTree(board, deep, player);	
			minimax.setTree(tree);						
			best_assessment = minimax.maxAB(tree.getNode(1), tree.getNode(1).getAlpha(), tree.getNode(1).getBeta(), player);
			
//			System.out.println(tree.getStruct());
			

			
			System.out.println("Beste bewertung: " + best_assessment);
			//System.err.println(tree.getAllLeafs());
									
			try {			
					
//				futureNode = tree.printNextBestStep(best_assessment);
//				tree.printNextBestStep(best_assessment);
				
				
//				System.out.println(futureNode);
								
			
				// heuristiken erst dann spielen lassen, wenn alle token gelegt wurden
				if (setTokenLimit <1) {
					
//					List<Node> allowedNodeList = new ArrayList<>();
					
					for (Node node : tree.getNodes(2)) {
						
						if (tree.neighborWasMyTokenHeuristic(new ArrayList<>(Arrays.asList(node)), player) ) {
							//continue;
							node.setAssessment(node.getAssessment() - 200);
						}
//						
						if (tree.nonNeighborHeuristic(new ArrayList<>(Arrays.asList(node)), player) ) {
							node.setAssessment(node.getAssessment() - 250);
						}	
						
//						allowedNodeList.add(node);						
					}
				
//					try {Thread.sleep(50);} catch (InterruptedException e1) {e1.printStackTrace();}
//					System.err.println("Gefilterne Wege");
//					try {Thread.sleep(50);} catch (InterruptedException e1) {e1.printStackTrace();}
//					System.out.println(allowedNodeList);										
//					try {Thread.sleep(50);} catch (InterruptedException e1) {e1.printStackTrace();}
					
					
//					if ( !allowedNodeList.isEmpty() ) {
//						futureNode = tree.getMaxAssessmentNode(tree.getNodes(2));
//					}
					
					
				}
							
				//tree.printAllBestWays(best_assessment);
				futureNode = tree.getMaxAssessmentNode(tree.getNodes(2));
				tree.printNextBestStep(futureNode.getAssessment());
				
				try {Thread.sleep(50);} catch (InterruptedException e1) {e1.printStackTrace();}
				System.err.println("Naechten moeglichen Wege!!!");
				System.out.println(tree.getNodes(2));
				try {Thread.sleep(50);} catch (InterruptedException e1) {e1.printStackTrace();}
				
				

				// bot make step
				this.bot.doStep(tree.getNode(1), futureNode);
				
			} catch (NullPointerException e) {
				System.err.println("Loesung nicht gefunden!");		
				
				// boolean for close the recusiv call
				step_not_found = true;
				
				// Bot clicked here random
				this.bot.doRandomStep(tree.getNode(1), player);							
				return;
			}
		
		} else {
			System.out.println(player + " bot not activ");
		}
		
		if (deep < max_deep) {
			deep++;
		}
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
		deferrals_count++;
		return true;
	}
	
	/**
	 * Method return the board position from clicked button
	 * @param jButton
	 * @return
	 */
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
	
	public static void main(String[] args) {
		Game g = new Game("Red", "Blue");
	}
}
