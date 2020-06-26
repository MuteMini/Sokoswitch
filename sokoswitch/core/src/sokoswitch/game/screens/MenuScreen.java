package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;

import sokoswitch.game.Sokoswitch;

public class MenuScreen extends ScreenAdapter{

	private Sokoswitch game;
	
	private int level;

	public MenuScreen(Sokoswitch game) {
		this.game = game;
		
		this.level = 1 -1;
	}
	
	@Override
	public void show() {
		System.out.println("moving on from level " + level);
		game.gsm.showPuzzleScreen(++level);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.45f, 0.45f, .45f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			gsm.showPuzzleScreen(testNum);
		}*/
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false); 
	}

	@Override
	public void dispose() {
		
	}
}
