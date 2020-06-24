package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;

import sokoswitch.game.Sokoswitch;

public class MenuScreen extends ScreenAdapter{

	private Sokoswitch game;
	private GameScreenManager gsm;
	
	private int testNum;

	public MenuScreen(Sokoswitch game, GameScreenManager gsm) {
		this.game = game;
		this.gsm = gsm;
		
		this.testNum = 1;
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.justTouched()) {
			testNum += (testNum >= 6) ? -5 : 1;
		}
		Gdx.gl.glClearColor(.45f, 0.1f*testNum, .45f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.showPuzzleScreen(4);
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
