package sokoswitch.game.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class WorldDataLoader extends AsynchronousAssetLoader<WorldData, WorldDataLoader.WorldDataParameter>{

	WorldData worldData;
	
	public WorldDataLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, WorldDataParameter parameter) {
		this.worldData = null;
        this.worldData = new WorldData(file);
	}

	@Override
	public WorldData loadSync(AssetManager manager, String fileName, FileHandle file, WorldDataParameter parameter) {
		WorldData worldData = this.worldData;
        this.worldData = null;
        	
        return worldData;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, WorldDataParameter parameter) {
		return null;
	}
	
	static public class WorldDataParameter extends AssetLoaderParameters<WorldData> {
	}
}
