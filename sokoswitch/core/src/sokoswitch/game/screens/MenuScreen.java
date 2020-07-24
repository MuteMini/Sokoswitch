package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import sokoswitch.game.Sokoswitch;

public class MenuScreen extends GameScreen{

	private Sokoswitch game;
	
	public MenuScreen(Sokoswitch game) {
		super();
		this.game = game;
	}
	
	@Override
	public void show() {
		game.gsm.showWorldScreen(0);
		//game.gsm.showPuzzleScreen(203);
	}

	public void update(float delta) {
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.45f, 0.45f, .45f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false); 
	}

	@Override
	public void dispose() {
		
	}
}
