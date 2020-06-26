package sokoswitch.game.entities;

import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.GameAssetManager;

public class Player extends Entity{
	
	/*0=up, 1=right, 2=down, 3=left*/
	private byte facing;
	/*if the player can move into the direction it's facing*/
	private boolean mobile;
	/*if the player has chosen to rotate*/
	private boolean rotate;
	/*holds the "tag" of the player for equal method*/
	private int tag;
	
	public Player(int x, int y, int tag, GameAssetManager manager) {
		super(2, x, y, manager);
		this.tag = tag;
		this.facing = 0;
		this.mobile = false;
		this.rotate = false;
	}
	
	public Vector2 interact() {
		int xOffset = (facing % 2 == 0) ? 0 : 1;
		int yOffset = (facing % 2 == 0) ? 1 : 0;
		xOffset *= (facing == 3) ? -1 : 1;
		yOffset *= (facing == 2) ? -1 : 1;
		return new Vector2(x+xOffset, y+yOffset);
	}
	
	public byte getFacing() {
		return facing;
	}
	
	public boolean getMobile() {
		return mobile;
	}
	
	public boolean getRotate() {
		return rotate;
	}
	
	public int getTag() {
		return tag;
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
	
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
}
