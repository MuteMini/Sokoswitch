package sokoswitch.game.screens;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.*;
import sokoswitch.game.Sokoswitch;
import sokoswitch.game.level.*;

public class PuzzleScreen extends PlayerScreen{

	/*used to switch screens*/
	private Sokoswitch game;
	
	/*holds levelPath id for GameLevel*/
	private int levelId;
	/*holds the actual game level*/
	private GameLevel gameLevel;

	public PuzzleScreen(Sokoswitch game, int levelId, HashSet<Long> levelsSolved) {
		super();
		this.game = game;
		this.levelId = levelId;
		this.levelsSolved = levelsSolved;
		resetLevel();
	}
	
	public void resetLevel() {
		this.gameLevel = new GameLevel(levelId, game, levelsSolved);
	}
	
	@Override
	public void show() {
		int borderX = gameLevel.getWidth() * Tiles.SIZE;
		int borderY = gameLevel.getHeight() * Tiles.SIZE;

		game.camera.zoom = 1;
		game.viewport = new FitViewport(borderX, borderY, game.camera);
		game.viewport.apply();
		game.camera.position.set(borderX/2f, borderY/2f, 0);
		game.camera.update();
	}

	public void update(float delta) {
		gameLevel.takeInput(keysPressed);
		gameLevel.update(delta);
		
		if(gameLevel.isSolved()) {
			levelsSolved.add((long)levelId);
			game.gsm.pop();
		}

		if(Gdx.input.justTouched()) {
			resetLevel();
			/*Vector3 clickPos = game.camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
			Tiles tile = gameLevel.locateTilesByPosition(2, clickPos.x, clickPos.y);
			System.out.println(tile.getId() + " " + clickPos);*/
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.50f, .50f, .50f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		gameLevel.render(game.camera);
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false);
	}
	
	@Override
	public void dispose() {
		gameLevel.dispose();
	}
	
	public int getLevelId() {
		return levelId;
	}
}
