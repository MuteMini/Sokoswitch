package sokoswitch.game.screens;

import java.util.HashSet;

public abstract class PlayerScreen extends GameScreen{

	/*holds how much levels the player has solved*/
	protected HashSet<Long> levelsSolved;
	
	public HashSet<Long> getLevelsSolved() {
		return levelsSolved;
	}
}
