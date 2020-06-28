package sokoswitch.game.entities;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.level.Tiles;

public class InversePlayer extends Player{

	public InversePlayer(int x, int y, int tag, int facing, GameAssetManager manager) {
		super(x, y, tag, facing, manager);
		setSprite(3);
	}
	
	@Override
	public void move(int direction) {
		int tempDirection = (direction+2)%4;
		if(tempDirection == 0) {
			y += 1;
		}
		else if(tempDirection == 1) {
			x += 1;
		}
		else if(tempDirection == 2) {
			y -= 1;
		}
		else if(tempDirection == 3) {
			x -= 1;
		}	
		setSpritePos();
	}
	
	@Override
	public void setRotation(float rotateOffset) {
		sprite.setRotation((facing*-90)+rotateOffset);
	}
	
	@Override
	public boolean setFacing(byte facing) {
		int tempFacing = (facing+2)%4;
		if(this.facing != tempFacing) {
			this.facing = (byte) tempFacing;
			return true;
		}
		return false;
	}
}
