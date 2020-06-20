package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;

import sokoswitch.game.Sokoswitch;
import sokoswitch.game.level.*;

public class MenuScreen extends ScreenAdapter{

	private Sokoswitch game;

	public MenuScreen(Sokoswitch game) {
		this.game = game;
	}
	
	@Override
	public void show() {
	
	}

	@Override
	public void render(float delta) {
	
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false); 
	}

	@Override
	public void dispose() {
		
	}
}
