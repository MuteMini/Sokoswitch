package sokoswitch.game.loaders;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class LevelData {

	public String levelName;
	public int[][] playerPos;
	public boolean[] playerType;
	public int[][] blockPos;
	public ArrayList<Integer[]> blockState;
	public TiledMap map;

	public LevelData() {
		this.levelName = "";
		this.playerPos = new int[0][2];
		this.playerType = new boolean[0];
		this.blockPos = new int[0][2];
		this.blockState = new ArrayList<>();
		this.map = new TiledMap();
	}

    public LevelData(FileHandle file, Texture texture) {
    	this();
    	setLevelData(file, texture);
    }

	@SuppressWarnings("unchecked")
	private void setLevelData(FileHandle file, Texture texture) {
		JSONParser parser = new JSONParser();
		try {
			Reader reader = file.reader();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			
			this.levelName = (String) jsonObject.get("levelName");
			int height = Math.toIntExact((long)jsonObject.get("height"));
			int width = Math.toIntExact((long)jsonObject.get("width"));
			
			JSONArray tileArr = (JSONArray) jsonObject.get("tiles");
			Iterator<Long> tiletIterator = tileArr.iterator();
			
			MapLayers layers = this.map.getLayers();
			TiledMapTileLayer layer1 = new TiledMapTileLayer(width, height, 256, 256);
			
			Cell cell1 = new Cell();
			StaticTiledMapTile spaceTile = new StaticTiledMapTile(new TextureRegion(texture));
			spaceTile.setId(1);
			cell1.setTile(spaceTile);
			
			int xPos = 0, yPos = height-1;
			while(tiletIterator.hasNext()) {
				if(xPos == width) {
					xPos = 0;
					yPos--;
				}
				switch(Math.toIntExact((long)tiletIterator.next())) {
					case 1:
					layer1.setCell(xPos, yPos, cell1);
				}
				xPos++;
			}
			
			layers.add(layer1);
			
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
						blockData[2] = Math.toIntExact((long)blockObjects.next());
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