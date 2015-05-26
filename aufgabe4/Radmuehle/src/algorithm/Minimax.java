package algorithm;

/**
 * This class implement the minimax algorithm and there worked on our Tree
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

	// TODO:
}
