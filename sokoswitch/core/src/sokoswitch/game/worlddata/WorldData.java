package sokoswitch.game.worlddata;

import java.util.ArrayList;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class WorldData {

	private ArrayList<Vector2> levelPos;
	private int[][] levelData;
	
	public WorldData() {
		this.levelPos = new ArrayList<>();
		this.levelData = new int[0][0];
	}
	
	public WorldData(String string) {
		this();
		setLevelData(new String(string.getBytes()));
		
	}

    public WorldData(FileHandle file) {
    	this();
    	setLevelData(new String(file.readBytes()));
    }
	
    public int getDataSize() {
    	return levelData.length;
    }
    
    public ArrayList<Vector2> getLevelPos() {
    	return levelPos;
    }
	
	public int[] getLevelData(int index) {
		return levelData[index];
	}
	
	private void setLevelData(String fullFile) {
		String[] splitFile = fullFile.split("\n");
		this.levelData = new int[splitFile.length][2];
		int count = 0;
		try {
			for(String line : splitFile) {
				String[] data = line.split(" ");
				levelPos.add(new Vector2(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
				levelData[count][0] = Integer.parseInt(data[2]);
				levelData[count][1] = Integer.parseInt(data[3]);
				count++;
			}
		}
		catch(NumberFormatException e) {}
	}
}
