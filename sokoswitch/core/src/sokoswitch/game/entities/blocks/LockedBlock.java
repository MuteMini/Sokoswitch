package sokoswitch.game.entities.blocks;

import sokoswitch.game.level.Tiles;

public class LockedBlock extends Block{

	public LockedBlock(int x, int y, boolean turnedOn) {
		super((turnedOn) ? 6 : 5, x, y, turnedOn);
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		this.sprite = Tiles.getTilesById((onState) ? 6 : 5).getSprite();
	}
	
	@Override
	public boolean switchPossible(int direction) {
		return false;
	}
}
