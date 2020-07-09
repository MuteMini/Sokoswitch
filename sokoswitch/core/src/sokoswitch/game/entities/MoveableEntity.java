package sokoswitch.game.entities;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.level.Tiles;

public class MoveableEntity extends Entity{

	protected byte facing;
	
	public MoveableEntity(int id, int pos, int x, int y, GameAssetManager manager) {
		super(id, pos, x, y, manager);
		this.facing = 0;
	}

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
		setSpritePos();
	}
	
	public byte getFacing() {
		return facing;
	}
	
	public void setSpritePos(float offset) {
		if(this.facing / 2 == 0)
			offset *= -1;
			
		if(this.facing % 2 == 0)
			sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+offset+(Tiles.SIZE/2));
		else if (this.facing % 2 == 1)
			sprite.setCenter((x*Tiles.SIZE)+offset+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	public boolean setFacing(byte facing) {
		if(this.facing != facing) {
			this.facing = facing;
			return true;
		}
		return false;
	}
}
