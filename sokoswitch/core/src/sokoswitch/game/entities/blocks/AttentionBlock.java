package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.blocks.abstracts.NumberedBlock;

public class AttentionBlock extends NumberedBlock{

	public AttentionBlock(int x, int y, boolean onState, int switchAmount, GameAssetManager manager) {
		super(x, y, onState, switchAmount, manager);
		this.blockId = 4;
	}

	@Override
	public boolean isSolved() {
		return onState && switchAmount == 0;
	}
	
	@Override
	public boolean switchPossible(int direction) {
		return switchAmount > 0;
	}

	@Override
	protected void updateSwitchSprite() {
		this.switchSprite = manager.getSprite(1, 9+(5-switchAmount));
	}
}
