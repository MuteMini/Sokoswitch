package sokoswitch.game.entities.blocks;

import sokoswitch.game.level.Tiles;

public class NormalBlock extends Block{

	public NormalBlock(int x, int y, boolean turnedOn) {
		super((turnedOn) ? 4 : 3, x, y, turnedOn);
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		this.sprite = Tiles.getTilesById((onState) ? 4 : 3).getSprite();
	}
}
