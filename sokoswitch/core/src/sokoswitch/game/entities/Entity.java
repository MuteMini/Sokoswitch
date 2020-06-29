package sokoswitch.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.level.Tiles;

public abstract class Entity {
	
	protected int id;
	protected int x, y;
	protected Sprite sprite;
	protected GameAssetManager manager;
	
	public Entity(int id, int x, int y, GameAssetManager manager) {
		this.manager = manager;
		setSprite(id);
		this.x = x;
		this.y = y;
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
	
	public int getId() {
		return id;
	}
	
	public void setSprite(int id) {
		this.id = id;
		Tiles tile = Tiles.getTilesById(id);
		this.sprite = manager.getSprite(tile.getTextureId(), tile.getTexturePos());
	}
	
	public void setSpritePos() {
		sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
