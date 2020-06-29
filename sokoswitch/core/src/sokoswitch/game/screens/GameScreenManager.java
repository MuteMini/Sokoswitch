package sokoswitch.game.screens;

import java.util.HashSet;
import java.util.Stack;
import com.badlogic.gdx.Screen;
import sokoswitch.game.Sokoswitch;

public class GameScreenManager{
	
	private Stack<Screen> screens;
	private Sokoswitch game;
	
	public GameScreenManager(Sokoswitch game) {
		this.screens = new Stack<>();
		this.game = game;
		screens.push(new LoadingScreen(game));
	}
	
	public void showPuzzleScreen(int id) {
		HashSet<Long> tempHs = new HashSet<>();
		if(screens.peek() instanceof PuzzleScreen)
			tempHs = ((PuzzleScreen)screens.peek()).getLevelsSolved();
		screens.push(new PuzzleScreen(game, id, tempHs));
	}
	
	public void showMenu() {
		screens.pop();
		screens.push(new MenuScreen(game));
	}
	
	public void pop() {
		screens.pop();
	}
	
	public Screen peek() {
		return screens.peek();
	}
	
	public void printStack() {
		for(Screen scr : screens) {
			if(scr instanceof LoadingScreen) {
				System.out.print("loading ");
			}
			else if(scr instanceof MenuScreen) {
				System.out.print("menu ");
			}
			else if(scr instanceof PuzzleScreen) {
				System.out.print("puzzle ");
			}
		}
		System.out.println();
	}
}
