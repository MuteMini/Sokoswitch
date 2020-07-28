package sokoswitch.game;

import java.util.Stack;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.*;
import sokoswitch.game.screens.*;

public class Sokoswitch extends Game {
	
	public OrthographicCamera camera;
	public Viewport viewport;
	public GameScreenManager gsm;
	public GameAssetManager gam;
	public SpriteBatch batch;
	
	/*holds what keys are being pressed*/
	private Stack<Integer> keysPressed;
	
	/*holds the normal camera's matrix*/
	private Matrix4 normalMatrix;
	
	public float totalDeltaTime;
	public int renderCount;
	
	public int transition;
	private boolean pauseTransition;
	private float animationDelta;
	private float pauseDelta;
	
	@Override
	public void create () {
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		this.gsm = new GameScreenManager(this);
		this.gam = new GameAssetManager();
		this.batch = new SpriteBatch();
		
		this.normalMatrix = this.camera.projection;
		
		setScreen(gsm.peek());
		
		this.viewport.apply();
		
		this.totalDeltaTime = 0;
		this.renderCount = 0;
		this.transition = 0;
		this.pauseTransition = false;
		this.animationDelta = 0;
		this.pauseDelta = 0;
		
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
	            	case Input.Keys.ENTER:
	            		keysPressed.push(5);
	            		break;
	            	case Input.Keys.Z:
	            		keysPressed.push(6);
	            		break;
	            	case Input.Keys.ESCAPE:
	            	case Input.Keys.P:
	            		keysPressed.push(7);
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
	            	case Input.Keys.ESCAPE:
	            		keysPressed.remove((Integer)7);
	            		break;
	            	default:
	            		 return false;
	            }
            return true;
	        }
	    });
	}

	@Override
	public void render () {
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.enableBlending();
		batch.begin();
		
		((GameScreen)screen).setKeysPressed(keysPressed);
		screen.render(Gdx.graphics.getDeltaTime());
		if(batch.isDrawing()) batch.end();
		
		if(transition != 0) drawScreenTransition();

		if(transition <= 0 && gsm.peek() != screen) {
			setScreen(gsm.peek());
		}
		
		//gsm.printStack();
		
		/*framerate testing*/
		totalDeltaTime += Gdx.graphics.getDeltaTime();
		renderCount++;
		if(totalDeltaTime >= 1) {
			System.out.println("update count: " + renderCount);
			System.out.println("other thing: " + Gdx.graphics.getFramesPerSecond());
			renderCount = 0;
			totalDeltaTime = 0;
		}
	}
	
	@Override
	public void dispose () {
		screen.dispose();
		gam.dispose();
		batch.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}
	
	private void drawScreenTransition() {
		batch.setProjectionMatrix(normalMatrix);
		batch.begin();
		
		animationDelta += Gdx.graphics.getDeltaTime() * (pauseTransition ? 0 : 1);
		pauseDelta += Gdx.graphics.getDeltaTime() * (pauseTransition ? 1 : 0);
		
		if(pauseDelta > 0.6) pauseTransition = false;
		
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
		pixmap.setBlending(Pixmap.Blending.None);
		pixmap.setColor(new Color(.4f, .4f, .4f, 1f));
		pixmap.fill();
		switch(transition) {
			case -2:
				float radiusMul1 = (pauseDelta == 0) ? 250 : 500;
				float radius1 = animationDelta*radiusMul1;
				if(radius1 >= 740) {
					animationDelta = 0;
					pauseDelta = 0;
					transition = 0;
					break;
				}
				else if(pauseDelta == 0 && radius1 >= 100) {
					pauseTransition = true;
					animationDelta /= 2;
				}
				pixmap.setColor(new Color(.4f, .4f, .4f, 0f));
				pixmap.fillCircle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, (int)radius1);
				break;
			case -1:
				float a1 = 1-animationDelta*0.6f;
				if(a1 <= 0.1) {
					animationDelta = 0;
					transition = 0;
					break;
				}
				pixmap.setColor(new Color(.4f, .4f, .4f, a1));
				pixmap.fill();
				break;
			case 1:
				float a2 = animationDelta*0.6f;
				if(a2 >= 1) {
					animationDelta = 0;
					transition = -2;
					break;
				}
				pixmap.setColor(new Color(.4f, .4f, .4f, a2));
				pixmap.fill();
				break;
			case 2:
				float radiusMul2 = (pauseDelta == 0) ? 500 : 125;
				float radius2 = 740-animationDelta*radiusMul2;
				if(radius2 <= 0) {
					animationDelta = 0;
					pauseDelta = 0;
					transition = -2;
					break;
				}
				else if(pauseDelta == 0 && radius2 <= 100) {
					pauseTransition = true;
					animationDelta *= 4;
				}
				pixmap.setColor(new Color(.4f, .4f, .4f, 0f));
				pixmap.fillCircle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, (int)radius2);
				break;
		}

		Texture pixmapTexture = new Texture(pixmap, Pixmap.Format.RGBA8888, false);
		Sprite transition = new Sprite(pixmapTexture);
		transition.setPosition(-Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
		transition.draw(batch);
		
		batch.end();
		pixmap.dispose();
	}
}
