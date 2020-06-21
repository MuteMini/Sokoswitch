package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sokoswitch.game.level.Tiles;

public abstract class Entity {

	protected int x, y;
	protected int xMax, yMax;
	protected TextureRegion texture;
	
	public Entity(int id, int x, int y, int xMax, int yMax) {
		this.texture = Tiles.getTilesById(id).getSprite();
		this.x = x;
		this.y = y;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public abstract void update(float delta);
	
	public void dispose() {
		texture.getTexture().dispose();
	}
	
	public int getXPos() {
		return x * Tiles.SIZE;
	}
	
	public int getYPos() {
		return y * Tiles.SIZE;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
}
