package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.blocks.abstracts.NumberedBlock;

public class LimiterBlock extends NumberedBlock{
	
	public LimiterBlock(int x, int y, boolean onState, int switchAmount, GameAssetManager manager) {
		super(x, y, onState, switchAmount, manager);
		this.blockId = 3;
	}
}
