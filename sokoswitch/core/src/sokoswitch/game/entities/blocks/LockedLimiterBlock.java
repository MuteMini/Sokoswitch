package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.blocks.abstracts.NumberedBlock;

public class LockedLimiterBlock extends NumberedBlock{

	public LockedLimiterBlock(int x, int y, boolean onState, int switchAmount, GameAssetManager manager) {
		super(x, y, onState, switchAmount, manager);
		this.blockId = 4;
		this.spriteId = 3;
		updateSprite();
	}

	@Override
	public boolean switchPossibleDirect(int direction) {
		return false;
	}
}
