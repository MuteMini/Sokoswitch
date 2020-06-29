package sokoswitch.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import sokoswitch.game.level.*;
import sokoswitch.game.worlddata.*;

public final class GameAssetManager {
	
	public final AssetManager manager = new AssetManager();
	
	public final String puzzleEntityPath = "puzzleEntity.png";
	public final String puzzleBlocksPath = "puzzleBlocks.png";
	public final String[] fontPath = {"fonts/BalsamiqSans-Regular.ttf"};
	
	private TextureRegion[][] entity;
	private TextureRegion[][] blocks;
	private FreeTypeFontGenerator[] fonts;
	
	public GameAssetManager() {
		TmxMapLoader loader = new TmxMapLoader();
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(TiledMap.class, loader);
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(WorldData.class, new WorldDataLoader(resolver));
	}
	
	public void loadImages() {
		manager.load(puzzleEntityPath, Texture.class);
		manager.load(puzzleBlocksPath, Texture.class);
	}
	
	public void loadLevels() {
		for(LevelPath lp : LevelPath.values()) {
			if(lp.getLevelNum() <= 0)
				manager.load(LevelPath.getDataPath(Math.abs(lp.getLevelNum())), WorldData.class);
			manager.load(lp.getFilePath(), TiledMap.class);
		}
	}
	
	public void loadFonts() {
		for(int i = 0; i < fontPath.length; i++) {
			manager.load(fontPath[i], FreeTypeFontGenerator.class);
		}
	}
	
	public void initializeValues() {
		entity = TextureRegion.split(manager.get(puzzleEntityPath), Tiles.SIZE, Tiles.SIZE);
		blocks = TextureRegion.split(manager.get(puzzleBlocksPath), Tiles.SIZE, Tiles.SIZE);
		fonts = new FreeTypeFontGenerator[fontPath.length];
		for(int i = 0; i < 1; i++) {
			fonts[i] = manager.get(fontPath[i]);
		}
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
	
	public FreeTypeFontGenerator getFont(int index) {
		return fonts[index];
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
