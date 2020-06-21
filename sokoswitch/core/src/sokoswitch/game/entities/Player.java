package sokoswitch.game.entities;

public class Player extends Entity implements Moveable{
	
	/*0=up, 1=right, 2=down, 3=left*/
	private byte facing;
	
	public Player(int x, int y, int xMax, int yMax) {
		super(2, x, y, xMax, yMax);
		this.facing = 0;
	}

	@Override
	public void update(float delta) {
		
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int[] interact() {
		int xOffset = (facing / 2 == 0) ? 0 : 1;
		int yOffset = (facing / 2 == 0) ? 1 : 0;
		xOffset *= (facing == 3) ? -1 : 1;
		yOffset *= (facing == 2) ? -1 : 1;
		return new int[] {x+xOffset, y+yOffset};
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
}
