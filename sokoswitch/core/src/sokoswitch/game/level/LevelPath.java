package sokoswitch.game.level;

import java.util.HashMap;

public enum LevelPath {
	
	SUBWORLD_4(-4, "0/04.world"),
	SUBWORLD_3(-3, "0/03.world"),
	SUBWORLD_2(-2, "0/02.world"),
	SUBWORLD_1(-1, "0/01.world"),
	WORLD(0, "0/00.world"),
	LEVEL1_1(1, "1/01"),
	LEVEL1_2(2, "1/02"),
	LEVEL1_3(3, "1/03"),
	LEVEL1_4(4, "1/04"),
	LEVEL1_5(5, "1/05"),
	LEVEL1_6(6, "1/06"),
	LEVEL2_1(7, "2/01"),
	LEVEL2_2(8, "2/02"),
	LEVEL2_3(9, "2/03"),
	LEVEL2_4(10, "2/04"),
	LEVEL2_5(11, "2/05"),
	LEVEL2_6(12, "2/06"),
	LEVEL2_7(13, "2/07"),
	LEVEL2_8(14, "2/08"),
	LEVEL2_9(15, "2/09"),
	LEVEL2_10(16, "2/10"),
	LEVEL2_11(17, "2/11"),
	LEVEL2_12(18, "2/12"),
	LEVEL2_13(19, "2/13"),
	LEVEL3_1(20, "3/01"),
	LEVEL3_2(21, "3/02"),
	LEVEL3_a(22, "3/a"),
	LEVEL3_b(23, "3/b"),
	LEVEL3_c(24, "3/c"),
	LEVEL3_d(25, "3/d"),
	LEVEL3_e(26, "3/e"),
	LEVEL3_f(27, "3/f"),
	LEVEL3_g(28, "3/g"),
	LEVEL3_A(29, "3/~a"),
	TEST(30, "test");
	
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
