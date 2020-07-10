package sokoswitch.game.level;

import java.util.HashMap;

public enum Tiles {

	EMPTY(0, false),
	SPACE(1, true);
	
	public final static int SIZE = 256;
	private static HashMap<Integer, Tiles> blockMap;
	
	private int id;
	private boolean moveOn;
	
	static {
		blockMap = new HashMap<>();
		for(Tiles ob : Tiles.values()) {
			blockMap.put(ob.getId(), ob);
		}
	}
	
	public static Tiles getTilesById(int id) {
		return blockMap.get(id);
	}
	
	public int getId() {
		return id;
	}
	
	public boolean getMoveOn() {
		return moveOn;
	}
	
	private Tiles(int id, boolean moveOn) {
		this.id = id;
		this.moveOn = moveOn;
	}
}
