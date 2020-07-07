package sokoswitch.game.screens;

import java.util.ArrayList;
import java.util.HashSet;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

	private boolean keysHeld;
	
	private Sprite cursorSprite;
	private int cursorX, cursorY;
	
	public WorldScreen(Sokoswitch game, int levelId, HashSet<Long> levelsSolved) {
		super();
		this.levelsSolved = levelsSolved;
		this.game = game;
		
		this.worldData = game.gam.manager.get(LevelPath.getLevelPath(levelId).getFilePath());
		
		this.keysHeld = false;
		
		this.cursorSprite = new Sprite(new Texture(Gdx.files.getFileHandle("tempCursor.png", Files.FileType.Internal)));
			cursorSprite.scale(8);
		this.cursorX = -1;
		this.cursorY = -1;
		
		this.levelTiles = new ArrayList<>();
		this.roadsHidden = new HashSet<>();
		this.roadsShown = new HashSet<>();
		this.visitable = new HashSet<>();
		
		for(int i = 0; i < worldData.getDataSize(); i++) {
			Vector2 pos = worldData.levelPos.get(i);
			if(i == 0 && cursorX == -1) {
				this.cursorX = (int)pos.x;
				this.cursorY = (int)pos.y;
			}
			levelTiles.add(new LevelTile((int)pos.x, (int)pos.y, game.gam, worldData.levelDisplay[i], worldData.levelConnected[i], worldData.levelPrereq[i], worldData.levelReqSize[i]));
			visitable.add(pos);
		}
		/*for(int i = 1; i < levelTiles.size(); i++) {
			LevelTile lt = levelTiles.get(i);
			Vector2 ve1 = lt.getPosition();
			for(Long l : lt.getPrereq()) {
				Vector2 ve2 = levelTiles.get(Math.toIntExact(l)).getPosition();
				if(worldData.horizontal) {
					
				}
				else {
					
				}
			}
		}*/
		roadsHidden.add(new LevelRoad(1, 1, game.gam));
		roadsHidden.add(new LevelRoad(1, 1, game.gam));
		System.out.println(roadsHidden.size());
	}
	
	@Override
	public void show() {
		game.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.camera.zoom = 3;
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		/**/
		
		game.camera.position.set(cursorX*Tiles.SIZE + (Tiles.SIZE/2), cursorY*Tiles.SIZE + (Tiles.SIZE/2), 0);
		game.camera.update();
	}
	
	public void update(float delta) {
		if(!keysHeld && !keysPressed.isEmpty()) {
			switch(keysPressed.peek()) {
				case 1:
					if(cursorY < worldData.levelSize[1] && visitable.contains(new Vector2(cursorX, cursorY+1)))
						cursorY++;
					keysHeld = true;
					break;
				case 2:
					if(cursorY > 0 && visitable.contains(new Vector2(cursorX, cursorY-1)))
						cursorY--;
					keysHeld = true;
					break;
				case 3:
					if(cursorX < worldData.levelSize[0] && visitable.contains(new Vector2(cursorX+1, cursorY)))
						cursorX++;
					keysHeld = true;
					break;
				case 4:
					if(cursorX > 0 && visitable.contains(new Vector2(cursorX-1, cursorY)))
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
			keysHeld = false;
		}

		cursorSprite.setCenter(cursorX*Tiles.SIZE + (Tiles.SIZE/2), cursorY*Tiles.SIZE + (Tiles.SIZE/2));
		
		float cameraX = (Math.abs(cursorSprite.getX() - game.camera.position.x) < 10) ? cursorSprite.getX() : game.camera.position.x+(cursorSprite.getX() - game.camera.position.x)*(delta*2);
		float cameraY = (Math.abs(cursorSprite.getY() - game.camera.position.y) < 10) ? cursorSprite.getY() : game.camera.position.y+(cursorSprite.getY() - game.camera.position.y)*(delta*2);
		game.camera.position.set(cameraX, cameraY, 0);
		game.camera.update();
		
		if(Math.abs(cursorSprite.getX()-cameraX) < 64 && Math.abs(cursorSprite.getY()-cameraY) < 64) keysHeld = false;
		
		for(LevelTile lt : levelTiles) {
			lt.update(levelsSolved);
		}
		
		if(Gdx.input.justTouched()) {
			Vector3 bruh = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			game.camera.unproject(bruh);
			System.out.println(bruh);
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
				if(id <= 0) game.gsm.showWorldScreen(id);
				else game.gsm.showPuzzleScreen(id);
			}
		}
	}
}
