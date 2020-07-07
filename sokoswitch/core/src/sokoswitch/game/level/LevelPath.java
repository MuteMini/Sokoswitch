package sokoswitch.game.level;

import java.util.HashMap;

public enum LevelPath {
	
	SUBWORLD_4(-4, "0/04.world"),
	SUBWORLD_3(-3, "0/03.world"),
	SUBWORLD_2(-2, "0/02.world"),
	SUBWORLD_1(-1, "0/01.world"),
	WORLD(0, "0/00.world"),
	LEVEL1_1(1, "1/01.tmx"),
	LEVEL1_2(2, "1/02.tmx"),
	LEVEL1_3(3, "1/03.tmx"),
	LEVEL1_4(4, "1/04.tmx"),
	LEVEL1_5(5, "1/05.tmx"),
	LEVEL1_6(6, "1/06.tmx"),
	LEVEL2_1(7, "2/01.tmx"),
	LEVEL2_2(8, "2/02.tmx"),
	LEVEL2_3(9, "2/03.tmx"),
	LEVEL2_4(10, "2/04.tmx"),
	LEVEL2_5(11, "2/05.tmx"),
	LEVEL2_6(12, "2/06.tmx"),
	LEVEL2_7(13, "2/07.tmx"),
	LEVEL2_8(14, "2/08.tmx"),
	LEVEL2_9(15, "2/09.tmx"),
	LEVEL2_10(16, "2/10.tmx"),
	LEVEL2_11(17, "2/11.tmx"),
	LEVEL2_12(18, "2/12.tmx"),
	LEVEL2_13(19, "2/13.tmx"),
	LEVEL3_1(20, "3/01.tmx"),
	LEVEL3_2(21, "3/02.tmx"),
	LEVEL3_a(22, "3/a.tmx"),
	LEVEL3_b(23, "3/b.tmx"),
	LEVEL3_c(24, "3/c.tmx"),
	LEVEL3_d(25, "3/d.tmx"),
	LEVEL3_e(26, "3/e.tmx"),
	LEVEL3_f(27, "3/f.tmx"),
	LEVEL3_g(28, "3/g.tmx"),
	LEVEL3_A(29, "3/~a.tmx");
	
	private static final String LEVEL_DIR = "levels/";
	private static HashMap<Integer, LevelPath> levelMap;
	
	private int levelNum;
	private String filePath;
	
	static {
		levelMap = new HashMap<>();
		for(LevelPath ob : LevelPath.values()) {
			levelMap.put(ob.getLevelNum(), ob);
		}
	}
	
	public static LevelPath getLevelPath(int id) {
		return levelMap.get(id);
	}

	public int getLevelNum() {
		return levelNum;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	private LevelPath(int levelNum, String filePath) {
		this.levelNum = levelNum;
		this.filePath = LEVEL_DIR+filePath;
	}
}
