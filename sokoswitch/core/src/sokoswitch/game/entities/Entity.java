package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.level.Tiles;

public abstract class Entity {
	
	protected int x, y;
	protected Sprite sprite;
	protected GameAssetManager manager;
	
	public Entity(int id, int pos, int x, int y, GameAssetManager manager) {
		this.manager = manager;
		this.x = x;
		this.y = y;
		setSprite(id, pos);
	}
	
	public void render(Batch batch) {
		sprite.draw(batch);
	}
	
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(int id, int pos) {
		this.sprite = manager.getSprite(id, pos);
	}
	
	public void setSpritePos() {
		sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
