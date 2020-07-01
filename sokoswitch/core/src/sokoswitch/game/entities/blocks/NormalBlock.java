package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;

public class NormalBlock extends Block{

	public NormalBlock(int x, int y, boolean turnedOn, GameAssetManager manager) {
		super(4, x, y, turnedOn, manager);
		this.spriteId = 2;
		updateSprite();
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		updateStateSprite();
	}
}
