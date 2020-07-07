package sokoswitch.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import sokoswitch.game.level.*;
import sokoswitch.game.loaders.*;

public final class GameAssetManager {
	
	public final AssetManager manager = new AssetManager();
	
	public final String levelRoadPath = "textures/levelRoad.atlas";
	public final String puzzleEntityPath = "textures/entities.atlas";
	public final String normalBlockPath = "textures/normalBlock.atlas";
	public final String lockedBlockPath = "textures/lockedBlock.atlas";
	public final String[] fontPath = {"fonts/BalsamiqSans-Regular.ttf", "fonts/Kenney-Pixel.ttf", "fonts/Nunito-Regular.ttf"};
	public final int fontArraySize = 1;
	
	private EntityAssets entityAssets;
	private NormalBlockAssets normalBlockAssets;
	private LockedBlockAssets lockedBlockAssets;
	
	private BitmapFont[] fonts;
	
	public GameAssetManager() {
		CustomTmxMapLoader loader = new CustomTmxMapLoader();
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(TiledMap.class, loader);
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(WorldData.class, new WorldDataLoader(resolver));
	}
	
	public void loadImages() {
		manager.load(levelRoadPath, TextureAtlas.class);
		manager.load(puzzleEntityPath, TextureAtlas.class);
		manager.load(normalBlockPath, TextureAtlas.class);
		manager.load(lockedBlockPath, TextureAtlas.class);
	}
	
	public void loadLevels() {
		for(LevelPath lp : LevelPath.values()) {
			if(lp.getLevelNum() <= 0)
				manager.load(lp.getFilePath(), WorldData.class);
			else
				manager.load(lp.getFilePath(), TiledMap.class);
		}
	}
	
	public void loadFonts() {
		for(int i = 0; i < fontPath.length; i++) {
			manager.load(fontPath[i], FreeTypeFontGenerator.class);
		}
	}
	
	public void initializeValues() {
		entityAssets = new EntityAssets(manager.get(puzzleEntityPath));
		normalBlockAssets = new NormalBlockAssets(manager.get(normalBlockPath));
		lockedBlockAssets = new LockedBlockAssets(manager.get(lockedBlockPath));
		
		fonts = new BitmapFont[2];
		
		FreeTypeFontGenerator balsamic = manager.get(fontPath[0]);
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
			param.size = 120;
			param.borderWidth = 10;
			param.borderColor = Color.DARK_GRAY;
			param.color = new Color(.90f, .90f, .90f, 1);
		fonts[0] = balsamic.generateFont(param);
			param.size = 90;
		fonts[1] = balsamic.generateFont(param);
	}
	
	public Sprite getSprite(int id, int pos) {
		Sprite sprite = new Sprite();
		
		if(id == 0) sprite = new Sprite(((TextureAtlas)manager.get(levelRoadPath)).findRegion("levelRoad"+pos));
		else if(id == 1) sprite = new Sprite(entityAssets.assets[pos]);
		else if(id == 2) sprite = new Sprite(normalBlockAssets.assets[pos]);
		else if(id == 3) sprite = new Sprite(lockedBlockAssets.assets[pos]);
			
		if(id == 1) {
			if(pos == 3 || pos == 4) sprite.setScale(7.5f);
			else sprite.setScale(8);
		}
		else sprite.setScale(8.1f);
		
		return sprite;
	}
	
	public BitmapFont getFont(int index) {
		return fonts[index];
	}
	
	public void dispose () {
		manager.dispose();
	}
	
	public class EntityAssets {
        public final TextureRegion[] assets;

        public EntityAssets(TextureAtlas atlas) {
        	assets = new TextureRegion[7];
        	assets[0] = atlas.findRegion("levelTile0");
        	assets[1] = atlas.findRegion("levelTile1");
        	assets[2] = atlas.findRegion("levelTile2");
        	assets[3] = atlas.findRegion("player");
        	assets[4] = atlas.findRegion("inversePlayer");
        	assets[5] = atlas.findRegion("on");
        	assets[6] = atlas.findRegion("off");
        }
    }
	
	public class NormalBlockAssets {
        public final TextureRegion[] assets;

