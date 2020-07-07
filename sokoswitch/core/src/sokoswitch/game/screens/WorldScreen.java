package sokoswitch.game.screens;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import sokoswitch.game.Sokoswitch;
import sokoswitch.game.entities.*;
import sokoswitch.game.level.*;
import sokoswitch.game.loaders.WorldData;

public class WorldScreen extends PlayerScreen{

	private Sokoswitch game;
	
	private WorldData worldData;
	private ArrayList<LevelTile> levelTiles;
	private HashSet<LevelRoad> roadsHidden;
	private HashSet<LevelRoad> roadsShown;
	private HashSet<Vector2> visitable;
	private Queue<LevelRoad> roadsToShow;
	
	private int levelId;
	private boolean keysHeld;
	private float heldCount;
	
	private Sprite cursorSprite;
	private int cursorX, cursorY;
	
	private boolean animationShown;
	private float animationCount;
	
	public WorldScreen(Sokoswitch game, int levelId, HashSet<Long> levelsSolved) {
		super();
		this.levelsSolved = levelsSolved;
		this.game = game;
		
		this.worldData = game.gam.manager.get(LevelPath.getLevelPath(levelId).getFilePath());
		
		this.levelId = levelId;
		this.keysHeld = false;
		this.heldCount = 0;
		
		this.cursorSprite = new Sprite(new Texture(Gdx.files.getFileHandle("tempCursor.png", Files.FileType.Internal)));
			cursorSprite.scale(8);
		this.cursorX = -1;
		this.cursorY = -1;
		
		this.animationShown = false;
		this.animationCount = 0;
		
		this.levelTiles = new ArrayList<>();
		this.roadsHidden = new HashSet<>();
		this.roadsShown = new HashSet<>();
		this.roadsToShow = new LinkedList<>();
		this.visitable = new HashSet<>();
		
		for(int i = 0; i < worldData.getDataSize(); i++) {
			Vector2 pos = worldData.levelPos.get(i);
			if(i == 0 && cursorX == -1) {
				this.cursorX = (int)pos.x;
				this.cursorY = (int)pos.y;
			}
			LevelTile lt = new LevelTile((int)pos.x, (int)pos.y, game.gam, worldData.levelDisplay[i], worldData.levelConnected[i], worldData.levelPrereq[i], worldData.levelReqSize[i]);
			lt.update(levelsSolved);
			levelTiles.add(lt);
			visitable.add(pos);
		}
		
		for(int i = 1; i < levelTiles.size(); i++) {
			LevelTile lt1 = levelTiles.get(i);
			Vector2 ve1 = lt1.getPosition();
			for(Long l : lt1.getPrereq()) {
				int prereqPos = worldData.levelConnectedInOrder.indexOf(l);
				if(prereqPos == -1) {
					continue;
				}
				LevelTile lt2 = levelTiles.get(prereqPos);
				Vector2 ve2 = lt2.getPosition();
				
				HashSet<LevelRoad> tempSet;
				boolean beingShown = false;
				
				if((lt1.isShown() && lt2.isSolved()) || (lt1.isShown() && l <= 0)) tempSet = roadsShown;
				else tempSet = roadsHidden;
				
				if(tempSet == roadsShown) beingShown = true; 
				
				if(worldData.horizontal) {
					tempSet.add(new LevelRoad((int)ve2.x+1, (int)ve2.y, game.gam));
					tempSet.add(new LevelRoad((int)ve1.x-1, (int)ve1.y, game.gam));
					if(beingShown) {
						visitable.add(new Vector2(ve2.x+1, ve2.y));
						visitable.add(new Vector2(ve1.x-1, ve1.y));
					}
					for(int j = (int)Math.min(ve1.y, ve2.y); j <= (int)Math.max(ve1.y, ve2.y); j++) {
						tempSet.add(new LevelRoad((int)ve1.x-2, j, game.gam));
						if(beingShown) visitable.add(new Vector2(ve1.x-2, j));
					}
				}
				else {
					tempSet.add(new LevelRoad((int)ve2.x, (int)ve2.y+1, game.gam));
					tempSet.add(new LevelRoad((int)ve1.x, (int)ve1.y-1, game.gam));
					if(beingShown) {
						visitable.add(new Vector2(ve2.x, ve2.y+1));
						visitable.add(new Vector2(ve1.x, ve1.y-1));
					}
					for(int j = (int)Math.min(ve1.x, ve2.x); j <= (int)Math.max(ve1.x, ve2.x); j++) {
						tempSet.add(new LevelRoad(j, (int)ve1.y-1, game.gam));
						if(beingShown) visitable.add(new Vector2(j, ve1.y-1));
					}
				}
			}
		}
	}
	
	@Override
	public void show() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.camera.zoom = 3.5f;
		game.camera.position.set(cursorX*Tiles.SIZE + (Tiles.SIZE/2), cursorY*Tiles.SIZE + (Tiles.SIZE/2), 0);
		game.camera.update();
		
		for(LevelTile lt : levelTiles) {
			lt.update(levelsSolved);
		}
		
