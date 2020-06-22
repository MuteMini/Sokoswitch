package sokoswitch.game.entities;

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
}
