package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import sokoswitch.game.GameAssetManager;

public class LevelTiles extends Entity{

	private BitmapFont font;
	private String displayNum;
	private int connectedLevel;
	
	public LevelTiles(int x, int y, GameAssetManager manager, int displayNum, int connectedLevel) {
		super(-1, x, y, manager);
		FreeTypeFontGenerator generator = manager.getFont(0);
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
			param.size = 200;
			param.borderWidth = 5;
			param.borderColor = Color.BLACK;
			param.color = new Color(.90f, .90f, .90f, 1);
		this.font = generator.generateFont(param);
		this.displayNum = displayNum+"";
		this.connectedLevel = connectedLevel;
	}

	@Override
	public void render(Batch batch) {
		sprite.draw(batch);
		font.draw(batch, displayNum, sprite.getX(), sprite.getY());
	}
	
	public int getConnectedLevel() {
		return connectedLevel;
	}
}
