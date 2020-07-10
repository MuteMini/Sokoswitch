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
	protected int blockId;
	protected int spriteId;
	
	public Block(int id, int pos, int x, int y, boolean onState, GameAssetManager manager) {
		super(id, pos, x, y, manager);
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
		int touching = 0b00000000;
		for(int i = 0; i < blockTouch.length; i++) {
			if(blockTouch[i]) touching += 0b00000001 << i;
		}

		switch(touching) {
			case 0b00000000:
			case 0b00000001:
			case 0b00000100:
			case 0b00010000:
			case 0b01000000:
			case 0b00000101:
			case 0b00010001:
			case 0b01000001:
			case 0b00010100:
			case 0b01000100:
			case 0b01010000:
			case 0b00010101:
			case 0b01000101:
			case 0b01010001:
			case 0b01010100:
			case 0b01010101:
				this.sprite = manager.getSprite(spriteId, 0);
				break;
			case 0b00000010:
	    	case 0b00000011:
			case 0b00000110:
			case 0b00010010:
			case 0b01000010:
			case 0b00000111:
			case 0b00010011:
			case 0b01000011:
			case 0b00010110:
			case 0b01000110:
			case 0b01010010:
			case 0b00010111:
			case 0b01000111:
			case 0b01010011:
			case 0b01010110:
			case 0b01010111:
				this.sprite = manager.getSprite(spriteId, 1);
				break;
			case 0b00001000:
			case 0b00001001:
			case 0b00001100:
			case 0b00011000:
			case 0b01001000:
			case 0b00001101:
			case 0b00011001:
			case 0b01001001:
			case 0b00011100:
			case 0b01001100:
			case 0b01011000:
			case 0b00011101:
			case 0b01001101:
			case 0b01011001:
			case 0b01011100:
			case 0b01011101:
				this.sprite = manager.getSprite(spriteId, 2);
				break;
			case 0b00100000:
			case 0b00100001:
			case 0b00100100:
			case 0b00110000:
			case 0b01100000:
			case 0b00100101:
			case 0b00110001:
			case 0b01100001:
			case 0b00110100:
			case 0b01100100:
			case 0b01110000:
			case 0b00110101:
			case 0b01100101:
			case 0b01110001:
			case 0b01110100:
			case 0b01110101:
				this.sprite = manager.getSprite(spriteId, 3);
				break;
			case 0b10000000:
			case 0b10000001:
			case 0b10000100:
			case 0b10010000:
			case 0b11000000:
			case 0b10000101:
			case 0b10010001:
			case 0b11000001:
			case 0b10010100:
			case 0b11000100:
			case 0b11010000:
			case 0b10010101:
			case 0b11000101:
			case 0b11010001:
			case 0b11010100:
			case 0b11010101:
				this.sprite = manager.getSprite(spriteId, 4);
				break;
			case 0b00001010:
			case 0b00001011:
			case 0b00011010:
			case 0b01001010:
			case 0b00011011:
			case 0b01001011:
			case 0b01011010:	
			case 0b01011011:
				this.sprite = manager.getSprite(spriteId, 5);
				break;
			case 0b00101000:
			case 0b00101001:
			case 0b00101100:
			case 0b01101000:
			case 0b00101101:
			case 0b01101001:
			case 0b01101100:
			case 0b01101101:
				this.sprite = manager.getSprite(spriteId, 6);
				break;
			case 0b10100000:
			case 0b10100001:
			case 0b10100100:
			case 0b10110000:
			case 0b10100101:
			case 0b10110001:
			case 0b10110100:
			case 0b10110101:
				this.sprite = manager.getSprite(spriteId, 7);
				break;
			case 0b10000010:
			case 0b10000110:
			case 0b10010010:
			case 0b11000010:
			case 0b10010110:
			case 0b11000110:
			case 0b11010010:
			case 0b11010110:
				this.sprite = manager.getSprite(spriteId, 8);
				break;
			case 0b00001110:
			case 0b00001111:
			case 0b00011110:
			case 0b01001110:
			case 0b00011111:
			case 0b01001111:
			case 0b01011110:
			case 0b01011111:
				this.sprite = manager.getSprite(spriteId, 9);
				break;
			case 0b00111000:
			case 0b00111001:
			case 0b00111100:
			case 0b01111000:
			case 0b00111101:
			case 0b01111001:
			case 0b01111100:
			case 0b01111101:
				this.sprite = manager.getSprite(spriteId, 10);
				break;
			case 0b11100000:
			case 0b11100001:
			case 0b11100100:
			case 0b11110000:
			case 0b11100101:
			case 0b11110001:
			case 0b11110100:
			case 0b11110101:
				this.sprite = manager.getSprite(spriteId, 11);
				break;
			case 0b10000011:
			case 0b10000111:
			case 0b10010011:
			case 0b11000011:
			case 0b10010111:
			case 0b11000111:
			case 0b11010011:
			case 0b11010111:
				this.sprite = manager.getSprite(spriteId, 12);
				break;
			case 0b00100010:
			case 0b00100011:
			case 0b00100110:
			case 0b00110010:
			case 0b01100010:
			case 0b00100111:
			case 0b00110011:
			case 0b01100011:
			case 0b00110110:
			case 0b01100110:
			case 0b01110010:
			case 0b00110111:
			case 0b01100111:
			case 0b01110011:
			case 0b01110110:
			case 0b01110111:
				this.sprite = manager.getSprite(spriteId, 13);
				break;
			case 0b10001000:
			case 0b10001001:
			case 0b10001100:
			case 0b10011000:
			case 0b11001000:
			case 0b10001101:
			case 0b10011001:
			case 0b11001001:
			case 0b10011100:
			case 0b11001100:
			case 0b11011000:
			case 0b10011101:
			case 0b11001101:
			case 0b11011001:
			case 0b11011100:
			case 0b11011101:
				this.sprite = manager.getSprite(spriteId, 14);
				break;
			case 0b00101010:
			case 0b00101011:
			case 0b01101010:
			case 0b01101011:
				this.sprite = manager.getSprite(spriteId, 15);
				break;
			case 0b10101000:
			case 0b10101001:
			case 0b10101100:
			case 0b10101101:
				this.sprite = manager.getSprite(spriteId, 16);
				break;
			case 0b10100010:
			case 0b10100110:
			case 0b10110010:
			case 0b10110110:
				this.sprite = manager.getSprite(spriteId, 17);
				break;
			case 0b10001010:
			case 0b10011010:
			case 0b11001010:
			case 0b11011010:
				this.sprite = manager.getSprite(spriteId, 18);
				break;
			case 0b00101110:
			case 0b00101111:
			case 0b01101110:
			case 0b01101111:
				this.sprite = manager.getSprite(spriteId, 19);
				break;
			case 0b10111000:
			case 0b10111001:
			case 0b10111100:
			case 0b10111101:
				this.sprite = manager.getSprite(spriteId, 20);
				break;
			case 0b11100010:
			case 0b11100110:
			case 0b11110010:
			case 0b11110110:
				this.sprite = manager.getSprite(spriteId, 21);
				break;
			case 0b10001011:
			case 0b10011011:
			case 0b11001011:
			case 0b11011011:
				this.sprite = manager.getSprite(spriteId, 22);
				break;
			case 0b00111010:
			case 0b00111011:
			case 0b01111010:
			case 0b01111011:
				this.sprite = manager.getSprite(spriteId, 23);
				break;
			case 0b11101000:
			case 0b11101001:
			case 0b11101100:
			case 0b11101101:
				this.sprite = manager.getSprite(spriteId, 24);
				break;
			case 0b10100011:
			case 0b10100111:
			case 0b10110011:
			case 0b10110111:
				this.sprite = manager.getSprite(spriteId, 25);
				break;
			case 0b10001110:
			case 0b10011110:
			case 0b11001110:
			case 0b11011110:
				this.sprite = manager.getSprite(spriteId, 26);
				break;
			case 0b00111110:
			case 0b00111111:
			case 0b01111110:
			case 0b01111111:
				this.sprite = manager.getSprite(spriteId, 27);
				break;
			case 0b11111000:
			case 0b11111001:
			case 0b11111100:
			case 0b11111101:
				this.sprite = manager.getSprite(spriteId, 28);
				break;
			case 0b11100011:
			case 0b11100111:
			case 0b11110011:
			case 0b11110111:
				this.sprite = manager.getSprite(spriteId, 29);
				break;
			case 0b10001111:
			case 0b10011111:
			case 0b11001111:
			case 0b11011111:
				this.sprite = manager.getSprite(spriteId, 30);
				break;
			case 0b10101010:
				this.sprite = manager.getSprite(spriteId, 31);
				break;
			case 0b10101110:
				this.sprite = manager.getSprite(spriteId, 32);
				break;
			case 0b10111010:
				this.sprite = manager.getSprite(spriteId, 33);
				break;
			case 0b11101010:
				this.sprite = manager.getSprite(spriteId, 34);
				break;
			case 0b10101011:
				this.sprite = manager.getSprite(spriteId, 35);
				break;
			case 0b10111110:
				this.sprite = manager.getSprite(spriteId, 36);
				break;
			case 0b11101110:
				this.sprite = manager.getSprite(spriteId, 37);
				break;
			case 0b10101111:
				this.sprite = manager.getSprite(spriteId, 38);
				break;
			case 0b11111010:
				this.sprite = manager.getSprite(spriteId, 39);
				break;
			case 0b10111011:
				this.sprite = manager.getSprite(spriteId, 40);
				break;
			case 0b11101011:
				this.sprite = manager.getSprite(spriteId, 41);
				break;
			case 0b11111011:
				this.sprite = manager.getSprite(spriteId, 42);
				break;
			case 0b11101111:
				this.sprite = manager.getSprite(spriteId, 43);
				break;
			case 0b10111111:
				this.sprite = manager.getSprite(spriteId, 44);
				break;
			case 0b11111110:
				this.sprite = manager.getSprite(spriteId, 45);
				break;
			case 0b11111111:
				this.sprite = manager.getSprite(spriteId, 46);
				break;
		}
	}
	
	public boolean getState() {
		return onState;
	}
	
	public boolean getPushed() {
		return pushed;
	}
	
	public int getBlockId() {
		return blockId;
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
		this.stateSprite = manager.getSprite(1, onState ? 2 :3);
	}
}
