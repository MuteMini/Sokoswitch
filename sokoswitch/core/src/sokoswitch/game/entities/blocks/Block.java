package sokoswitch.game.entities.blocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.MoveableEntity;
import sokoswitch.game.level.Tiles;

public abstract class Block extends MoveableEntity{

	protected Sprite stateSprite;
	protected boolean onState;
	protected boolean pushed;
	protected boolean[] blockTouch;
	protected int spriteId;
	
	public Block(int id, int x, int y, boolean onState, GameAssetManager manager) {
		super(id, x, y, manager);
		this.onState = onState;
		this.pushed = false;
		this.blockTouch = new boolean[8];
		updateStateSprite();
	}

	@Override
	public void render(Batch batch) {
		sprite.draw(batch);
		stateSprite.draw(batch);
	}
	
	@Override
	public void move(int direction) {
		super.move(direction);
		pushed = true;
	}
	
	public abstract void switchState();
	
	public boolean movePossible(int direction) {
		return true;
	}
	
	public boolean switchPossible(int direction) {
		return true;
	}

	public boolean connectionPossible(int direction) {
		return true;
	}
	
	public void resetBlocksTouched() {
		blockTouch = new boolean[8];
	}
	
	public void blocksTouching(int index) {
		blockTouch[index] = true;
	}
	
	public void updateSprite() {
		int touching = 0;
		if(blockTouch[1]) touching++;
		if(blockTouch[3]) touching++;
		if(blockTouch[4]) touching++;
		if(blockTouch[6]) touching++;
		
		switch(touching) {
			case 0:
				this.sprite = manager.getSprite(spriteId, 0);
				break;
			case 1:
				if(blockTouch[1])
					this.sprite = manager.getSprite(spriteId, 1);
				else if(blockTouch[4])
					this.sprite = manager.getSprite(spriteId, 2);
				else if(blockTouch[6])
					this.sprite = manager.getSprite(spriteId, 3);
				else if(blockTouch[3])
					this.sprite = manager.getSprite(spriteId, 4);
				break;
			case 2:
				if(blockTouch[1]) {
					if(blockTouch[4]) {
						if(blockTouch[2])
							this.sprite = manager.getSprite(spriteId, 9);
						else
							this.sprite = manager.getSprite(spriteId, 5);
					}
					else if(blockTouch[3]) {
						if(blockTouch[0])
							this.sprite = manager.getSprite(spriteId, 12);
						else
							this.sprite = manager.getSprite(spriteId, 8);
					}
					else if(blockTouch[6])
						this.sprite = manager.getSprite(spriteId, 13);
				}
				else if(blockTouch[6]) {
					if(blockTouch[4]) {
						if(blockTouch[7])
							this.sprite = manager.getSprite(spriteId, 10);
						else
							this.sprite = manager.getSprite(spriteId, 6);
					}
					else if(blockTouch[3]) {
						if(blockTouch[5])
							this.sprite = manager.getSprite(spriteId, 11);
						else
							this.sprite = manager.getSprite(spriteId, 7);
					}
				}
				else 
					this.sprite = manager.getSprite(spriteId, 14);
				break;
			case 3:
				if(blockTouch[1]) {
					if(blockTouch[4]) {
						if(blockTouch[6]) {
							if(blockTouch[2]) {
								if(blockTouch[7])
									this.sprite = manager.getSprite(spriteId, 27);
								else
									this.sprite = manager.getSprite(spriteId, 19);
							}
							else if(blockTouch[7])
								this.sprite = manager.getSprite(spriteId, 23);
							else
								this.sprite = manager.getSprite(spriteId, 15);
						}
						else {
							if(blockTouch[0]) {
								if(blockTouch[2])
									this.sprite = manager.getSprite(spriteId, 30);
								else
									this.sprite = manager.getSprite(spriteId, 22);
							}
							else if(blockTouch[2])
								this.sprite = manager.getSprite(spriteId, 26);
							else
								this.sprite = manager.getSprite(spriteId, 18);
						}
					}
					else {
						if(blockTouch[0]) {
							if(blockTouch[5])
								this.sprite = manager.getSprite(spriteId, 29);
							else
								this.sprite = manager.getSprite(spriteId, 25);
						}
						else if(blockTouch[5])
							this.sprite = manager.getSprite(spriteId, 21);
						else
							this.sprite = manager.getSprite(spriteId, 17);
					}
				}
				else {
					if(blockTouch[5]) {
						if(blockTouch[7])
							this.sprite = manager.getSprite(spriteId, 28);
						else
							this.sprite = manager.getSprite(spriteId, 24);
					}
					else if(blockTouch[7])
						this.sprite = manager.getSprite(spriteId, 20);
					else
						this.sprite = manager.getSprite(spriteId, 16);
				}
				break;
			case 4:
				if(blockTouch[0]) {
					if(blockTouch[2]) {
						if(blockTouch[5]) {
							if(blockTouch[7])
								this.sprite = manager.getSprite(spriteId, 46);
							else
								this.sprite = manager.getSprite(spriteId, 43);
						}
						else if(blockTouch[7])
							this.sprite = manager.getSprite(spriteId, 44);
						else
							this.sprite = manager.getSprite(spriteId, 38);
					}
					else if(blockTouch[5]) {
						if(blockTouch[7])
							this.sprite = manager.getSprite(spriteId, 42);
						else
							this.sprite = manager.getSprite(spriteId, 41);
					}
					else if(blockTouch[7])
						this.sprite = manager.getSprite(spriteId, 40);
					else
						this.sprite = manager.getSprite(spriteId, 35);
				}
				else if(blockTouch[2]) {
					if(blockTouch[5]) {
						if(blockTouch[7])
							this.sprite = manager.getSprite(spriteId, 45);
						else
							this.sprite = manager.getSprite(spriteId, 37);
					}
					else if(blockTouch[7])
						this.sprite = manager.getSprite(spriteId, 36);
					else
						this.sprite = manager.getSprite(spriteId, 32);
				}
				else if(blockTouch[5]) {
					if(blockTouch[7])
						this.sprite = manager.getSprite(spriteId, 39);
					else
						this.sprite = manager.getSprite(spriteId, 34);
				}
				else if(blockTouch[7])
					this.sprite = manager.getSprite(spriteId, 33);
				else
					this.sprite = manager.getSprite(spriteId, 31);
				break;
		}
		
		for(boolean b : blockTouch) {
			System.out.print(b + " ");
		}
		System.out.println();
	}
	
	public boolean getState() {
		return onState;
	}
	
	public boolean getPushed() {
		return pushed;
	}

	public void setState(boolean onState) {
		this.onState = onState;
	}
	
	public void setPushed(boolean pushed) {
		this.pushed = pushed;
	}

	@Override
	public void setSpritePos() {
		sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
		stateSprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
	}
	
	@Override
	public void setSpritePos(float offset) {
		if(facing <= 1)
			offset *= -1;
			
		if(facing % 2 == 0) {
			sprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+offset+(Tiles.SIZE/2));
			stateSprite.setCenter((x*Tiles.SIZE)+(Tiles.SIZE/2), (y*Tiles.SIZE)+offset+(Tiles.SIZE/2));
		}
		else {
			sprite.setCenter((x*Tiles.SIZE)+offset+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
			stateSprite.setCenter((x*Tiles.SIZE)+offset+(Tiles.SIZE/2), (y*Tiles.SIZE)+(Tiles.SIZE/2));
		}	
	}
	
	protected void updateStateSprite() {
		this.stateSprite = manager.getSprite(1, onState ? 5 : 6);
	}
}
