package sokoswitch.game.level;

import java.util.*;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.*;
import sokoswitch.game.entities.blocks.*;

public class GameLevel extends Level{
	
	private GameAssetManager manager;
	
	private TiledMap map;
	private ArrayList<TiledMapTileLayer> layers;
	private OrthogonalTiledMapRenderer mapRender;
	
	/*holds the entities on the grid - players and blocks*/
	private Comparator<Player> playerComparatorTopDown, playerComparatorLeftRight;
	private ArrayList<Player> players;
	private ArrayList<BlockWrapper> pushable;
	
	private Stack<GameState> undoStack;
	
	/*controls animation offset values*/
	private float offset;
	private int offsetSpeed;
	/*how long a button has been pressed*/
	private int heldCount;
	/*if a button is being pressed*/
	private boolean keyPressed;
	
	/*controls which way the player will be moving*/
	private byte movement;
	/*controls the movement value*/
	private float movementOffset;
	/*holds if a movement button is being pressed*/
	private boolean movementHeld;
	/*controls what degree the player is being rotated by*/
	private float rotateOffset;
	private byte pastMovement;
	
	/*holds if the undo button is being pressed*/
	private boolean undoHeld;
	
	/*self explanatory, hopefully*/
	private boolean solved;
	
	public GameLevel(TiledMap map, GameAssetManager manager) {
		this.manager = manager;
		
		this.map = map;
		this.layers = new ArrayList<>();
		for(MapLayer layer : map.getLayers()) {
			layers.add((TiledMapTileLayer)layer);
		}
		this.mapRender = new OrthogonalTiledMapRenderer(map);

		this.pushable = new ArrayList<>();
		this.playerComparatorTopDown = new Comparator<Player>(){
			@Override
			public int compare(Player o1, Player o2) {
				return ((Float)o1.getPosition().y).compareTo((Float)o2.getPosition().y);
			}
		};
		this.playerComparatorLeftRight = new Comparator<Player>(){
			@Override
			public int compare(Player o1, Player o2) {
				return ((Float)o1.getPosition().x).compareTo((Float)o2.getPosition().x);
			}
		};
		this.players = new ArrayList<>();
		
		this.undoStack = new Stack<>();
		
		this.offset = 1;
		this.offsetSpeed = 50;
		this.heldCount = 0;
		this.keyPressed = false;
		
		this.movement = -1;
		this.movementOffset = 0;
		this.movementHeld = false;
		
		this.rotateOffset = 0;
		this.pastMovement = -1;
		
		this.undoHeld = false;
		
		this.solved = false;
		
		int xMax = getWidth();
		int yMax = getHeight();
		for(int y = 0; y < yMax; y++) {
			for(int x = 0; x < xMax; x++) {
				Tiles t = locateTilesByCoordinate(2,x,y);
				switch(t) {
					case PLAYER:
						Player p = new Player(x, y, players.size()+1, manager);
						p.setSpritePos();
						players.add(p);
						break;
					case INVERSE_PLAYER:
						Player ip = new InversePlayer(x, y, -(players.size()+1), manager);
						ip.setSpritePos();
						players.add(ip);
						break;
					case BLOCK_OFF:
					case BLOCK_ON:
						Block b1 = new NormalBlock(x, y, (t==Tiles.BLOCK_ON), manager);
						b1.setSpritePos();
						pushable.add(new BlockWrapper(b1));
						break;
					case LOCKED_BLOCK_OFF:
					case LOCKED_BLOCK_ON:
						Block b2 = new LockedBlock(x, y, (t==Tiles.LOCKED_BLOCK_ON), manager);
						b2.setSpritePos();
						pushable.add(new BlockWrapper(b2));
						break;
					default:
				}
			}
		}
		
		updateArrays();
		addState();
		joinAllBlocks();
	}
	
