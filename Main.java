package Pack;

import java.util.List;

import sac.graph.AStar;
import sac.graph.BestFirstSearch;
import sac.graph.GraphSearchAlgorithm;
import sac.graph.GraphSearchConfigurator;
import sac.graph.GraphState;

/**
 * Puzzle przestawne
 * 
 * @author https://github.com/dylionen
 * @version 1.0
 */

public class Main {
	public static void main(String[] agrs) {
		SlidingPuzzle game = new SlidingPuzzle();
		game.mixBoard(100);
		System.out.println(game);

		/*
		 * Heurystyka: MisplacedTiles
		 */
		List<GraphState> g = game.generateChildren();

		/*
		 * for (GraphState sol : g) { System.out.println(sol + "\n"); }
		 */

		SlidingPuzzle.setHFunction(new MisplacedTilesHeuristics());

		GraphSearchAlgorithm a = new AStar(game);
		a.execute();

		List<GraphState> solutions = a.getSolutions();
		/*
		 * for (GraphState sol : solutions) { System.out.println(sol + "\n"); }
		 */

		SlidingPuzzle temp = (SlidingPuzzle) solutions.get(0);
		temp.showMeTheWayToWin();

		System.out.println("MisplacedTiles:");
		System.out.println("Time: " + a.getDurationTime());
		System.out.println("Closed: " + a.getClosedStatesCount());
		System.out.println("Open: " + a.getOpenSet().size());
		System.out.println("Solutions: " + solutions.size());
		System.out.println("--------------------");

		/*
		 * Heurystyka: Manhattan
		 */
		List<GraphState> m = game.generateChildren();
		/*
		 * for (GraphState sol : g) { System.out.println(sol + "\n"); }
		 */

		SlidingPuzzle.setHFunction(new ManhattanHeuristics());

		GraphSearchAlgorithm b = new AStar(game);
		b.execute();

		List<GraphState> solutionsManhattan = b.getSolutions();
		for (GraphState sol : solutionsManhattan) {
			System.out.println(sol + "\n");
		}
		System.out.println("Manhattan:");
		System.out.println("Time: " + b.getDurationTime());
		System.out.println("Closed: " + b.getClosedStatesCount());
		System.out.println("Open: " + b.getOpenSet().size());
		System.out.println("Solutions: " + solutionsManhattan.size());

	}

}
