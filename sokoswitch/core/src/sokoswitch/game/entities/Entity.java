package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.level.Tiles;

public abstract class Entity {
	
	protected int id;
	protected int x, y;
	protected Sprite sprite;
	
	public Entity(int id, int x, int y) {
		this.sprite = new Sprite(Tiles.getTilesById(id).getTexture());
		this.id = id;
		this.x = x;
		this.y = y;
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
		setSpritePos();
	}
	
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getId() {
		return id;
	}
	
	public void setSprite(int id) {
		this.id = id;
		this.sprite = new Sprite(Tiles.getTilesById(id).getTexture());
	}
	
	public void setSpritePos() {
		sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	public void setSpritePos(float xOffset, float yOffset) {
		sprite.setCenter((x*Tiles.SIZE)+xOffset+(Tiles.SIZE/2), (y*Tiles.SIZE)+yOffset+(Tiles.SIZE/2));
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
