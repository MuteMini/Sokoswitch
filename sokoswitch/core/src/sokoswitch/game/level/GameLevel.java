package sokoswitch.game.level;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import sokoswitch.game.entities.Player;

public class GameLevel extends Level{
	
	private ArrayList<TiledMapTileLayer> layers;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRender;
	
	private Player player;
	
	public GameLevel(String level) {
		map = new TmxMapLoader().load(level);
		layers = new ArrayList<>();
		for(MapLayer layer : map.getLayers()) {
			layers.add((TiledMapTileLayer)layer);
		}
		mapRender = new OrthogonalTiledMapRenderer(map);
	}
	
	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
			player.moveUp();
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
			player.moveDown();
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
			player.moveRight();
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
			player.moveLeft();
		
		mapRender.setView(camera);
		mapRender.getBatch().begin();
		mapRender.renderTileLayer(layers.get(0));
		mapRender.getBatch().draw(player.getTexture(), player.getXPos(), player.getYPos());
		mapRender.getBatch().end();
	}

	@Override
	public void dispose() {
		map.dispose();
		mapRender.dispose();
	}

	@Override
	public int getWidth() {
		return layers.get(0).getWidth();
	}

	@Override
	public int getHeight() {
		return layers.get(0).getHeight();
	}

	@Override
	public Blocks locateBlockByCoordinate(int layer, int x, int y) {
		Cell cell = ((TiledMapTileLayer)(map.getLayers().get(layer))).getCell(x, y);
		System.out.print(x + " " + y + "|");
		if(cell != null) {
			TiledMapTile tile = cell.getTile();
			if(tile != null) {
				return Blocks.getBlockById(tile.getId());
			}
		}
		return Blocks.getBlockById(0);
	}
	
	public Player getPlayer() {
		return player;
	}
}
