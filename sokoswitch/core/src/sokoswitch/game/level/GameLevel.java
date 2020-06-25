package sokoswitch.game.level;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.entities.*;
import sokoswitch.game.entities.blocks.*;

public class GameLevel extends Level{
	
	private TiledMap map;
	private ArrayList<TiledMapTileLayer> layers;
	private OrthogonalTiledMapRenderer mapRender;
	
	/*holds the entities on the grid - players and blocks*/
	private ArrayList<Player> players;
	private ArrayList<BlockWrapper> pushable;

	/*controls animation offset values*/
	private float offset;
	
	/*controls which way the player will be moving*/
	private byte movement;
	/*controls the movement value*/
	private float movementOffset;
	/*controls at what speed the player will be moving at (slow at first, then gets faster)*/
	private int movementSpeed;
	/*controls how much one key has been held for (3~5 holds and gets faster)*/
	private boolean movementHeld;
	private int heldCount;
	/*controls what degree the player is being rotated by*/
	private int rotateAngle;
	private float rotateOffset;
	private byte pastMovement;
	
	/*self explanatory, hopefully*/
	private boolean solved;
	
	public GameLevel(String level) {
		this.map = new TmxMapLoader().load(level);
		this.layers = new ArrayList<>();
		for(MapLayer layer : map.getLayers()) {
			layers.add((TiledMapTileLayer)layer);
		}
		this.mapRender = new OrthogonalTiledMapRenderer(map);
		
		this.players = new ArrayList<>();
		this.pushable = new ArrayList<>();
		
		this.offset = 1;
		
		this.movement = -1;
		this.movementOffset = 0;
		this.movementSpeed = 50;
		this.movementHeld = false;
		this.heldCount = 0;
		
		this.rotateAngle = 0;
		this.rotateOffset = 0;
		this.pastMovement = -1;
		
		this.solved = false;
		
		int xMax = getWidth();
		int yMax = getHeight();
		for(int y = 0; y < yMax; y++) {
			for(int x = 0; x < xMax; x++) {
				Tiles t = locateTilesByCoordinate(2,x,y);
				switch(t) {
					case PLAYER:
						Player p = new Player(x, y);
						p.setSpritePos();
						players.add(p);
						break;
					case BLOCK_OFF:
					case BLOCK_ON:
						Block b1 = new NormalBlock(x, y, (t==Tiles.BLOCK_ON));
						b1.setSpritePos();
						pushable.add(new BlockWrapper(b1));
						break;
					case LOCKED_BLOCK_OFF:
					case LOCKED_BLOCK_ON:
						Block b2 = new LockedBlock(x, y, (t==Tiles.LOCKED_BLOCK_ON));
						b2.setSpritePos();
						pushable.add(new BlockWrapper(b2));
						break;
					default:
				}
			}
		}
		
		joinAllBlocks();
	}
	
	public void takeInput(Stack<Integer> input) {	
		if(input.isEmpty()) {
			movementHeld = false;
		}
		else if(!solved){
			if(movementOffset == 0){
				switch(input.peek()) {		
					case 1:
						movement = 0;
						break;
					case 2:
						movement = 2;
						break;
					case 3:
						movement = 1;
						break;
					case 4:
						movement = 3;
						break;
					case 5:
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
			offset += delta*0.8;
			float smoothing = (1-(1-offset)*(1-offset)*(1-offset))*movementSpeed;
			
			if(players.get(0).getRotate()) {
				rotateOffset -= (rotateOffset > 0) ? smoothing : -smoothing;
				if(Math.abs(rotateOffset) < 20) {
					rotateOffset = 0;
					offset = 1;
				}
			}
			else {
				movementOffset -= (movementOffset > 0) ? smoothing : -smoothing;
				if(Math.abs(movementOffset) < 20) {
					movementOffset = 0;
					offset = 1;
				}
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
				movementSpeed = (heldCount > 3) ? 90 : 50;
				if(!movePlayers()) {
					movementHeld = false;
				}
			}
			else {
				resetAllPush();
				resetAllRotate();
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

			if(p.getRotate() || movement == -1)
				p.getSprite().setRotation(rotateAngle+rotateOffset);
			else if(movement % 2 == 0)
				p.setSpritePos(0, tempOffset);
			else if(movement % 2 == 1)
				p.setSpritePos(tempOffset, 0);
			p.getSprite().draw(mapRender.getBatch());
		}
		
		for(BlockWrapper bw : pushable) {
			for(Block b : bw.getBlockArray()) {
				b.setSpritePos();
				if(b.getPushed()) {	
					if(movement % 2 == 0)
						b.setSpritePos(0, movementOffset);
					else if(movement % 2 == 1)
						b.setSpritePos(movementOffset, 0);
				}
				b.getSprite().draw(mapRender.getBatch());
			}
		}
		mapRender.getBatch().end();
	}

	@Override
	public void dispose() {
		map.dispose();
		mapRender.dispose();
		for(Player p : players) {
			p.dispose();
		}
		for(BlockWrapper bw : pushable) {
			bw.dispose();
		}
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
	
	public boolean isSolved() {
		return solved;
	}
	
	private boolean movePlayers() {
		boolean allMoved = false;
		
		resetAllPush();
		resetAllRotate();
		pastMovement = players.get(0).getFacing();
		for(Player p : players) {
			if(p.setFacing(movement)) {
				p.setRotate(true);
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
		
		if(players.get(0).getRotate()) {
			rotateAngle = movement*-90;
			rotateOffset = (pastMovement-movement)*-90;
			
			if(rotateOffset == 180) 
				rotateOffset *= -1;
			else if(rotateOffset == 270)
				rotateOffset = -90;
			else if(rotateOffset == -270)
				rotateOffset = 90;
		}
		
		joinAllBlocks();
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
				if(b.switchPossible(movement) 
						&& playerInteract.contains(b.getPosition())) {
					bw.switchStates();
					break;
				}
			}
		}
		this.solved = checkLevelSolved();
	}
	
	private void joinAllBlocks() {
		for(int i = 0; i < pushable.size()-1; i++) {
			for(int j = i+1; j < pushable.size(); j++) {
				if(pushable.get(i).touching(pushable.get(j))) {
					pushable.get(i).connect(pushable.get(j));
					pushable.remove(pushable.get(j));
					j--;
				}
			}
		}
	}
	
	private void resetAllPush() {
		for(BlockWrapper bw : pushable) {
			bw.unpush();
		}
	}
	
	private void resetAllRotate() {
		for(Player p : players) {
			p.setRotate(false);
		}
	}
	
	private boolean checkLevelSolved() {
		for(BlockWrapper bw : pushable) {
			if(!bw.getBlockStateOn()) {
				return false;
			}
		}
		return true;
	}
}
