package sokoswitch.game.level;

import java.util.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.Sokoswitch;
import sokoswitch.game.entities.*;
import sokoswitch.game.entities.blocks.*;
import sokoswitch.game.entities.blocks.abstracts.Block;
import sokoswitch.game.loaders.LevelData;

public class GameLevel extends Level{
	
	private GameAssetManager gam;
	
	@SuppressWarnings("unused")
	private int levelId;
	private TiledMap map;
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer mapRender;
	
	/*holds the entities on the grid - players and blocks*/
	private ArrayList<Player> players;
	private ArrayList<BlockWrapper> pushable;
	
	private Stack<GameState> undoStack;
	
	/*holds level name*/
	private String levelName;
	
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
	
	public GameLevel(int levelId, Sokoswitch game, HashSet<Long> levelsSolved) {	
		this.gam = game.gam;
		
		this.levelId = levelId;
		
		this.pushable = new ArrayList<>();
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
		
		LevelData levelData = gam.manager.get(LevelPath.getLevelPath(levelId).getFilePath());
		this.map = levelData.map;
		this.layer = (TiledMapTileLayer)this.map.getLayers().get(0);
		this.mapRender = new OrthogonalTiledMapRenderer(this.map, game.batch);
		
		this.levelName = levelData.levelName;
		
		for(int i = 0; i < levelData.playerPos.length; i++) {
			Player p;
			if(!levelData.playerType[i])
				p = new Player(levelData.playerPos[i][0], levelData.playerPos[i][1], 0, gam);
			else
				p = new InversePlayer(levelData.playerPos[i][0], levelData.playerPos[i][1], 2, gam);
			p.setSpritePos();
			players.add(p);
		}
		
		for(int i = 0; i < levelData.blockPos.length; i++) {
			Block b;
			int blockOnState = levelData.blockState.get(i)[1];
			switch(levelData.blockState.get(i)[0]) {
				case 1:
					b = new NormalBlock(levelData.blockPos[i][0], levelData.blockPos[i][1], (blockOnState==1), gam);
					break;
				case 2:
					b = new LockedBlock(levelData.blockPos[i][0], levelData.blockPos[i][1], (blockOnState==1), gam);
					break;
				case 3:
					b = new LimiterBlock(levelData.blockPos[i][0], levelData.blockPos[i][1], (blockOnState==1), levelData.blockState.get(i)[2], gam);
					break;
				case 4:
					b = new LockedLimiterBlock(levelData.blockPos[i][0], levelData.blockPos[i][1], (blockOnState==1), levelData.blockState.get(i)[2], gam);
					break;
				default:
					b = null;
					break;
			}
			b.setSpritePos();
			pushable.add(new BlockWrapper(b));
		}
		
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
				moveEntities();
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
			resetBlockValue();
			resetPlayerValue();
			movement = -1;
			movementOffset = 0;
			offset = 1;
			heldCount = 0;
		}
	}

	@Override
	public void render(OrthographicCamera camera) {
		mapRender.setView(camera);
		mapRender.renderTileLayer(layer);

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
	}

	@Override
	public void dispose() {
		map.dispose();
		mapRender.dispose();
	}

	@Override
	public int getWidth() {
		return layer.getWidth();
	}

	@Override
	public int getHeight() {
		return layer.getHeight();
	}

	public String getLevelName() {
		return levelName;
	}
	
	@Override
	public Tiles locateTilesByCoordinate(int x, int y) {
		Cell cell = layer.getCell(x, y);
		if(cell != null) {
			TiledMapTile tile = cell.getTile();
			if(tile != null)
				return Tiles.getTilesById(tile.getId());
		}
		return Tiles.getTilesById(0);
	}
	
	public Player getPlayer() {
		return players.get(0);
	}
	
	public boolean isSolved() {
		return solved;
	}

	private void moveEntities() {	
		resetBlockValue();
		resetPlayerValue();
		pastMovement = getPlayer().getFacing();

		boolean notMobile = true;
		ArrayList<Vector2> playerV = new ArrayList<>(players.size());
		Vector2[][] blockWrapperV = new Vector2[pushable.size()][];
		
		for(Player p : players) {
			if(p.setFacing(movement)) {
				p.setRotate(true);
				p.setMobile(true);
				notMobile = false;
			}
			playerV.add(locateTilesByCoordinate((int)p.interact().x, (int)p.interact().y) == Tiles.SPACE ? p.interact() : p.getPosition());
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
		else {
			boolean complete = false;
			boolean[] updateP = new boolean[players.size()];
			boolean[] updateB = new boolean[pushable.size()];
			int[] blockPlayerPusher = new int[pushable.size()];
			
			Arrays.fill(updateP, true);
			Arrays.fill(blockPlayerPusher, -1);
			
			for(int i = 0; i < blockWrapperV.length; i++) {
				blockWrapperV[i] = pushable.get(i).getBlockPos();
			}
			
			while(!complete) {
				boolean allUpdated = true;
				for(int i = 0; i < playerV.size(); i++) {
					int first = playerV.indexOf(playerV.get(i));
					int last = playerV.lastIndexOf(playerV.get(i));
					BlockWrapper blockPushed = moveBlocks(playerV.get(i));
					
					if(players.get(i) instanceof InversePlayer) {
						boolean updateInverse = true;
						for(int j = 0; j < playerV.size(); j++) {
							if(i != j && playerV.get(j).equals(players.get(i).getPosition())) {
								updateInverse = false;
								updateP[j] = false;
								playerV.set(j, players.get(j).getPosition());
							}
						}
						if(!updateInverse) {
							updateP[i] = false;
							allUpdated = false;
							playerV.set(i, players.get(i).getPosition());
						}
					}
					
					if(first != last) {
						allUpdated = false;
						for(int j = 0; j < playerV.size(); j++) {
							if(playerV.get(j).equals(playerV.get(i))) {
								updateP[j] = false;
								playerV.set(j, players.get(j).getPosition());
							}
						}
					}
					
					if(blockPushed != null) {
						int xOffset = (players.get(i).getFacing() % 2 == 0) ? 0 : 1;
						int yOffset = (players.get(i).getFacing() % 2 == 0) ? 1 : 0;
						xOffset *= (players.get(i).getFacing() == 3) ? -1 : 1;
						yOffset *= (players.get(i).getFacing() == 2) ? -1 : 1;
						Vector2 movementVector = new Vector2(xOffset, yOffset);
						Vector2[] blockV = blockPushed.getBlockPos();
						boolean[] playerPosOverlaps = new boolean[players.size()];
						
						boolean update = true;	
						for(Vector2 vect : blockV) {
							if(locateTilesByCoordinate((int)vect.x, (int)vect.y) != Tiles.SPACE 
									|| locateTilesByCoordinate((int)vect.x+xOffset, (int)vect.y+yOffset) == Tiles.EMPTY) {
								update = false;
								allUpdated = false;
								break;
							}
							for(int j = 0; j < players.size(); j++) {
								if(j != i && vect.equals(players.get(j).interact())) {
									playerPosOverlaps[j] = true;
								}
							}
						}
					
						if(update) {
							for(int j = 0; j < blockV.length; j++) {
								blockV[j].add(movementVector);
							}
							for(Vector2 vect : blockV) {
								if(playerV.contains(vect)) {
									update = false;
									allUpdated = false;
								}
								for(int j = 0; j < players.size(); j++) {
									if(vect.equals(players.get(j).getPosition())) {
										if(playerPosOverlaps[j] && players.get(i).getFacing() != players.get(j).getFacing()) {
											updateP[j] = false;
											playerV.set(j, players.get(j).getPosition());
											update = false;
											allUpdated = false;
										}
									}
								}
							}
						}

						if(update) {
							for(int j = 0; j < pushable.size(); j++) {
								if(blockPushed != pushable.get(j) && blockPushed.collides(players.get(i).getFacing(), blockWrapperV[j])) {
									update = false;
									allUpdated = false;
									break;
								}
							}
						}
						
						updateB[pushable.indexOf(blockPushed)] = update;
						updateP[i] = update;	
						if(update) {
							blockPlayerPusher[pushable.indexOf(blockPushed)] = i;
							blockWrapperV[pushable.indexOf(blockPushed)] = blockV;
						}
						else {
							playerV.set(i, players.get(i).getPosition());
						}
					}
					//System.out.println(i+" "+updateP[i]+" "+updateB[i]+"|"+players.get(i).getPosition());
				}
				complete = allUpdated;
			}
			
			for(int i = 0; i < updateP.length; i++) {
				if(updateP[i] && playerV.get(i).equals(players.get(i).interact())) {
					players.get(i).move(movement);
					players.get(i).setMobile(true);
					notMobile = false;
				}
			}
			for(int i = 0; i < updateB.length; i++) {
				if(updateB[i]) {
					pushable.get(i).push(players.get(blockPlayerPusher[i]).getFacing());
				}
			}
		}
		
		joinAllBlocks();
		if(!notMobile) {
			addState();
		}
	}
	
	private BlockWrapper moveBlocks(Vector2 pVector) {
		for(int i = 0; i < pushable.size(); i++) {
			Vector2[] v = pushable.get(i).getBlockPos();
			boolean push = false;
			
			for(int j = 0; j < v.length; j++) {
				if(v[j].equals(pVector)) {
					push = true;
					break;
				}
			}
			if(push) {
				return pushable.get(i);
			}
		}
		return null;
	}

	private void interact() {
		List<Vector2> playerInteract = new ArrayList<>();
		for(int i = 0; i < players.size(); i++) {
			playerInteract.add(players.get(i).interact());
		}
		
		for(BlockWrapper bw : pushable) {
			for(Block b : bw.getBlockArray()) {
				while(b.switchPossibleDirect(movement) 
						&& playerInteract.contains(b.getPosition())) {
					bw.switchStates(b.getPosition());
					playerInteract.remove(b.getPosition());
				}
			}
		}
		
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
		updateBlockSprites();
	}
	
	private void resetBlockValue() {
		for(BlockWrapper bw : pushable) {
			bw.unpush();
		}
	}
	
	private void updateBlockSprites() {
		for(BlockWrapper bw : pushable) {
			bw.updateSprites();
		}
	}
	
	private void resetPlayerValue() {
		for(Player p : players) {
			p.setRotate(false);
			p.setMobile(false);
		}
	}
	
	private void addState() {
		undoStack.add(new GameState(players, pushable));
		updateBlockSprites();
	}
	
	private void undoState() {
		if(undoStack.size() > 1)
			undoStack.pop();
		ArrayList<Player> players = undoStack.peek().getPlayerArray(gam);
		this.players = new ArrayList<>(players);
		this.pushable = undoStack.peek().getBlockArray(gam);
		joinAllBlocks();
	}
	
	private boolean checkLevelSolved() {
		for(BlockWrapper bw : pushable) {
			if(!bw.getSolved()) {
				return false;
			}
		}
		return true;
	}
}
