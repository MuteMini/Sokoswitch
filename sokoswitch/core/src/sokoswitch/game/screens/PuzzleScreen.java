package sokoswitch.game.screens;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;

import sokoswitch.game.Sokoswitch;
import sokoswitch.game.level.*;

public class PuzzleScreen extends ScreenAdapter{

	private Sokoswitch game;
	
	private int levelId;
	private boolean cameraCentered;
	private Stack<Integer> keysPressed;
	private GameLevel gameLevel;

	public PuzzleScreen(Sokoswitch game, int levelId) {
		this.game = game;
		this.levelId = levelId;
		this.cameraCentered = false;
		this.keysPressed = new Stack<>();
	}
	
	public void resetLevel() {
		gameLevel = new GameLevel(LevelPath.getLevelPath(levelId));
	}
	
	@Override
	public void show() {
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
	}

	@Override
	public void render(float delta) {
		gameLevel.takeInput(keysPressed);
		gameLevel.update(delta);
		
		/*testing*/
		if(gameLevel.isSolved()) {
			Gdx.gl.glClearColor(.5f, .65f, .12f, 1);
		}
		else /*testing*/
			Gdx.gl.glClearColor(.50f, .50f, .50f, 1);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameLevel.render(game.camera);
		
		if(gameLevel.isSolved()) {
			game.gsm.pop();
		}
			
		if(Gdx.input.justTouched()) {
			resetLevel();
			
			/*Vector3 clickPos = game.viewport.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
			Tiles type = gameLevel.locateTilesByPosition(2, clickPos.x, clickPos.y);
			gameLevel.getPlayer(0).setPosition((int)clickPos.x/Tiles.SIZE, (int)clickPos.y/Tiles.SIZE);
			System.out.println(clickPos.x + "," + clickPos.y +"\t");
			System.out.println(type.getId() + " : " + type.getName());*/
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
