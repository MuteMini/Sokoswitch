package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import sokoswitch.game.Sokoswitch;

public class MenuScreen extends GameScreen{

	private Sokoswitch game;
	
	//private int level;

	public MenuScreen(Sokoswitch game) {
		super();
		this.game = game;
		
		//this.level = 0;
	}
	
	@Override
	public void show() {
		//System.out.println("moving on from level " + level);
		game.gsm.showWorldScreen(0);
	}

	public void update(float delta) {
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.45f, 0.45f, .45f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
		
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
