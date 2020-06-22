package sokoswitch.game.entities;

import sokoswitch.game.level.Tiles;

public class Block extends Entity implements Moveable{
	
	protected boolean onState;
	protected boolean pushed;
	
	public Block(int id, int x, int y, int xMax, int yMax, boolean onState) {
		super(id, x, y, xMax, yMax);
		this.onState = onState;
		this.pushed = false;
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
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
		pushed = true;
	}

	public void switchState() {
		this.onState = !this.onState;
		this.sprite = Tiles.getTilesById((onState) ? 4 : 3).getSprite();
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
