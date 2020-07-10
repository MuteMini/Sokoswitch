package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;

public class NormalBlock extends Block{

	public NormalBlock(int x, int y, boolean turnedOn, GameAssetManager manager) {
		super(2, 2, x, y, turnedOn, manager);
		this.blockId = 1;
		this.spriteId = 2;
		updateSprite();
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		updateStateSprite();
	}
}
