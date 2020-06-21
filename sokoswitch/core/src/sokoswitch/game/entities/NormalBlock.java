package sokoswitch.game.entities;

public class NormalBlock extends Block implements Connectable{

	public NormalBlock(int x, int y, int xMax, int yMax, boolean turnedOn, int connectID) {
		super((turnedOn) ? 4 : 3, x, y, xMax, yMax, turnedOn, connectID);
	}

	@Override
	public void connect() {
		
	}
}
