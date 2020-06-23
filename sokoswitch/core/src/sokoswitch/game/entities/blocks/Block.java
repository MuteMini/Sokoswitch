package sokoswitch.game.entities.blocks;

import sokoswitch.game.entities.Entity;
import sokoswitch.game.level.Tiles;

public class Block extends Entity{

	protected boolean onState;
	protected boolean pushed;
	
	public Block(int id, int x, int y, int xMax, int yMax, boolean onState) {
		super(id, x, y, xMax, yMax);
		this.onState = onState;
		this.pushed = false;
	}

	@Override
	public void move(int direction) {
		super.move(direction);
		pushed = true;
	}
	
	public void switchState() {
		this.onState = !this.onState;
		this.sprite = Tiles.getTilesById((onState) ? 4 : 3).getSprite();
	}
	
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

	public void setPushed(boolean pushed) {
		this.pushed = pushed;
	}
}
