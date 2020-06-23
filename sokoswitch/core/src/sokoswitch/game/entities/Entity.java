package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.level.Tiles;

public abstract class Entity {

	protected int x, y;
	protected int xMax, yMax;
	protected Sprite sprite;
	
	public Entity(int id, int x, int y, int xMax, int yMax) {
		this.sprite = Tiles.getTilesById(id).getSprite();
		this.x = x;
		this.y = y;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	public void dispose() {
		sprite.getTexture().dispose();
	}
	
	public void move(int direction) {
		if(direction == 0) {
			y += 1;
		}
		else if(direction == 1) {
			x += 1;
		}
		else if(direction == 2) {
			y -= 1;
		}
		else if(direction == 3) {
			x -= 1;
		}
	}
	
	public int getXPos() {
		return x * Tiles.SIZE;
	}
	
	public int getYPos() {
		return y * Tiles.SIZE;
	}
	
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
