package sokoswitch.game.level;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.entities.*;

public class GameLevel extends Level{
	
	private TiledMap map;
	private ArrayList<TiledMapTileLayer> layers;
	private OrthogonalTiledMapRenderer mapRender;
	
	/*holds the entities on the grid - players and blocks*/
	private ArrayList<Player> players;
	private ArrayList<BlockWrapper> pushable;

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
		this.map = new TmxMapLoader().load(level);
		this.layers = new ArrayList<>();
		for(MapLayer layer : map.getLayers()) {
			layers.add((TiledMapTileLayer)layer);
		}
		this.mapRender = new OrthogonalTiledMapRenderer(map);
		
		int xMax = getWidth();
		int yMax = getHeight();
		
		this.players = new ArrayList<>();
		this.pushable = new ArrayList<>();
		
		this.movement = -1;
		this.movementOffset = 0;
		this.movementSpeed = 50;
		this.offset = 1;
		
		for(int y = 0; y < yMax; y++) {
			for(int x = 0; x < xMax; x++) {
				Tiles t = locateTilesByCoordinate(2,x,y);
				switch(t) {
					case PLAYER:
						players.add(new Player(x, y, xMax, yMax));
						break;
					case BLOCK_OFF:
					case BLOCK_ON:
						pushable.add(new BlockWrapper(new NormalBlock(x, y, xMax, yMax, (t==Tiles.BLOCK_ON))));
						break;
					default:
				}
			}
		}
	}
	
	public void takeInput(Stack<Integer> input) {	
		if(input.isEmpty()) {
			movementHeld = false;
		}
		else {
			if(movementOffset == 0){
				switch(input.peek()) {		
					case Input.Keys.UP:
						movement = 0;
						break;
					case Input.Keys.DOWN:
						movement = 2;
						break;
					case Input.Keys.RIGHT:
						movement = 1;
						break;
					case Input.Keys.LEFT:
						movement = 3;
						break;
					case Input.Keys.SPACE:
						movement = -1;
						input.pop();
						interact();
						break;
				}
				if(movement != -1) {
					movementHeld = true;
					offset = 0;
					if(movement / 2 == 0)
	        			movementOffset = -Tiles.SIZE;
	    			else
	    				movementOffset = Tiles.SIZE; 
				}
			}
		}
	}
	
	@Override
	public void update(float delta) {
		if(offset == 0 && !movePlayers()) {
			movementHeld = false;
		}
		
		if(offset < 1) {
			offset += delta;
			float smoothing = (1-(1-offset)*(1-offset)*(1-offset))*movementSpeed;
			movementOffset -= (movementOffset > 0) ? smoothing : -smoothing;
			if(Math.abs(movementOffset) < 20) {
				movementOffset = 0;
				offset = 1;
			}
		}
		else {
			resetAllPush();
			if(movementHeld) {
				heldCount++;
				offset = 0.01f;
				if(movement / 2 == 0)
        			movementOffset = -Tiles.SIZE;
    			else
    				movementOffset = Tiles.SIZE; 
				movementSpeed = (heldCount > 3) ? 90 : 50;
				if(!movePlayers()) {
					movementHeld = false;
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
			float tempOffset = movementOffset;
			if(!p.getMobile())
				tempOffset /= 6;
			
			if(movement % 2 == 0)
				mapRender.getBatch().draw(p.getSprite(), p.getXPos(), p.getYPos()+tempOffset);
			else if(movement % 2 == 1)
				mapRender.getBatch().draw(p.getSprite(), p.getXPos()+tempOffset, p.getYPos());
			else
				mapRender.getBatch().draw(p.getSprite(), p.getXPos(), p.getYPos());
		}
		
		for(BlockWrapper bw : pushable) {
			for(Block b : bw.getBlockArray()) {
				if(b.getPushed()) {		
					if(movement % 2 == 0)
						mapRender.getBatch().draw(b.getSprite(), b.getXPos(), b.getYPos()+movementOffset);
					else if(movement % 2 == 1)
						mapRender.getBatch().draw(b.getSprite(), b.getXPos()+movementOffset, b.getYPos());
				}
				else {
					mapRender.getBatch().draw(b.getSprite(), b.getXPos(), b.getYPos());
				}
			}
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
		Cell cell = layers.get(layer).getCell(x, y);
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
	
	private boolean movePlayers() {
		boolean allMoved = false;
		
		resetAllPush();
		
		for(Player p : players) {
			if(p.setFacing(movement)) {
				p.setMobile(false);
			}
			else if(locateTilesByCoordinate(0, (int)p.interact().x, (int)p.interact().y) == Tiles.SPACE
					&& moveBlocks(p.interact())) {
				p.move(movement);
				p.setMobile(true);
				allMoved = true;
			}
			else {
				p.setMobile(false);
			}
		}
		return allMoved;
	}
	
	private boolean moveBlocks(Vector2 playerPos) {
		int xOffset = (movement % 2 == 0) ? 0 : 1;
		int yOffset = (movement % 2 == 0) ? 1 : 0;
		xOffset *= (movement == 3) ? -1 : 1;
		yOffset *= (movement == 2) ? -1 : 1;
		
		for(int i = 0; i < pushable.size(); i++) {
			Vector2[] v = pushable.get(i).getBlockPos();
			boolean push = false;
			boolean outside = false;
			
			for(Vector2 vect : v) {
				if(vect.equals(playerPos))
					push = true;
				if(locateTilesByCoordinate(0, (int)vect.x+xOffset, (int)vect.y+yOffset) != Tiles.SPACE)
					outside = true;
			}
			
			if(push && !outside) {
				for(int j = 0; j < pushable.size(); j++) {
					if(j != i) {
						if(pushable.get(i).collides(movement, pushable.get(j)))
							return false;
					}
				}
				pushable.get(i).push(movement);
				return true;
			}
			else if(push && outside) {
				return false;
			}
		}
		return true;
	}
	
	private void interact() {
		Set<Vector2> playerInteract = new HashSet<>();
		for(int i = 0; i < players.size(); i++) {
			playerInteract.add(players.get(i).interact());
		}
		for(BlockWrapper bw : pushable) {
			for(Block b : bw.getBlockArray()) {
				if(b instanceof Switchable 
						&& ((Switchable)b).switchPossible(movement) 
						&& playerInteract.contains(b.getPosition())) {
					bw.switchStates();
					break;
				}
			}
		}
	}
	
	private void resetAllPush() {
		for(BlockWrapper bw : pushable) {
			bw.unpush();
		}
	}
}
