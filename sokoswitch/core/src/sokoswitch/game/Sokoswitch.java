package sokoswitch.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.*;
import sokoswitch.game.level.Tiles;
import sokoswitch.game.screens.*;

public class Sokoswitch extends Game {
	
	public final float testingUnit = 1/Tiles.SIZE;
	
	public OrthographicCamera camera;
	public Viewport viewport;
	public GameScreenManager gsm;
	public GameAssetManager gam;
	public ShapeRenderer shapeRenderer;
	
	public float totalDeltaTime;
	public int renderCount;
	
	public int transition;
	private float alpha;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gsm = new GameScreenManager(this);
		gam = new GameAssetManager();
		shapeRenderer = new ShapeRenderer();
		setScreen(gsm.peek());
		
		this.totalDeltaTime = 0;
		this.renderCount = 0;
		this.transition = 0;
		this.alpha = 0;
	}

	@Override
	public void render () {
		camera.update();
		
		screen.render(Gdx.graphics.getDeltaTime());
		
		drawScreenTransition();

		if(transition <= 0 && gsm.peek() != screen) {
			setScreen(gsm.peek());
		}
		
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
	}
	
	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}
	
	private void drawScreenTransition() {
		if(transition != 0) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			Gdx.gl.glEnable(GL20.GL_BLEND);
		}
		switch(transition) {
			case -1:
				alpha -= Gdx.graphics.getDeltaTime()*0.6;
				if(alpha <= 0.1) {
					alpha = 0;
					transition = 0;
				}
				shapeRenderer.setColor(.45f, .45f, .45f, alpha);
				shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				shapeRenderer.end();
				Gdx.gl.glDisable(GL20.GL_BLEND);
				break;
			case 1:
				alpha += Gdx.graphics.getDeltaTime()*0.6;
				if(alpha > 1) {
					alpha = 1;
					transition = -1;
				}
				shapeRenderer.setColor(.45f, .45f, .45f, alpha);
				shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				shapeRenderer.end();
				Gdx.gl.glDisable(GL20.GL_BLEND);
				break;
		}
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
