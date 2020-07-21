package sokoswitch.game.level;

import java.util.HashMap;

public enum LevelPath {
	
	SUBWORLD_4(-4, "0/04.world"),
	SUBWORLD_3(-3, "0/03.world"),
	SUBWORLD_2(-2, "0/02.world"),
	SUBWORLD_1(-1, "0/01.world"),
	WORLD(0, "0/00.world"),
	LEVEL1_1(101, "1/01"),
	LEVEL1_2(102, "1/02"),
	LEVEL1_3(103, "1/03"),
	LEVEL1_4(104, "1/04"),
	LEVEL1_5(105, "1/05"),
	LEVEL1_6(106, "1/06"),
	LEVEL2_1(201, "2/01"),
	LEVEL2_2(202, "2/02"),
	LEVEL2_3(203, "2/03"),
	LEVEL2_4(204, "2/04"),
	LEVEL2_5(205, "2/05"),
	LEVEL2_6(206, "2/06"),
	LEVEL2_7(207, "2/07"),
	LEVEL2_8(208, "2/08"),
	LEVEL2_9(209, "2/09"),
	LEVEL2_10(210, "2/10"),
	LEVEL2_11(211, "2/11"),
	LEVEL2_12(212, "2/12"),
	LEVEL2_13(213, "2/13"),
	LEVEL3_1(301, "3/01"),
	LEVEL3_2(302, "3/02"),
	LEVEL3_a(303, "3/a"),
	LEVEL3_b(304, "3/b"),
	LEVEL3_c(305, "3/c"),
	LEVEL3_d(306, "3/d"),
	LEVEL3_e(307, "3/e"),
	LEVEL3_f(308, "3/f"),
	LEVEL3_g(309, "3/g"),
	LEVEL3_A(390, "3/~a"),
	LEVEL3_B(391, "3/~b"),
	LEVEL3_C(392, "3/~c"),
	LEVEL4_1(401, "4/01"),
	LEVEL4_a(490, "4/a"),
	LEVEL4_b(491, "4/b");
	
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
