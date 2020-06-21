package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;

import sokoswitch.game.Sokoswitch;
import sokoswitch.game.level.*;

public class PuzzleScreen extends ScreenAdapter{

	private int levelId;
	private boolean cameraCentered;
	private GameLevel gameLevel;
	private Sokoswitch game;
	
	public PuzzleScreen(Sokoswitch game, int levelId) {
		this.game = game;
		this.levelId = levelId;
		this.cameraCentered = false;
	}
	
	public void resetLevel() {}
	public void setLevel() {}
	
	@Override
	public void show() {
		gameLevel = new GameLevel(LevelPath.getLevelPath(levelId));
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		int borderX = gameLevel.getWidth() * Tiles.SIZE;
		int borderY = gameLevel.getHeight() * Tiles.SIZE;

		game.camera = new OrthographicCamera(w, h);
		game.camera.position.set(borderX/2f, borderY/2f, 0);
		
		if(gameLevel.getWidth() > 14 || gameLevel.getHeight() > 14) {
			game.camera.zoom += 4;
			cameraCentered = true;
		}
		else {
			game.viewport = new FitViewport(borderX, borderY, game.camera);
			game.viewport.apply();
		}
		game.camera.update();
		
		System.out.println(gameLevel.getWidth() + " " + gameLevel.getHeight());
	}

	@Override
	public void render(float delta) {
		gameLevel.update(delta);
		
		Gdx.gl.glClearColor(.45f, .45f, .45f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameLevel.render(game.camera);
		
		/*if(Gdx.input.isTouched()) {
			game.camera.translate(-Gdx.input.getDeltaX()*3, Gdx.input.getDeltaY()*3);
		}*/
		if(Gdx.input.justTouched()) {
			Vector3 clickPos = game.viewport.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
			Tiles type = gameLevel.locateTilesByPosition(0, clickPos.x, clickPos.y);
			gameLevel.getPlayer(0).setPosition((int)clickPos.x/Tiles.SIZE, (int)clickPos.y/Tiles.SIZE);
			System.out.println(clickPos.x + "," + clickPos.y +"\t");
			System.out.println(type.getId() + " : " + type.getName());
		}
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false); 
	}

	@Override
	public void dispose() {
		gameLevel.dispose();
	}
}
