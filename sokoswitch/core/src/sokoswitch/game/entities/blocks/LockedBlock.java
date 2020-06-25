package sokoswitch.game.entities.blocks;

public class LockedBlock extends Block{

	public LockedBlock(int x, int y, boolean turnedOn) {
		super((turnedOn) ? 6 : 5, x, y, turnedOn);
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		super.setSprite((onState) ? 6 : 5);
	}
	
	@Override
	public boolean switchPossible(int direction) {
		return false;
	}
}
