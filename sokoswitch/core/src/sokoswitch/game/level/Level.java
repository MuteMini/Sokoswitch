package sokoswitch.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Level {

	public abstract void update(float delta);
	public abstract void render(OrthographicCamera camera, SpriteBatch batch);
	public abstract void dispose();
	
	public Blocks locateBlockByPosition(int layer, float x, float y) {
		return this.locateBlockByCoordinate(layer, (int)(x/Blocks.SIZE), (int)(y/Blocks.SIZE));
	}
	public abstract Blocks locateBlockByCoordinate(int layer, int x, int y);
	
	public abstract int getWidth();
	public abstract int getHeight();
}
