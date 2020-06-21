package sokoswitch.game.level;

import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import sokoswitch.game.entities.*;

public class GameLevel extends Level{
	
	private ArrayList<TiledMapTileLayer> layers;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRender;
	
	private ArrayList<Player> players;
	private ArrayList<Entity> pushable;
	
	private int connectCount;
	
	/*controls which way the player will be moving*/
	private int movement;
	/*controls which way the player will be moving*/
	private float movementOffset;
	/*controls at what speed the player will be moving at (slow at first, then gets faster)*/
	private int movementSpeed;
	/*controls how much one key has been held for (3~5 holds and gets faster)*/
	private int movementHeld;
	
	public GameLevel(String level) {
		
		Gdx.input.setInputProcessor(new InputAdapter() {
	        @Override 
	        public boolean keyUp (int keycode) {
	            if (keycode == Input.Keys.UP || keycode == Input.Keys.W){
	            	 movement = 0;
	            	 movementOffset = Tiles.SIZE;
	            	 return true;
	            }
	            if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
	            	 movement = 2;
	            	 movementOffset = Tiles.SIZE;
	            	 return true;
	            }
	            if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
	            	 movement = 1;
	            	 movementOffset = Tiles.SIZE;
	            	 return true;
	            }
	            if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
	            	 movement = 3;
	            	 movementOffset = Tiles.SIZE;
	            	 return true;
	            }
	            movement = -1;
	            movementOffset = 0;
	            return false;
	        }
	    });
		
		this.map = new TmxMapLoader().load(level);
		this.layers = new ArrayList<>();
		for(MapLayer layer : map.getLayers()) {
			layers.add((TiledMapTileLayer)layer);
		}
		this.mapRender = new OrthogonalTiledMapRenderer(map);
		
		this.players = new ArrayList<>();
		this.pushable = new ArrayList<>();
		
		this.connectCount = 1;
		
		int xMax = getWidth();
		int yMax = getHeight();
		
		for(int y = 0; y < yMax; y++) {
			for(int x = 0; x < xMax; x++) {
				switch(locateTilesByCoordinate(2,x,y)) {
					case PLAYER:
						players.add(new Player(x, y, xMax, yMax));
						break;
					case BLOCK_OFF:
						pushable.add(new NormalBlock(x, y, xMax, yMax, false, connectCount));
						connectCount++;
						break;
					case BLOCK_ON:
						pushable.add(new NormalBlock(x, y, xMax, yMax, true, connectCount));
						connectCount++;
						break;
					default:
						break;
				}
			}
		}
	}
	
	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(OrthographicCamera camera) {
		mapRender.setView(camera);
		mapRender.getBatch().begin();
		mapRender.renderTileLayer(layers.get(0));
		for(Player p : players) {
			if(movement / 2 == 0)
				mapRender.getBatch().draw(p.getTexture(), p.getXPos(), p.getYPos());
			else if(movement / 2 == 1)
				mapRender.getBatch().draw(p.getTexture(), p.getXPos(), p.getYPos());
		}
		for(Entity b : pushable) {
			mapRender.getBatch().draw(b.getTexture(), b.getXPos(), b.getYPos());
		}
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
	public Tiles locateTilesByCoordinate(int layer, int x, int y) {
		Cell cell = ((TiledMapTileLayer)(map.getLayers().get(layer))).getCell(x, y);
		System.out.print(x + " " + y + "|");
		if(cell != null) {
			TiledMapTile tile = cell.getTile();
			if(tile != null) {
				return Tiles.getTilesById(tile.getId());
			}
		}
		return Tiles.getTilesById(0);
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
	}
}
