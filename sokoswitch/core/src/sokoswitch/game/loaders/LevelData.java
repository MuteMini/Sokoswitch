package sokoswitch.game.loaders;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.badlogic.gdx.files.FileHandle;

public class LevelData {

	public int[][] playerPos;
	public boolean[] playerType;
	public int[][] blockPos;
	public ArrayList<Integer[]> blockState;

	public LevelData() {
		this.playerPos = new int[0][2];
		this.playerType = new boolean[0];
		this.blockPos = new int[0][2];
		this.blockState = new ArrayList<>();
	}

    public LevelData(FileHandle file) {
    	this();
    	setLevelData(file);
    }

	@SuppressWarnings("unchecked")
	private void setLevelData(FileHandle file) {
		JSONParser parser = new JSONParser();
		try {
			Reader reader = file.reader();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			
			JSONArray playerArr = (JSONArray) jsonObject.get("players");
			Iterator<JSONArray> playerIterator = playerArr.iterator();

			int count = 0;
			this.playerPos = new int[playerArr.size()][2];
			this.playerType = new boolean[playerArr.size()];
			while(playerIterator.hasNext()) {
				Iterator<Object> playerData = playerIterator.next().iterator();
				this.playerPos[count][0] = Math.toIntExact((long)playerData.next());
				this.playerPos[count][1] = Math.toIntExact((long)playerData.next());
				this.playerType[count] = (boolean)playerData.next();
				count++;
			}
			
			JSONArray blockArr = (JSONArray) jsonObject.get("blocks");
			Iterator<JSONArray> blockIterator = blockArr.iterator();
				
			count = 0;
			this.blockPos = new int[blockArr.size()][2];
			while(blockIterator.hasNext()) {
				Iterator<Object> blockObjects = blockIterator.next().iterator();
				this.blockPos[count][0] = Math.toIntExact((long)blockObjects.next());
				this.blockPos[count][1] = Math.toIntExact((long)blockObjects.next());
				
				int blockType = Math.toIntExact((long)blockObjects.next());
				Integer[] blockData = new Integer[0];
				switch(blockType) {
					case 1:
					case 2:
						blockData = new Integer[2];
						blockData[0] = blockType;
						blockData[1] = ((boolean)blockObjects.next()) ? 1 : 0;
						break;
					case 3:
					case 4:
						blockData = new Integer[3];
						blockData[0] = blockType;
						blockData[1] = ((boolean)blockObjects.next()) ? 1 : 0;
						blockData[2] = (int)blockObjects.next();
						break;
				}
				blockState.add(blockData);
				count++;
			}
		}
		catch(IOException e) {}
		catch(ParseException e) {}
	}
}