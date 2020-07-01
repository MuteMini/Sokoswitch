package sokoswitch.game.level;

import java.util.HashMap;

public enum Tiles {

	LOCKED_LEVELTILE(-3, 1, 0),
	UNSOLVED_LEVELTILE(-2, 1, 1),
	SOLVED_LEVELTILE(-1, 1, 2),
	EMPTY(0, 0, 0),
	SPACE(1, 1, 0),
	PLAYER(2, 1, 3),
	INVERSE_PLAYER(3, 1, 4),
	BLOCK_OFF(4, 2, 2),
	BLOCK_ON(5, 2, 2),
	LOCKED_BLOCK_OFF(6, 2, 3),
	LOCKED_BLOCK_ON(7, 2, 3);
	
	public final static int SIZE = 256;
	private static HashMap<Integer, Tiles> blockMap;
	
	private int id;
	private int textureId;
	private int texturePos;
	
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
	
	public int getTextureId() {
		return textureId;
	}
	
	public int getTexturePos() {
		return texturePos;
	}
	
	private Tiles(int id, int textureId, int texturePos) {
		this.id = id;
		this.textureId = textureId;
		this.texturePos = texturePos;
	}
}
