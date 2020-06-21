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
	
	/*first, stores the amount of blocks on the level.*/
	private int connectCount;
	
	/*controls which way the player will be moving*/
	private byte movement;
	/*controls the movement value*/
	private float movementOffset;
	private float offset;
	/*controls at what speed the player will be moving at (slow at first, then gets faster)*/
	private int movementSpeed;
	/*controls how much one key has been held for (3~5 holds and gets faster)*/
	private boolean movementHeld;
	private int heldCount;
	
	public GameLevel(String level) {
		
		Gdx.input.setInputProcessor(new InputAdapter() {
	        @Override 
	        public boolean keyDown (int keycode) {
	        	if(movementOffset == 0) {
		            if (keycode == Input.Keys.UP || keycode == Input.Keys.W){
		            	 movement = 0;
		            	 movementHeld = true;
		            }
		            if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S){
		            	 movement = 2;
		            	 movementHeld = true;
		            }
		            if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D){
		            	 movement = 1;
		            	 movementHeld = true;
		            }
		            if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A){
		            	 movement = 3;
		            	 movementHeld = true;
		            }
	        	}
	        	if(movementOffset == 0) {
	        		offset = 0;
	        		movementSpeed = 60;
	        		heldCount = 0;
	        		if(movement / 2 == 0)
	        			movementOffset = -Tiles.SIZE;
	    			else
	    				movementOffset = Tiles.SIZE; 
	        	}
	            return movementHeld;
	        }
	        @Override 
	        public boolean keyUp (int keycode) {
	        	if((keycode == Input.Keys.UP || keycode == Input.Keys.W)
		        	|| (keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
		            || (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
		            || (keycode == Input.Keys.LEFT || keycode == Input.Keys.A)){
	        		movementHeld = false;
	        		return true;
	        	}
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
		this.movement = -1;
		this.movementOffset = 0;
		this.offset = 1;
		
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
		if(offset == 0) {
			for(Player p : players) {
				p.move(movement);
			}
		}
		
		if(offset < 1) {
			offset += delta;
			float smoothing = (1-(1-offset)*(1-offset))*movementSpeed;
			movementOffset -= (movementOffset > 0) ? smoothing : -smoothing;
			if(movementOffset < 20 && movementOffset > -20) {
				movementOffset = 0;
				offset = 1;
			}
		}
		else {
			if(movementHeld) {
				heldCount++;
				offset = 0.01f;
				if(movement / 2 == 0)
        			movementOffset = -Tiles.SIZE;
    			else
    				movementOffset = Tiles.SIZE; 
				heldCount++;
				movementSpeed = (heldCount > 3) ? 90 : 50;
				for(Player p : players) {
					p.move(movement);
				}
			}
			else {
				movement = -1;
				movementOffset = 0;
				offset = 1;
				heldCount = 0;
			}
		}
	}

	@Override
	public void render(OrthographicCamera camera) {
		mapRender.setView(camera);
		mapRender.getBatch().begin();
		mapRender.renderTileLayer(layers.get(0));
		for(Player p : players) {
			if(movement % 2 == 0)
				mapRender.getBatch().draw(p.getTexture(), p.getXPos(), p.getYPos()+movementOffset);
			else if(movement % 2 == 1)
				mapRender.getBatch().draw(p.getTexture(), p.getXPos()+movementOffset, p.getYPos());
			else
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
		System.out.print(x + " " + y + " | ");
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
