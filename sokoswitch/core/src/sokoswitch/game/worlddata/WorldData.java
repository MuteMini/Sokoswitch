package sokoswitch.game.worlddata;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class WorldData {

	private ArrayList<Vector2> levelPos;
	private String[] levelDisplay;
	private int[] levelConnected;
	private ArrayList<Long>[] levelPrereq;
	
	@SuppressWarnings("unchecked")
	public WorldData() {
		this.levelPos = new ArrayList<>();
		this.levelConnected = new int[0];
		this.levelDisplay = new String[0];
		this.levelPrereq = new ArrayList[0];
	}

    public WorldData(FileHandle file) {
    	this();
    	setLevelData(file);
    }
	
    public int getDataSize() {
    	return levelConnected.length;
    }
    
    public ArrayList<Vector2> getLevelPos() {
    	return levelPos;
    }
	
	public String getLevelDisplay(int index) {
		return levelDisplay[index];
	}
	
	public int getLevelConnected(int index) {
		return levelConnected[index];
	}
	
	public ArrayList<Long> getLevelPrereq(int index) {
		return levelPrereq[index];
	}
	
	@SuppressWarnings("unchecked")
	private void setLevelData(FileHandle file) {
		JSONParser parser = new JSONParser();
		try {
			Reader reader = file.reader();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			
			JSONArray posArr = (JSONArray) jsonObject.get("pos");
			JSONArray displayArr = (JSONArray) jsonObject.get("displayText");
			JSONArray connectArr = (JSONArray) jsonObject.get("connectedLevel");
			JSONArray prereqArr = (JSONArray) jsonObject.get("prereq");
			
			Iterator<JSONArray> posIterator = posArr.iterator();
			Iterator<String> displayIterator = displayArr.iterator();
			Iterator<Long> connectIterator = connectArr.iterator();
			Iterator<JSONArray> prereqIterator = prereqArr.iterator();
			
			this.levelDisplay = new String[displayArr.size()];
			this.levelConnected = new int[displayArr.size()];
			this.levelPrereq = new ArrayList[displayArr.size()];
			int count = 0;
			while(posIterator.hasNext() && connectIterator.hasNext() && displayIterator.hasNext() && prereqIterator.hasNext()) {
				Iterator<Long> pos = posIterator.next().iterator();
				levelPos.add(new Vector2(pos.next(), pos.next()));
				levelDisplay[count] = displayIterator.next();
				levelConnected[count] = Math.toIntExact(connectIterator.next());
				levelPrereq[count] = new ArrayList<>();
				Iterator<Long> prereq = prereqIterator.next().listIterator();
				prereq.forEachRemaining(levelPrereq[count]::add);
				count++;
            }
		}
		catch(IOException e) {}
		catch(ParseException e) {}
	}
}
