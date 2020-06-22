package sokoswitch.game.entities;

public class NormalBlock extends Block implements Switchable{

	public NormalBlock(int x, int y, int xMax, int yMax, boolean turnedOn) {
		super((turnedOn) ? 4 : 3, x, y, xMax, yMax, turnedOn);
	}

	@Override
	public boolean switchPossible(int direction) {
		return true;
	}
}