	public void takeInput(Stack<Integer> input) {	
		keyPressed = !input.isEmpty();
		if(offset == 1 && keyPressed && !solved && !undoHeld) {
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
					heldCount = 0;
					input.pop();
					interact();
					break;
				case 6:
					undoState();
					movement = -1;
					offsetSpeed = (heldCount >= 5) ? 17 : 7;
					offset = 0;
					undoHeld = true;
					break;
			}
			if(movement != -1) {
				movementHeld = true;
				offsetSpeed = (heldCount >= 10) ? 80 : 50;
				offset = 0;
				movementOffset = Tiles.SIZE; 
				movePlayers();
			}
		}
	}
	
	@Override
	public void update(float delta) {	
		if(undoHeld) {
			if(offset < 1) {
				offset += delta*offsetSpeed;
			}
			else {
				offset = 1;
				heldCount++;
				undoHeld = false;
			}
		}
		else if (movementHeld){
			if(offset < 1) {
				offset += delta*0.8;
				float smoothing = (1-(1-offset)*(1-offset)*(1-offset))*offsetSpeed;
				if(players.get(0).getRotate()) {
					rotateOffset -= (rotateOffset > 0) ? smoothing : -smoothing;
					if(Math.abs(rotateOffset) < 20) {
						rotateOffset = 0;
						offset = 1;
						heldCount++;
						movementHeld = false;	
					}
				}
				else {
					movementOffset -= smoothing;
					if(movementOffset < 20) {
						movementOffset = 0;
						offset = 1;
						heldCount++;
						movementHeld = false;
					}
				}
			}
		}
		else if (!keyPressed){
			resetAllPush();
			resetAllRotate();
			movement = -1;
			movementOffset = 0;
			offset = 1;
			heldCount = 0;
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
			
			if(p.getRotate() || movement == -1) {
				p.setRotation(rotateOffset);
				p.setSpritePos();
			}
			else
				p.setSpritePos(tempOffset);
			p.render(mapRender.getBatch());
		}
		
		for(BlockWrapper bw : pushable) {
			for(Block b : bw.getBlockArray()) {
				b.setSpritePos();
				if(b.getPushed()) {	
					b.setSpritePos(movementOffset);
				}
				b.render(mapRender.getBatch());
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
			if(tile != null)
				return Tiles.getTilesById(tile.getId());
		}
		return Tiles.getTilesById(0);
	}
	
	public Player getPlayer() {
		for(Player p : players) {
			if(p.getTag() == 1)
				return p;
		}
		return null;
	}
	
	public boolean isSolved() {
		return solved;
	}

	public boolean isWorld() {
		if(map.getProperties().get("isWorld") != null)
			return (boolean) map.getProperties().get("isWorld");
		return false;
	}
	
	private void movePlayers() {	
		resetAllPush();
		resetAllRotate();
		pastMovement = getPlayer().getFacing();
		updateArrays();

		for(Player p : players) {
			if(p.setFacing(movement)) {
				p.setRotate(true);
				p.setMobile(true);
			}
			else if(locateTilesByCoordinate(0, (int)p.interact().x, (int)p.interact().y) == Tiles.SPACE
					&& moveBlocks(players, players.indexOf(p))
					&& !collidesWithPlayer(players, players.indexOf(p))) {
				p.move(movement);
				p.setMobile(true);
			}
			else
				p.setMobile(false);
			
			System.out.println(p.getId() + " " + p.getFacing());
		}

		if(players.get(0).getRotate()) {
			rotateOffset = (pastMovement-movement)*-90;
			
			if(rotateOffset == 180) 
				rotateOffset *= -1;
			else if(rotateOffset == 270)
				rotateOffset = -90;
			else if(rotateOffset == -270)
				rotateOffset = 90;
		}
		
		joinAllBlocks();
		for(Player p : players) {
			if(p.getMobile()) {
				addState();
				break;
			}
		}
	}
	
	private boolean moveBlocks(ArrayList<Player> players, int index) {
		byte playerDirection = players.get(index).getFacing();
		int xOffset = (playerDirection % 2 == 0) ? 0 : 1;
		int yOffset = (playerDirection % 2 == 0) ? 1 : 0;
		xOffset *= (playerDirection == 3) ? -1 : 1;
		yOffset *= (playerDirection == 2) ? -1 : 1;
		Vector2 movementVector = new Vector2(xOffset, yOffset);
		
		for(int i = 0; i < pushable.size(); i++) {
			Vector2[] v = pushable.get(i).getBlockPos();
			boolean push = false;
			boolean outside = false;
			
			for(int j = 0; j < v.length; j++) {
				if(v[j].equals(players.get(index).interact()))
					push = true;
				if((locateTilesByCoordinate(0, (int)v[j].x+xOffset, (int)v[j].y+yOffset) != Tiles.SPACE)
						|| (locateTilesByCoordinate(0, (int)v[j].x, (int)v[j].y) == Tiles.EMPTY))
					outside = true;
			}
			
			if(push) {
				if(outside)
					return false;
				else {
					for(Block b : pushable.get(i).getBlockArray()) {
						for(int j = 0; j < players.size(); j++) {
							if(j != index && players.get(j).getPosition().equals(b.getPosition().add(movementVector))){
								return false;
							}
						}
					}
					for(int j = 0; j < pushable.size(); j++) {
						if(j != i) {
							if(pushable.get(i).collides(playerDirection, pushable.get(j)))
								return false;
						}
					}
					pushable.get(i).push(playerDirection);
					return true;
				}
			}
		}
		return true;
	}

	private boolean collidesWithPlayer(ArrayList<Player> players, int index) {
		Player main = players.get(index);
		for(Player p : players) {
			if(p != main 
					&& p.getPosition().equals(main.interact()) 
					&& (locateTilesByCoordinate(0, (int)p.interact().x, (int)p.interact().y) == Tiles.EMPTY || !moveBlocks(players, players.indexOf(p)))) {
				return true;
			}
		}
		return false;
	}
	
	private void interact() {
		Set<Vector2> playerInteract = new HashSet<>();
		for(int i = 0; i < players.size(); i++) {
			playerInteract.add(players.get(i).interact());
		}
		
		boolean wasSwitched = false;
		for(BlockWrapper bw : pushable) {
			for(Block b : bw.getBlockArray()) {
				if(b.switchPossible(movement) 
						&& playerInteract.contains(b.getPosition())) {
					bw.switchStates();
					wasSwitched = true;
					break;
				}
			}
		}
		if(wasSwitched)
			addState();
		this.solved = checkLevelSolved();
	}
	
	private void joinAllBlocks() {
		int size = pushable.size();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(i != j && pushable.get(i).touching(pushable.get(j))) {
					pushable.get(i).connect(pushable.get(j));
					pushable.remove(pushable.get(j));
					if(j > i)
						j--;
					else
						i--;
					size--;
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
	
	private void addState() {
		undoStack.add(new GameState(players, pushable));
	}
	
	private void undoState() {
		ArrayList<Player> players = undoStack.peek().getPlayerArray(manager);
		
		this.players = new ArrayList<>(players);
		this.pushable = undoStack.peek().getBlockArray(manager);
		if(undoStack.size() > 1)
			undoStack.pop();
	}
	
	private void updateArrays() {
		if(movement % 2 == 0) {
			Collections.sort(players, playerComparatorTopDown);
			if(movement == 0)
				Collections.reverse(players);
		}
		else {
			Collections.sort(players, playerComparatorLeftRight);
			if(movement == 1)
				Collections.reverse(players);
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
