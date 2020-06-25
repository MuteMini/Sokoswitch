package sokoswitch.game.level;

import java.util.HashMap;

public final class LevelPath {
	private static final String LEVEL_DIR = "levels/";
	private static HashMap<Integer, String> levelPath;
	
	static {
		levelPath = new HashMap<>();
		levelPath.put(1, LEVEL_DIR+"1/01.tmx");
		levelPath.put(2, LEVEL_DIR+"1/02.tmx");
		levelPath.put(3, LEVEL_DIR+"1/03.tmx");
		levelPath.put(4, LEVEL_DIR+"1/04.tmx");
		levelPath.put(5, LEVEL_DIR+"1/05.tmx");
		levelPath.put(6, LEVEL_DIR+"1/06.tmx");
		levelPath.put(7, LEVEL_DIR+"2/01.tmx");
		levelPath.put(8, LEVEL_DIR+"2/02.tmx");
		levelPath.put(9, LEVEL_DIR+"2/03.tmx");
		levelPath.put(10, LEVEL_DIR+"2/04.tmx");
		levelPath.put(11, LEVEL_DIR+"2/05.tmx");
		levelPath.put(12, LEVEL_DIR+"2/06.tmx");
		levelPath.put(13, LEVEL_DIR+"2/07.tmx");
		levelPath.put(14, LEVEL_DIR+"2/08.tmx");
		levelPath.put(15, LEVEL_DIR+"2/09.tmx");
		levelPath.put(16, LEVEL_DIR+"2/10.tmx");
		levelPath.put(17, LEVEL_DIR+"2/11.tmx");
		levelPath.put(18, LEVEL_DIR+"a.tmx");
	}
	
	public static String getLevelPath(int id) {
		return levelPath.get(id);
	}
}
