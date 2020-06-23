package sokoswitch.game.entities;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity{
	
	/*0=up, 1=right, 2=down, 3=left*/
	private byte facing;
	/*if the player can move into the direction it's facing*/
	private boolean mobile;
	
	public Player(int x, int y, int xMax, int yMax) {
		super(2, x, y, xMax, yMax);
		this.facing = 0;
		this.mobile = true;
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
	
	public boolean setFacing(byte facing) {
		if(this.facing != facing) {
			this.facing = facing;
			return true;
		}
		return false;
	}
	
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
}
