package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;

import sokoswitch.game.Sokoswitch;

public class LoadingScreen extends ScreenAdapter{

	private Sokoswitch game;

	private final float WIDTH_GUIDE;
	private final float HEIGHT_GUIDE;
	
	private int loadPos;
	private float percent;
	private String loadingString;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	private GlyphLayout layout;
	
	public LoadingScreen(Sokoswitch game) {
		this.WIDTH_GUIDE = game.camera.viewportWidth/10f;
		this.HEIGHT_GUIDE = game.camera.viewportHeight/10f;
		this.game = game;
	}
	
	@Override
	public void show() {
		this.shapeRenderer = new ShapeRenderer();
		this.batch = new SpriteBatch();
		
		this.loadPos = 0;
		this.loadingString = "Loading Sprites...";
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BalsamiqSans-Regular.ttf"));
	    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	    parameter.size = 48;
	    parameter.borderWidth = 1;
	    parameter.color = new Color(.90f, .90f, .90f, 1);
	    this.font = generator.generateFont(parameter);
	    this.layout = new GlyphLayout(font, loadingString);
	    generator.dispose();
	}

	public void update(float delta) {
		if(game.gam.manager.update()) {
		    loadPos += 1;
		    switch(loadPos){
			    case 1:	
			    	game.gam.loadImages(); 
			    	loadingString = "Loading Sprites...";
			    	break;
			    case 2:
			    	game.gam.loadLevels();
			    	loadingString = "Loading Levels...";
			    	break;
		    }
			if (loadPos > 2){
				loadingString = "Loading Complete!";
				percent = 1;
				game.gam.initializeValues();
				game.gsm.showMenu();
				game.transition = 1;
		    }
			this.layout = new GlyphLayout(font, loadingString);
		}
		else {
			percent = Interpolation.linear.apply(percent, game.gam.manager.getProgress(), 0.1f);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.35f, .35f, .35f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(WIDTH_GUIDE*2, HEIGHT_GUIDE*5, WIDTH_GUIDE*6, 32);
		shapeRenderer.setColor(Color.CYAN);
		shapeRenderer.rect(WIDTH_GUIDE*2, HEIGHT_GUIDE*5, WIDTH_GUIDE*6*percent, 32);
		shapeRenderer.end();
		
		batch.begin();
		font.draw(batch, loadingString, WIDTH_GUIDE*5.1f-(layout.width/2), HEIGHT_GUIDE*6.1f);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, false); 
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}
}
