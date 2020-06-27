package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.level.Tiles;

public abstract class Entity {
	
	protected int id;
	protected int x, y;
	protected byte facing;
	protected Sprite sprite;
	protected GameAssetManager manager;
	
	public Entity(int id, int x, int y, GameAssetManager manager) {
		this.manager = manager;
		setSprite(id);
		this.x = x;
		this.y = y;
		this.facing = 0;
	}
	
	public void render(Batch batch) {
		sprite.draw(batch);
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
	
	public byte getFacing() {
		return facing;
	}
	
	public void setSprite(int id) {
		this.id = id;
		Tiles tile = Tiles.getTilesById(id);
		this.sprite = manager.getSprite(tile.getTextureId(), tile.getTexturePos());
	}
	
	public void setSpritePos() {
		sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	public void setSpritePos(float offset) {
		if(this.facing / 2 == 0)
			offset *= -1;
			
		if(this.facing % 2 == 0)
			sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+offset+(Tiles.SIZE/2));
		else if (this.facing % 2 == 1)
			sprite.setCenter((x*Tiles.SIZE)+offset+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	public boolean setFacing(byte facing) {
		if(this.facing != facing) {
			this.facing = facing;
			System.out.println(facing);
			return true;
		}
		return false;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
