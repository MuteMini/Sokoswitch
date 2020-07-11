package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.blocks.abstracts.NumberedBlock;

public class LimitedBlock extends NumberedBlock{
	
	public LimitedBlock(int x, int y, boolean onState, int switchAmount, GameAssetManager manager) {
		super(x, y, onState, switchAmount, manager);
		this.blockId = 3;
	}
	
	@Override
	public boolean isSolved() {
		return onState;
	}
	
	@Override
	public boolean switchPossible(int direction) {
		return switchAmount > 0;
	}

	@Override
	protected void updateSwitchSprite() {
		this.switchSprite = manager.getSprite(1, 4+switchAmount);
	}
}
