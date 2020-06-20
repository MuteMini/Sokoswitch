package sokoswitch.game.screens;

import java.util.Stack;
import com.badlogic.gdx.Screen;
import sokoswitch.game.Sokoswitch;

public class GameScreenManager {
	
	private Stack<Screen> screens;
	
	public GameScreenManager(Sokoswitch game) {
		screens = new Stack<>();
		screens.add(new PuzzleScreen(game, 1));
	}
	
	public Screen peek() {
		return screens.peek();
	}
	
}
