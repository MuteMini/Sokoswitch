package sokoswitch.game.entities.blocks.abstracts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.blocks.NormalBlock;
import sokoswitch.game.level.Tiles;

public abstract class NumberedBlock extends NormalBlock {
	
	protected Sprite switchSprite;
	protected int switchAmount;
	
	public NumberedBlock(int x, int y, boolean onState, int switchAmount, GameAssetManager manager) {
		super(x, y, onState, manager);
		this.blockId = 3;
		this.switchAmount = switchAmount;
		updateSwitchSprite();
	}

	@Override
	public void render(Batch batch) {
		super.render(batch);
		switchSprite.draw(batch);
	}
	
	@Override
	public void switchState() {
		super.switchState();
		switchAmount--;
		updateSwitchSprite();
	}
	
	public int getSwitchAmount() {
		return switchAmount;
	}
	
	@Override
	public void setSpritePos() {
		super.setSpritePos();
		switchSprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	@Override
	public void setSpritePos(float offset) {
		super.setSpritePos(offset);
		
		if(facing <= 1) offset *= -1;
			
		if(facing % 2 == 0) switchSprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+offset+(Tiles.SIZE/2));
		else switchSprite.setCenter((x*Tiles.SIZE)+offset+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	protected abstract void updateSwitchSprite();
}