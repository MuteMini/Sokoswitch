package sokoswitch.game.level;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public enum Tiles {

	EMPTY(0, false, "empty"),
	SPACE(1, false, "space"),
	PLAYER(2, true, "player"),
	BLOCK_OFF(3, true, "off block"),
	BLOCK_ON(4, true, "on block"),
	LOCKED_BLOCK_OFF(5, true, "off locked block"),
	LOCKED_BLOCK_ON(6, true, "on locked block");
	
	public final static int SIZE = 256;
	public final static String ASSET_PATH = "puzzlePrototype.png";
	private static HashMap<Integer, Tiles> blockMap;
	
	private final TextureRegion[][] tr = TextureRegion.split(new Texture(ASSET_PATH), SIZE, SIZE);
	
	private int id;
	private boolean collideable;
	private String name;
	private TextureRegion texture;
	
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

	public boolean isCollideable() {
		return collideable;
	}
	
	public String getName() {
		return name;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
	
	private Tiles(int id, boolean collideable, String name) {
		this.id = id;
		this.collideable = collideable;
		this.name = name;
		if(id != 0) {
			int xIndex = (id-1)%4;
			int yIndex = (id-1)/4;
			texture = tr[yIndex][xIndex];
		}
		else {
			texture = null;
		}
	}
}
