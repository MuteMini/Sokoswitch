package sokoswitch.game.level;

import java.util.HashMap;

public final class LevelPath {
	private static HashMap<Integer, String> levelPath;
	
	static {
		levelPath = new HashMap<>();
		levelPath.put(1, "e.tmx");
	}
	
	public static String getLevelPath(int id) {
		return levelPath.get(id);
	}
}
