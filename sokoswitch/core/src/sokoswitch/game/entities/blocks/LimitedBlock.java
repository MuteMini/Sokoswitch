package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;

public class LimitedBlock extends NormalBlock{

	public LimitedBlock(int x, int y, boolean onState, GameAssetManager manager) {
		super(x, y, onState, manager);
		this.blockId = 3;
	}

}
