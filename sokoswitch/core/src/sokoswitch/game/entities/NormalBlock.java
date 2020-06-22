package sokoswitch.game.entities;

public class NormalBlock extends Block implements Connectable{

	public NormalBlock(int x, int y, int xMax, int yMax, boolean turnedOn) {
		super((turnedOn) ? 4 : 3, x, y, xMax, yMax, turnedOn);
	}

	@Override
	public boolean connect(Block b) {
		return false;
	}
}
