package Pack;

import sac.State;
import sac.StateFunction;

public class MisplacedTilesHeuristics extends StateFunction{

	@Override
	public double calculate(State state) {
		SlidingPuzzle sp = (SlidingPuzzle) state;
		return sp.getMisplacedTiles();
	}
	

}
