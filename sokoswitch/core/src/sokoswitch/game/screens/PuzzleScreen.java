package sokoswitch.game.screens;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.*;
import sokoswitch.game.Sokoswitch;
import sokoswitch.game.level.*;

public class PuzzleScreen extends ScreenAdapter{

	private final int CENTERED_MARGIN = 5;
	
	/*used to switch screens*/
	private Sokoswitch game;
	
	/*holds if the camera should be "centered"*/
	private boolean cameraCentered;
	private float maxCameraX, maxCameraY;
	
	/*holds what keys are being pressed*/
	private Stack<Integer> keysPressed;
	
	/*holds levelPath id for GameLevel*/
	private int levelId;
	/*holds the actual game level*/
	private GameLevel gameLevel;
	/*holds how much levels the player has solved*/
	private HashSet<Long> levelsSolved;

	public PuzzleScreen(Sokoswitch game, int levelId, HashSet<Long> levelsSolved) {
		this.game = game;
		this.levelId = levelId;
		this.cameraCentered = false;
		this.levelsSolved = levelsSolved;
		resetLevel();
	}
	
	public void resetLevel() {
		this.gameLevel = new GameLevel(levelId, game.gam, levelsSolved);
	}
	
	@Override
	public void show() {
		this.keysPressed = new Stack<>();
		Gdx.input.setInputProcessor(new InputAdapter() {
	        @Override 
	        public boolean keyDown (int keycode) {
	            switch(keycode) {
	            	case Input.Keys.UP:
	            	case Input.Keys.W:
	            		keysPressed.push(1);
	            		break;
	            	case Input.Keys.DOWN:
	            	case Input.Keys.S:	
	            		keysPressed.push(2);
	            		break;
	            	case Input.Keys.RIGHT:
	            	case Input.Keys.D:
	            		keysPressed.push(3);
	            		break;
	            	case Input.Keys.LEFT:
	            	case Input.Keys.A:
	            		keysPressed.push(4);
	            		break;
	            	case Input.Keys.SPACE:
	            		keysPressed.push(5);
	            		break;
	            	case Input.Keys.Z:
	            		keysPressed.push(6);
	            		break;
	            	default:
	            		 return false;
	            }
	            return true;
	        }
	        @Override 
	        public boolean keyUp (int keycode) {
	        	switch(keycode) {
	            	case Input.Keys.UP:
	            	case Input.Keys.W:
	            		keysPressed.remove((Integer)1);
	            		break;
	            	case Input.Keys.DOWN:
	            	case Input.Keys.S:	
	            		keysPressed.remove((Integer)2);
	            		break;
	            	case Input.Keys.RIGHT:
	            	case Input.Keys.D:
	            		keysPressed.remove((Integer)3);
	            		break;
	            	case Input.Keys.LEFT:
	            	case Input.Keys.A:
	            		keysPressed.remove((Integer)4);
	            		break;
	            	case Input.Keys.SPACE:
	            		keysPressed.remove((Integer)5);
	            		break;
	            	case Input.Keys.Z:
	            		keysPressed.remove((Integer)6);
	            		break;
	            	default:
	            		 return false;
	            }
            return true;
	        }
	    });
		
		int borderX = gameLevel.getWidth() * Tiles.SIZE;
		int borderY = gameLevel.getHeight() * Tiles.SIZE;

		if(gameLevel.isCentered()) {
			game.camera.zoom = 4;
			game.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), game.camera);
			game.viewport.apply();
			cameraCentered = true;
			this.maxCameraX = borderX - Tiles.SIZE*CENTERED_MARGIN;
			this.maxCameraY = borderY - Tiles.SIZE*CENTERED_MARGIN;
		}
		else {
			game.camera.zoom = 1;
			game.viewport = new FitViewport(borderX, borderY, game.camera);
			game.viewport.apply();
			game.camera.position.set(borderX/2f, borderY/2f, 0);
			this.maxCameraX = 0;
			this.maxCameraY = 0;
		}
		
		game.camera.update();
	}

	public void update(float delta) {
		if(cameraCentered) {
			float cameraX = gameLevel.getPlayer().getSprite().getX();
			float cameraY = gameLevel.getPlayer().getSprite().getY();
			if(cameraX < Tiles.SIZE*CENTERED_MARGIN)
				cameraX = Tiles.SIZE*CENTERED_MARGIN;
			else if(cameraX > maxCameraX)
				cameraX = maxCameraX;
			if(cameraY < Tiles.SIZE*CENTERED_MARGIN)
				cameraY = Tiles.SIZE*CENTERED_MARGIN;
			else if(cameraY > maxCameraY)
				cameraY = maxCameraY;
			game.camera.position.set(cameraX + Tiles.SIZE/2, cameraY + Tiles.SIZE/2, 0);
			game.camera.update();
		}
		
		gameLevel.takeInput(keysPressed);
		gameLevel.update(delta);
		
		if(gameLevel.isWorld()) {
			if(gameLevel.isToSwitch()) {
				gameLevel.setToSwitch(false);
				if(gameLevel.getSwitchId() == 0)
					game.gsm.pop();
				else
					game.gsm.showPuzzleScreen(gameLevel.getSwitchId());
			}
			if(gameLevel.isSolved()) {
				levelsSolved.add((long)levelId);
			}
		}
		else if(!gameLevel.isWorld()) {
			if(gameLevel.isSolved()) {
				levelsSolved.add((long)levelId);
				game.gsm.pop();
			}
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
	
	public HashSet<Long> getLevelsSolved() {
		return levelsSolved;
	}
}
