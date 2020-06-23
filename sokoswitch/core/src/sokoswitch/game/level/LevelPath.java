package sokoswitch.game.level;

import java.util.HashMap;

public final class LevelPath {
	private static HashMap<Integer, String> levelPath;
	
	static {
		levelPath = new HashMap<>();
		levelPath.put(1, "01.tmx");
		levelPath.put(2, "05.tmx");
		levelPath.put(3, "03.tmx");
		levelPath.put(4, "c-1.tmx");
		levelPath.put(5, "testing.tmx");
		levelPath.put(6, "disconnect.tmx");
	}
	
	public static String getLevelPath(int id) {
		return levelPath.get(id);
	}
}
