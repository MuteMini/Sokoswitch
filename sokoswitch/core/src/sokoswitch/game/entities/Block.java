package sokoswitch.game.entities;

public class Block extends Entity implements Moveable{
	
	protected int connectID;
	protected boolean turnedOn;
	
	public Block(int id, int x, int y, int xMax, int yMax, boolean turnedOn, int connectID) {
		super(id, x, y, xMax, yMax);
		this.turnedOn = turnedOn;
		this.connectID = connectID;
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void move(int direction) {
		
	}
}
