package sokoswitch.game.entities.blocks;

import sokoswitch.game.GameAssetManager;

public class NormalBlock extends Block{

	public NormalBlock(int x, int y, boolean turnedOn, GameAssetManager manager) {
		super(3, x, y, turnedOn, manager);
	}
	
	@Override
	public void switchState() {
		this.onState = !this.onState;
		updateStateSprite();
	}
}
