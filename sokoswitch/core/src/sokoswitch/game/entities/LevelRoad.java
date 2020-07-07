package sokoswitch.game.entities;

import java.util.ArrayList;
import sokoswitch.game.GameAssetManager;

public class LevelRoad extends Entity{
	
	public LevelRoad(int x, int y, GameAssetManager manager) {
		super(-4, x, y, manager);
	}
	
	public void updateSprite(ArrayList<LevelRoad> levelRoads) {
		
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
		
		LevelRoad other = (LevelRoad) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		
		return true;
	}
}
