package sokoswitch.game.entities.blocks;

public class NormalBlock extends Block{

	public NormalBlock(int x, int y, int xMax, int yMax, boolean turnedOn) {
		super((turnedOn) ? 4 : 3, x, y, xMax, yMax, turnedOn);
	}
}
