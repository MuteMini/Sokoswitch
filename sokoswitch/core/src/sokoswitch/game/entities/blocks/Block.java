package sokoswitch.game.entities.blocks;

import sokoswitch.game.entities.Entity;

public abstract class Block extends Entity{

	protected boolean onState;
	protected boolean pushed;
	
	public Block(int id, int x, int y, boolean onState) {
		super(id, x, y);
		this.onState = onState;
		this.pushed = false;
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

	public void setPushed(boolean pushed) {
		this.pushed = pushed;
	}
}
