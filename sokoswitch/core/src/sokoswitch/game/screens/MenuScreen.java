package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;

import sokoswitch.game.Sokoswitch;

public class MenuScreen extends ScreenAdapter{

	private Sokoswitch game;
	private GameScreenManager gsm;

	public MenuScreen(Sokoswitch game, GameScreenManager gsm) {
		this.game = game;
		this.gsm = gsm;
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.45f, .45f, .45f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			gsm.showPuzzleScreen(5);
		}
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false); 
	}

	@Override
	public void dispose() {
		
	}
}
