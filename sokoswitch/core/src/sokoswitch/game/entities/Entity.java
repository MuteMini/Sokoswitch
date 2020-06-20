package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sokoswitch.game.level.Blocks;

public abstract class Entity {

	protected int x, y;
	protected int xMax, yMax;
	protected TextureRegion texture;
	
	public Entity(int id, int x, int y, int xMax, int yMax) {
		this.texture = Blocks.getBlockById(id).getSprite();
		this.x = x;
		this.y = y;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public abstract void update(float delta);
	public abstract void dispose();
	
	public int getXPos() {
		return x * Blocks.SIZE;
	}
	
	public int getYPos() {
		return y * Blocks.SIZE;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
	
	public void moveUp() {
		if(y < yMax) { y += 1; }
	}
	public void moveDown() {
		if(y > 0) { y -= 1; }
	}
	public void moveRight() {
		if(x < xMax) { x += 1; }
	}
	public void moveLeft() {
		if(x > 0) { x -= 1; }
	}
}
