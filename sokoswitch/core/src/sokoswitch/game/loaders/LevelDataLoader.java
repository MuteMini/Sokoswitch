package sokoswitch.game.loaders;

import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class LevelDataLoader extends AsynchronousAssetLoader<LevelData, LevelDataLoader.LevelDataParameter>{

	LevelData levelData;
	
	public LevelDataLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, LevelDataParameter parameter) {
		this.levelData = null;
        this.levelData = new LevelData(file);
	}

	@Override
	public LevelData loadSync(AssetManager manager, String fileName, FileHandle file, LevelDataParameter parameter) {
		LevelData worldData = this.levelData;
        this.levelData = null;
        	
        return worldData;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, LevelDataParameter parameter) {
		return null;
	}
	
	static public class LevelDataParameter extends AssetLoaderParameters<LevelData> {
	}
}