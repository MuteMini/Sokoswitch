package sokoswitch.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class Level {

	public abstract void update(float delta);
	public abstract void render(OrthographicCamera camera);
	public abstract void dispose();
	
	public Tiles locateTilesByPosition(int layer, float x, float y) {
		return this.locateTilesByCoordinate(layer, (int)(x/Tiles.SIZE), (int)(y/Tiles.SIZE));
	}
	public abstract Tiles locateTilesByCoordinate(int layer, int x, int y);
	
	public abstract int getWidth();
	public abstract int getHeight();
}
