package sokoswitch.game.screens;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.*;
import sokoswitch.game.Sokoswitch;
import sokoswitch.game.level.*;

public class PuzzleScreen extends PlayerScreen implements PauseMenu{

	/*used to switch screens*/
	private Sokoswitch game;
	
	/*holds levelPath id for GameLevel*/
	private int levelId;
	/*holds the actual game level*/
	private GameLevel gameLevel;
	
	/*holds if the game has been paused*/
	private boolean paused;
	private PauseStage pauseStage;

	public PuzzleScreen(Sokoswitch game, int levelId, HashSet<Long> levelsSolved) {
		super();
		this.game = game;
		this.levelId = levelId;
		this.levelsSolved = levelsSolved;
		this.paused = false;
		
		reset();
		
		String levelIdString = "Stage " + (char)(levelId/100 + 64) + " Level " + (levelId%100) +": ";
		this.pauseStage = new PauseStage(game.gam, true, levelIdString, this.gameLevel.getLevelName(), this);
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
		if(gameLevel.isSolved()) {
			levelsSolved.add((long)levelId);
			leave();
		}
		else if(paused) {
			pauseStage.takeInput(keysPressed);
			pauseStage.update(delta);
		}
		else {
			gameLevel.takeInput(keysPressed);
			gameLevel.update(delta);
			if(!keysPressed.isEmpty() && keysPressed.peek() == 7) {
				paused = true;
				keysPressed.pop();
			}
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.50f, .50f, .50f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		game.viewport.apply();
		gameLevel.render(game.camera);
		if(paused) {
			game.batch.end();
			pauseStage.getViewport().apply();
			pauseStage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false);
	}
	
	@Override
	public void dispose() {
		gameLevel.dispose();
		pauseStage.dispose();
	}
	
	public int getLevelId() {
		return levelId;
	}

	@Override
	public boolean getPaused() {
		return paused;
	}

	@Override
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	@Override
	public void reset(){
		this.gameLevel = new GameLevel(levelId, game, levelsSolved);
	}
	
	@Override
	public void leave(){
		game.gsm.pop();
	}
	
	@Override
	public void exit(){}
}
