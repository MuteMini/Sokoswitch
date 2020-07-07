package sokoswitch.game.screens;

import java.util.Stack;
import com.badlogic.gdx.ScreenAdapter;

public abstract class GameScreen extends ScreenAdapter{

	protected Stack<Integer> keysPressed;
	
	public GameScreen() {
		super();
		this.keysPressed = new Stack<>();
	}
	
	public void setKeysPressed(Stack<Integer> keysPressed) {
		this.keysPressed = keysPressed;
	}
}