		for(int i = 1; i < levelTiles.size(); i++) {
			LevelTile lt1 = levelTiles.get(i);
			Vector2 ve1 = lt1.getPosition();
			for(Long l : lt1.getPrereq()) {
				int prereqPos = worldData.levelConnectedInOrder.indexOf(l);
				if(prereqPos == -1) {
					continue;
				}
				LevelTile lt2 = levelTiles.get(prereqPos);
				Vector2 ve2 = lt2.getPosition();
				LevelRoad temp;
				
				if((lt1.isShown() && lt2.isSolved()) || (lt1.isShown() && l < 0)) {
					if(worldData.horizontal) {
						temp = new LevelRoad((int)ve2.x+1, (int)ve2.y, game.gam);
						if(roadsHidden.remove(temp)) roadsToShow.add(temp);
						
						if(ve1.y >= ve2.y) {
							for(int j = (int)ve2.y; j <= (int)ve1.y; j++) {
								temp = new LevelRoad((int)ve1.x-2, j, game.gam);
								if(roadsHidden.remove(temp)) roadsToShow.add(temp);
							}
						}
						else {
							for(int j = (int)ve2.y; j >= (int)ve1.y; j--) {
								temp = new LevelRoad((int)ve1.x-2, j, game.gam);
								if(roadsHidden.remove(temp)) roadsToShow.add(temp);
							}
						}
						
						temp = new LevelRoad((int)ve1.x-1, (int)ve1.y, game.gam);
						if(roadsHidden.remove(temp)) roadsToShow.add(temp);
					}
					else {
						temp = new LevelRoad((int)ve2.x, (int)ve2.y+1, game.gam);
						if(roadsHidden.remove(temp)) roadsToShow.add(temp);
						
						if(ve1.x >= ve2.x) {
							for(int j = (int)ve2.x; j <= (int)ve1.x; j++) {
								temp = new LevelRoad(j, (int)ve1.y-1, game.gam);
								if(roadsHidden.remove(temp)) roadsToShow.add(temp);
							}
						}
						else {
							for(int j = (int)ve2.x; j >= (int)ve1.x; j--) {
								temp = new LevelRoad(j, (int)ve1.y-1, game.gam);
								if(roadsHidden.remove(temp)) roadsToShow.add(temp);
							}
						}
						
						temp = new LevelRoad((int)ve1.x, (int)ve1.y-1, game.gam);
						if(roadsHidden.remove(temp)) roadsToShow.add(temp);
					}
				}
			}
		}
		if(!roadsToShow.isEmpty()) {
			animationShown = true;
		}
		
		boolean allSolved = true;
		for(LevelTile lt : levelTiles) {
			if(lt.getConnectedLevel() != 0 && !lt.isSolved())
				allSolved = false;
		}
		if(allSolved) {
			levelsSolved.add((long)levelId);
		}
	}
	
	public void update(float delta) {
		if(!keysHeld && !keysPressed.isEmpty()) {
			switch(keysPressed.peek()) {
				case 1:
					if(visitable.contains(new Vector2(cursorX, cursorY+1)))
						cursorY++;
					keysHeld = true;
					break;
				case 2:
					if(visitable.contains(new Vector2(cursorX, cursorY-1)))
						cursorY--;
					keysHeld = true;
					break;
				case 3:
					if(visitable.contains(new Vector2(cursorX+1, cursorY)))
						cursorX++;
					keysHeld = true;
					break;
				case 4:
					if(visitable.contains(new Vector2(cursorX-1, cursorY)))
						cursorX--;
					keysHeld = true;
					break;
				case 5:
					checkInput();
					keysPressed.pop();
					break;
			}
		}
		else if(keysPressed.isEmpty()) {
			heldCount = 0;
			keysHeld = false;
		}

		cursorSprite.setCenter(cursorX*Tiles.SIZE + (Tiles.SIZE/2), cursorY*Tiles.SIZE + (Tiles.SIZE/2));
		
		float cameraX = (Math.abs(cursorSprite.getX() - game.camera.position.x) < 10) ? cursorSprite.getX() : game.camera.position.x+(cursorSprite.getX() - game.camera.position.x)*(delta*2);
		float cameraY = (Math.abs(cursorSprite.getY() - game.camera.position.y) < 10) ? cursorSprite.getY() : game.camera.position.y+(cursorSprite.getY() - game.camera.position.y)*(delta*2);
		game.camera.position.set(cameraX, cameraY, 0);
		game.camera.update();
		
		if(keysHeld) {
			heldCount += delta;
			if(heldCount > 0.4) {
				heldCount = 0;
				keysHeld = false;
			}
		}
		
		if(animationShown) {
			animationCount+=delta;
			if(animationCount > 0.6) {
				LevelRoad lr = roadsToShow.poll();
				roadsShown.add(lr);
				visitable.add(lr.getPosition());
				animationCount = 0;
				if(roadsToShow.isEmpty()) animationShown = false;
			}
		}
		
		for(LevelTile lt : levelTiles) {
			lt.update(levelsSolved);
		}
		for(LevelRoad lr : roadsShown) {
			lr.updateSprite(visitable);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.50f, .50f, .50f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		for(LevelTile lt : levelTiles) {
			lt.render(game.batch);
		}
		for(LevelRoad lr : roadsShown) {
			lr.render(game.batch);
		}
		
		cursorSprite.draw(game.batch);
	}

	private void checkInput() {
		for(LevelTile lt : levelTiles) {
			if((int)lt.getPosition().x == cursorX && (int)lt.getPosition().y == cursorY){
				int id = lt.getConnectedLevel();
				
				if(id == 0) game.gsm.pop();
				else if(id < 0) game.gsm.showWorldScreen(id);
				else game.gsm.showPuzzleScreen(id);
			}
		}
	}
}
