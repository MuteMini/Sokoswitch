package sokoswitch.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import sokoswitch.game.level.Tiles;
import sokoswitch.game.screens.*;

public class Sokoswitch extends Game {
	
	public final float testingUnit = 1/Tiles.SIZE;
	
	public OrthographicCamera camera;
	public Viewport viewport;
	public GameScreenManager gsm;

	public float totalDeltaTime;
	public int renderCount;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gsm = new GameScreenManager(this);
		setScreen(gsm.peek());
		
		totalDeltaTime = 0;
		renderCount = 0;
	}

	@Override
	public void render () {
		if(getScreen() != gsm.peek())
			setScreen(gsm.peek());
		
		camera.update();
		screen.render(Gdx.graphics.getDeltaTime());
		
		/*framerate testing*/
		totalDeltaTime += Gdx.graphics.getDeltaTime();
		renderCount++;
		if(totalDeltaTime >= 1) {
			System.out.println("update count: " + renderCount);
			renderCount = 0;
			totalDeltaTime = 0;
		}
	}
	
	@Override
	public void dispose () {
		screen.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, false); 
	}
	
	public void testing() {
		/*
		Vector2 vector = new Vector2();
		
		FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BalsamiqSans-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 26;
		parameter.color = Color.WHITE;
		font = ftfg.generateFont(parameter);
		ftfg.dispose();
		*/
	}
}
