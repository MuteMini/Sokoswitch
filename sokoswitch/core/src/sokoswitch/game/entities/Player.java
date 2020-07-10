package sokoswitch.game.entities;

import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.GameAssetManager;

public class Player extends MoveableEntity{
	
	/*if the player can move into the direction it's facing*/
	protected boolean mobile;
	/*if the player has chosen to rotate*/
	protected boolean rotate;
	
	public Player(int x, int y, int facing, GameAssetManager manager) {
		super(1, 0, x, y, manager);
		this.facing = (byte)facing;
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
	
	public void setRotation(float rotateOffset) {
		sprite.setRotation((facing*-90)+rotateOffset);
	}
	
	public boolean getMobile() {
		return mobile;
	}
	
	public boolean getRotate() {
		return rotate;
	}

	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
	
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
}
