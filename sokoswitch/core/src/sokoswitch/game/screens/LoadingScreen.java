package sokoswitch.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;

import sokoswitch.game.Sokoswitch;

public class LoadingScreen extends GameScreen{

	private Sokoswitch game;

	private float widthGuide;
	private float heightGuide;
	
	private int loadPos;
	private float percent;
	private String loadingString;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	
	private BitmapFont font;
	private GlyphLayout layout;
	
	public LoadingScreen(Sokoswitch game) {
		super();
		this.widthGuide = game.camera.viewportWidth/10f;
		this.heightGuide = game.camera.viewportHeight/10f;
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
		    parameter.size = Gdx.graphics.getHeight()/15;
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
			    case 3:
			    	game.gam.loadFonts();
			    	loadingString = "Loading Fonts...";
			    	break;
		    }
			if (loadPos > 3){
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
		shapeRenderer.rect(widthGuide*2, heightGuide*5, widthGuide*6, 32);
		shapeRenderer.setColor(Color.CYAN);
		shapeRenderer.rect(widthGuide*2, heightGuide*5, widthGuide*6*percent, 32);
		shapeRenderer.end();
		
		batch.begin();
		font.draw(batch, loadingString, widthGuide*5.1f-(layout.width/2), heightGuide*6.2f);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
