package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;

public class LockedBlock extends Block{

	public LockedBlock(int x, int y, boolean turnedOn, GameAssetManager manager) {
		super(2, 3, x, y, turnedOn, manager);
		this.spriteId = 3;
		updateSprite();
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		updateStateSprite();
	}
	
	@Override
	public boolean switchPossible(int direction) {
		return false;
	}
}
