package sokoswitch.game.entities.blocks;

public class NormalBlock extends Block{

	public NormalBlock(int x, int y, boolean turnedOn) {
		super((turnedOn) ? 4 : 3, x, y, turnedOn);
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		super.setSprite((onState) ? 4 : 3);
	}
}
