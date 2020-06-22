package sokoswitch.game.entities;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity implements Moveable{
	
	/*0=up, 1=right, 2=down, 3=left*/
	private byte facing;
	/*if the player can move into the direction it's facing*/
	private boolean mobile;
	
	public Player(int x, int y, int xMax, int yMax) {
		super(2, x, y, xMax, yMax);
		this.facing = 0;
		this.mobile = true;
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
	}
	
	public Vector2 interact() {
		int xOffset = (facing % 2 == 0) ? 0 : 1;
		int yOffset = (facing % 2 == 0) ? 1 : 0;
		xOffset *= (facing == 3) ? -1 : 1;
		yOffset *= (facing == 2) ? -1 : 1;
		return new Vector2(x+xOffset, y+yOffset);
	}
	
	public boolean getMobile() {
		return mobile;
	}
	
	public void setFacing(byte facing) {
		this.facing = facing;
	}
	
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
}
