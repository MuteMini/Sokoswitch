package sokoswitch.game.screens;

import java.util.Stack;
import com.badlogic.gdx.Screen;
import sokoswitch.game.Sokoswitch;

public class GameScreenManager {
	
	private Stack<Screen> screens;
	private Sokoswitch game;
	
	public GameScreenManager(Sokoswitch game) {
		this.screens = new Stack<>();
		this.game = game;
		screens.push(new MenuScreen(game));
	}
	
	public void showPuzzleScreen(int id) {
		screens.push(new PuzzleScreen(game, id));
	}
	
	public void pop() {
		screens.pop();
	}
	
	public Screen peek() {
		return screens.peek();
	}
}
