package sokoswitch.game.entities;

import java.util.HashSet;
import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.GameAssetManager;

public class LevelRoad extends Entity{
	
	public LevelRoad(int x, int y, GameAssetManager manager) {
		super(0, 1, x, y, manager);
		this.sprite = manager.getSprite(0, 1);
		
		setSpritePos();
	}
	
	public void updateSprite(HashSet<Vector2> levelRoads) {
		int adj = 0b0000;
		if(levelRoads.contains(new Vector2(x, y+1))) adj += 0b0001;
		if(levelRoads.contains(new Vector2(x+1, y))) adj += 0b0010;
		if(levelRoads.contains(new Vector2(x, y-1))) adj += 0b0100;
		if(levelRoads.contains(new Vector2(x-1, y))) adj += 0b1000;
		
		switch(adj) {
			case 0b0010:
			case 0b1000:
			case 0b1010:
				this.sprite = manager.getSprite(0, 0);
				break;
			case 0b0001:
			case 0b0100:
			case 0b0101:
				this.sprite = manager.getSprite(0, 1);
				break;
			case 0b0011:
				this.sprite = manager.getSprite(0, 2);
				break;
			case 0b0110:
				this.sprite = manager.getSprite(0, 3);
				break;
			case 0b1100:
				this.sprite = manager.getSprite(0, 4);
				break;
			case 0b1001:
				this.sprite = manager.getSprite(0, 5);
				break;
			case 0b0111:
				this.sprite = manager.getSprite(0, 6);
				break;
			case 0b1110:
				this.sprite = manager.getSprite(0, 7);
				break;
			case 0b1101:
				this.sprite = manager.getSprite(0, 8);
				break;
			case 0b1011:
				this.sprite = manager.getSprite(0, 9);
				break;
			case 0b1111:
				this.sprite = manager.getSprite(0, 10);
		}
		setSpritePos();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if(obj instanceof LevelRoad) {
			LevelRoad other = (LevelRoad) obj;
			if (this.x != other.x)
				return false;
			if (this.y != other.y)
				return false;
			return true;
		}
		
		return false;
	}
}
