package sokoswitch.game.loaders;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class WorldData {

	public boolean horizontal;
	public int[] levelSize;
	public ArrayList<Vector2> levelPos;
	public int[] levelConnected;
	public String[] levelDisplay;
	public ArrayList<Long>[] levelPrereq;
	public int[] levelReqSize;
	
	@SuppressWarnings("unchecked")
	public WorldData() {
		this.levelSize = new int[2];
		this.levelPos = new ArrayList<>();
		this.levelConnected = new int[0];
		this.levelDisplay = new String[0];
		this.levelPrereq = new ArrayList[0];
		this.levelReqSize = new int[0];
	}

    public WorldData(FileHandle file) {
    	this();
    	setLevelData(file);
    }
	
    public int getDataSize() {
    	return levelConnected.length;
    }
	
	@SuppressWarnings("unchecked")
	private void setLevelData(FileHandle file) {
		JSONParser parser = new JSONParser();
		try {
			Reader reader = file.reader();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			
			Iterator<Long> sizeArr = ((JSONArray) jsonObject.get("size")).iterator();
			levelSize[0] = Math.toIntExact(sizeArr.next());
			levelSize[1] = Math.toIntExact(sizeArr.next());
			
			JSONArray posArr = (JSONArray) jsonObject.get("pos");
			JSONArray connectArr = (JSONArray) jsonObject.get("connectedLevel");
			JSONArray displayArr = (JSONArray) jsonObject.get("displayText");
			JSONArray prereqArr = (JSONArray) jsonObject.get("prereq");
			JSONArray reqSizeArr = (JSONArray) jsonObject.get("prereqSize");
			
			Iterator<JSONArray> posIterator = posArr.iterator();
			Iterator<Long> connectIterator = connectArr.iterator();
			Iterator<String> displayIterator = displayArr.iterator();
			Iterator<JSONArray> prereqIterator = prereqArr.iterator();
			Iterator<Long> reqSizeIterator = reqSizeArr.iterator();
			
			this.horizontal = (boolean) jsonObject.get("horizontal");
			this.levelPos = new ArrayList<>();
			this.levelConnected = new int[connectArr.size()];
			this.levelDisplay = new String[displayArr.size()];
			this.levelPrereq = new ArrayList[prereqArr.size()];
			this.levelReqSize =  new int[reqSizeArr.size()];
			
			int count = 0;
			while(posIterator.hasNext()) {
				Iterator<Long> pos = posIterator.next().iterator();
				levelPos.add(new Vector2(pos.next(), pos.next()));
				
				levelConnected[count] = Math.toIntExact(connectIterator.next());
				
				levelDisplay[count] = displayIterator.next();
				
				levelPrereq[count] = new ArrayList<>();
				Iterator<Long> prereq = prereqIterator.next().listIterator();
				prereq.forEachRemaining(levelPrereq[count]::add);
				
				levelReqSize[count] = Math.toIntExact(reqSizeIterator.next());
				
				count++;
            }
		}
		catch(IOException e) {}
		catch(ParseException e) {}
	}
}