        public NormalBlockAssets(TextureAtlas atlas) {
        	assets = new TextureRegion[47];
        	assets[0] = atlas.findRegion("block1");
        	assets[1] = atlas.findRegion("block2");
        	assets[2] = atlas.findRegion("block3");
        	assets[3] = atlas.findRegion("block4");
        	assets[4] = atlas.findRegion("block5");
        	assets[5] = atlas.findRegion("block6");
        	assets[6] = atlas.findRegion("block7");
        	assets[7] = atlas.findRegion("block8");
        	assets[8] = atlas.findRegion("block9");
        	
        	assets[9] = atlas.findRegion("block10");
        	assets[10] = atlas.findRegion("block11");
        	assets[11] = atlas.findRegion("block12");
        	assets[12] = atlas.findRegion("block13");
        	assets[13] = atlas.findRegion("block14");
        	assets[14] = atlas.findRegion("block15");
        	assets[15] = atlas.findRegion("block16");
        	assets[16] = atlas.findRegion("block17");
        	assets[17] = atlas.findRegion("block18");
        	assets[18] = atlas.findRegion("block19");
        	
        	assets[19] = atlas.findRegion("block20");
        	assets[20] = atlas.findRegion("block21");
        	assets[21] = atlas.findRegion("block22");
        	assets[22] = atlas.findRegion("block23");
        	assets[23] = atlas.findRegion("block24");
        	assets[24] = atlas.findRegion("block25");
        	assets[25] = atlas.findRegion("block26");
        	assets[26] = atlas.findRegion("block27");
        	assets[27] = atlas.findRegion("block28");
        	assets[28] = atlas.findRegion("block29");
        	
        	assets[29] = atlas.findRegion("block30");
        	assets[30] = atlas.findRegion("block31");
        	assets[31] = atlas.findRegion("block32");
        	assets[32] = atlas.findRegion("block33");
        	assets[33] = atlas.findRegion("block34");
        	assets[34] = atlas.findRegion("block35");
        	assets[35] = atlas.findRegion("block36");
        	assets[36] = atlas.findRegion("block37");
        	assets[37] = atlas.findRegion("block38");
        	assets[38] = atlas.findRegion("block39");
        	
        	assets[39] = atlas.findRegion("block40");
        	assets[40] = atlas.findRegion("block41");
        	assets[41] = atlas.findRegion("block42");
        	assets[42] = atlas.findRegion("block43");
        	assets[43] = atlas.findRegion("block44");
        	assets[44] = atlas.findRegion("block45");
        	assets[45] = atlas.findRegion("block46");
        	assets[46] = atlas.findRegion("block47");
        }
    }
	
	public class LockedBlockAssets {
        public final TextureRegion[] assets;

        public LockedBlockAssets(TextureAtlas atlas) {
        	assets = new TextureRegion[47];
        	assets[0] = atlas.findRegion("lockedBlock1");
        	assets[1] = atlas.findRegion("lockedBlock2");
        	assets[2] = atlas.findRegion("lockedBlock3");
        	assets[3] = atlas.findRegion("lockedBlock4");
        	assets[4] = atlas.findRegion("lockedBlock5");
        	assets[5] = atlas.findRegion("lockedBlock6");
        	assets[6] = atlas.findRegion("lockedBlock7");
        	assets[7] = atlas.findRegion("lockedBlock8");
        	assets[8] = atlas.findRegion("lockedBlock9");
        	
        	assets[9] = atlas.findRegion("lockedBlock10");
        	assets[10] = atlas.findRegion("lockedBlock11");
        	assets[11] = atlas.findRegion("lockedBlock12");
        	assets[12] = atlas.findRegion("lockedBlock13");
        	assets[13] = atlas.findRegion("lockedBlock14");
        	assets[14] = atlas.findRegion("lockedBlock15");
        	assets[15] = atlas.findRegion("lockedBlock16");
        	assets[16] = atlas.findRegion("lockedBlock17");
        	assets[17] = atlas.findRegion("lockedBlock18");
        	assets[18] = atlas.findRegion("lockedBlock19");
        	
        	assets[19] = atlas.findRegion("lockedBlock20");
        	assets[20] = atlas.findRegion("lockedBlock21");
        	assets[21] = atlas.findRegion("lockedBlock22");
        	assets[22] = atlas.findRegion("lockedBlock23");
        	assets[23] = atlas.findRegion("lockedBlock24");
        	assets[24] = atlas.findRegion("lockedBlock25");
        	assets[25] = atlas.findRegion("lockedBlock26");
        	assets[26] = atlas.findRegion("lockedBlock27");
        	assets[27] = atlas.findRegion("lockedBlock28");
        	assets[28] = atlas.findRegion("lockedBlock29");
        	
        	assets[29] = atlas.findRegion("lockedBlock30");
        	assets[30] = atlas.findRegion("lockedBlock31");
        	assets[31] = atlas.findRegion("lockedBlock32");
        	assets[32] = atlas.findRegion("lockedBlock33");
        	assets[33] = atlas.findRegion("lockedBlock34");
        	assets[34] = atlas.findRegion("lockedBlock35");
        	assets[35] = atlas.findRegion("lockedBlock36");
        	assets[36] = atlas.findRegion("lockedBlock37");
        	assets[37] = atlas.findRegion("lockedBlock38");
        	assets[38] = atlas.findRegion("lockedBlock39");
        	
        	assets[39] = atlas.findRegion("lockedBlock40");
        	assets[40] = atlas.findRegion("lockedBlock41");
        	assets[41] = atlas.findRegion("lockedBlock42");
        	assets[42] = atlas.findRegion("lockedBlock43");
        	assets[43] = atlas.findRegion("lockedBlock44");
        	assets[44] = atlas.findRegion("lockedBlock45");
        	assets[45] = atlas.findRegion("lockedBlock46");
        	assets[46] = atlas.findRegion("lockedBlock47");
        }
    }
}
