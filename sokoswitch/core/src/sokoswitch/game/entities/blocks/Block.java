package sokoswitch.game.entities.blocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.Entity;
import sokoswitch.game.level.Tiles;

public abstract class Block extends Entity{

	protected boolean onState;
	protected boolean pushed;
	protected Sprite stateSprite;
	
	public Block(int id, int x, int y, boolean onState, GameAssetManager manager) {
		super(id, x, y, manager);
		this.onState = onState;
		this.pushed = false;
		updateStateSprite();
	}

	@Override
	public void render(Batch batch) {
		sprite.draw(batch);
		stateSprite.draw(batch);
	}
	
	@Override
	public void move(int direction) {
		super.move(direction);
		pushed = true;
	}
	
	public abstract void switchState();
	
	public boolean movePossible(int direction) {
		return true;
	}
	
	public boolean switchPossible(int direction) {
		return true;
	}

	public boolean connectionPossible(int direction) {
		return true;
	}
	
	public boolean getState() {
		return onState;
	}
	
	public boolean getPushed() {
		return pushed;
	}

	public void setState(boolean onState) {
		this.onState = onState;
	}
	
	public void setPushed(boolean pushed) {
		this.pushed = pushed;
	}
	
	@Override
	public void setSpritePos() {
		sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
		stateSprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	@Override
	public void setSpritePos(float xOffset, float yOffset) {
		sprite.setCenter((x*Tiles.SIZE)+xOffset+(Tiles.SIZE/2), (y*Tiles.SIZE)+yOffset+(Tiles.SIZE/2));
		stateSprite.setCenter((x*Tiles.SIZE)+xOffset+(Tiles.SIZE/2), (y*Tiles.SIZE)+yOffset+(Tiles.SIZE/2));
	}
	
	protected void updateStateSprite() {
		this.stateSprite = manager.getSprite(2, onState ? 1 : 0);
	}
}
