package sokoswitch.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import sokoswitch.game.level.*;

public final class GameAssetManager {
	
	public final AssetManager manager = new AssetManager();
	
	public final String puzzleEntityPath = "puzzleEntity.png";
	public final String puzzleBlocksPath = "puzzleBlocks.png";
	
	private TextureRegion[][] entity;
	private TextureRegion[][] blocks;

	public void loadImages() {
		manager.load(puzzleEntityPath, Texture.class);
		manager.load(puzzleBlocksPath, Texture.class);
	}
	
	public void loadLevels() {
		TmxMapLoader loader = new TmxMapLoader();
		manager.setLoader(TiledMap.class, loader);
		for(LevelPath lp : LevelPath.values()) {
			manager.load(lp.getFilePath(), TiledMap.class);
		}
	}
	
	public void initializeValues() {
		entity = TextureRegion.split(manager.get(puzzleEntityPath), Tiles.SIZE, Tiles.SIZE);
		blocks = TextureRegion.split(manager.get(puzzleBlocksPath), Tiles.SIZE, Tiles.SIZE);
	}
	
	public Sprite getSprite(int id, int pos) {
		if(id == 1) {
			int x = pos/2;
			int y = pos%2;
			return new Sprite(entity[x][y]);
		}
		else if(id == 2) {
			int x = pos/4;
			int y = pos%4;
			return new Sprite(blocks[x][y]);
		}
		return null;
	}
	
	public void dispose () {
		manager.dispose();
		/*
		manager.unload(puzzleEntityPath);
		manager.unload(puzzleBlocksPath);
		for(LevelPath lp : LevelPath.values()) {
			manager.unload(lp.getFilePath());
		}*/
	}
}
