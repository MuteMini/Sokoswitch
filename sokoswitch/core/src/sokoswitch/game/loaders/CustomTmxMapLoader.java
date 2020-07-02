package sokoswitch.game.loaders;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;

public class CustomTmxMapLoader extends TmxMapLoader{
	
	public final int NUM_OF_TILES = 16;
	
	public CustomTmxMapLoader() {
		super(new InternalFileHandleResolver());
	}

	public CustomTmxMapLoader(FileHandleResolver resolver) {
		super(resolver);
	}
	
	@Override
	protected void addStaticTiles (FileHandle tmxFile, ImageResolver imageResolver, TiledMapTileSet tileSet,	Element element,
		Array<Element> tileElements, String name, int firstgid, int tilewidth, int tileheight, int spacing, int margin,
		String source, int offsetX, int offsetY, String imageSource, int imageWidth, int imageHeight, FileHandle image) {

		MapProperties props = tileSet.getProperties();
		if (image != null) {
			// One image for the whole tileSet
			TextureRegion texture = imageResolver.getImage(image.path());

			props.put("imagesource", imageSource);
			props.put("imagewidth", imageWidth);
			props.put("imageheight", imageHeight);
			props.put("tilewidth", tilewidth);
			props.put("tileheight", tileheight);
			props.put("margin", margin);
			props.put("spacing", spacing);

			int stopWidth = texture.getRegionWidth() - tilewidth;
			int stopHeight = texture.getRegionHeight() - tileheight;

			int id = firstgid;

			for (int y = margin; y <= stopHeight; y += tileheight + spacing) {
				for (int x = margin; x <= stopWidth; x += tilewidth + spacing) {
					TextureRegion tileRegion = new TextureRegion(texture, x, y, tilewidth, tileheight);
					int tileId = id++;
					addStaticTiledMapTile(tileSet, tileRegion, tileId, offsetX, offsetY);
				}
			}
			
			if(id <= NUM_OF_TILES) {
				TextureRegion tileRegion = new TextureRegion();
				while(id <= NUM_OF_TILES) {
					int tileId = id++;
					addStaticTiledMapTile(tileSet, tileRegion, tileId, offsetX, offsetY);
				}
			}
		}
		else {
			for (Element tileElement : tileElements) {
				Element imageElement = tileElement.getChildByName("image");
				if (imageElement != null) {
					imageSource = imageElement.getAttribute("source");

					if (source != null) {
						image = getRelativeFileHandle(getRelativeFileHandle(tmxFile, source), imageSource);
					} else {
						image = getRelativeFileHandle(tmxFile, imageSource);
					}
				}
				TextureRegion texture = imageResolver.getImage(image.path());
				int tileId = firstgid + tileElement.getIntAttribute("id");
				addStaticTiledMapTile(tileSet, texture, tileId, offsetX, offsetY);
			}
		}
	}
}
