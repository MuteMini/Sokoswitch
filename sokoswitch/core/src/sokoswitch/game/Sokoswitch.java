package sokoswitch.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;

import sokoswitch.game.level.Tiles;
import sokoswitch.game.screens.*;

public class Sokoswitch extends Game {
	
	public final float testingUnit = 1/Tiles.SIZE;
	
	public OrthographicCamera camera;
	public Viewport viewport;
	public SpriteBatch batch;
	public GameScreenManager gsm;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		gsm = new GameScreenManager(this);
		setScreen(gsm.peek());
	}

	@Override
	public void render () {
		if(getScreen() != gsm.peek())
			setScreen(gsm.peek());
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		screen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		screen.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
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
