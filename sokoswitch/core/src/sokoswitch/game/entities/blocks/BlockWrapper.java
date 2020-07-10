package sokoswitch.game.entities.blocks;

import java.util.*;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.entities.blocks.abstracts.Block;

public class BlockWrapper{
	
	protected ArrayList<Block> blocksJoined;
	
	public BlockWrapper(Block b) {
		this();
		addBlock(b);
	}
	
	public BlockWrapper() {
		this.blocksJoined = new ArrayList<>();
	}

	public void addBlock(Block b) {
		blocksJoined.add(b);
	}
	
	public void push(int direction) {
		for(Block b : blocksJoined) {
			b.setFacing((byte)direction);
			b.move(direction);
		}
		updateSprites();
	}
	
	public void unpush() {
		for(Block b : blocksJoined) {
			b.setPushed(false);
		}
	}
	
	public boolean collides(int direction, Vector2[] newBv) {
		Vector2[] thisBv = this.getBlockPosAfterPush(direction);
		
		for(Vector2 v1 : newBv) {
			for(Vector2 v2 : thisBv) {
				if(v1.equals(v2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean touching(BlockWrapper newB) {
		Vector2[] newBv = newB.getBlockPos();
		Vector2[] thisBv = this.getBlockPos();
		
		for(Vector2 v1 : newBv) {
			for(Vector2 v2 : thisBv) {
				if(v1.dst(v2) == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void connect(BlockWrapper newB) {
		this.blocksJoined.addAll(newB.blocksJoined);
	}
	
	@SuppressWarnings("unchecked")
	public BlockWrapper[] disconnect() {
		Vector2[] thisBv = this.getBlockPos();
		ArrayList<Integer>[] conections = new ArrayList[thisBv.length];
		
		for(int i = 0; i < thisBv.length; i++) {
			conections[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < thisBv.length-1; i++) {
			for(int j = i+1; j < thisBv.length; j++) {
				if(thisBv[i].dst(thisBv[j]) == 1) {
					conections[i].add(j);
					conections[j].add(i);
				}
			}
		}
		
		ArrayList<BlockWrapper> tempBW = new ArrayList<>();
		boolean allVisited = false;
		boolean[] visited = new boolean[thisBv.length];
		
		while(!allVisited) {
			int vertex = -1;
			for(int i = 0; i < visited.length; i++) {
				if(!visited[i]) {
					vertex = i;
					break;
				}
			}
			
			if(vertex != -1) {
				allVisited = true;
			}
			else {
				Stack<Integer> s = new Stack<>();
				s.push(vertex);
				BlockWrapper currBW = new BlockWrapper(blocksJoined.get(vertex));
				while(!s.isEmpty()) {
					int pos = s.pop();
					for(int num : conections[pos]) {
						if(!visited[num]) {
							visited[num] = true;
							s.push(num);
							currBW.addBlock(blocksJoined.get(num));
						}
					}
				}
				tempBW.add(currBW);
			}
		}
		
		BlockWrapper[] newBW = new BlockWrapper[tempBW.size()];
		newBW = tempBW.toArray(newBW);
		return newBW;
	}
	
	public void switchStates() {
		for(Block b : blocksJoined) {
			b.switchState();
		}
	}
	
	public void updateSprites() {
		for(Block b : blocksJoined) {
			b.resetBlocksTouched();
		}
		
		Vector2[] blockPos = getBlockPos();
		for(int i = 0; i < blocksJoined.size(); i++) {
			for(int j = 0; j < blocksJoined.size(); j++) {
				if(i != j && blockPos[i].dst(blockPos[j]) <= Math.sqrt(2)+0.1) {
					float x = blockPos[i].x - blockPos[j].x;
					float y = blockPos[i].y - blockPos[j].y;
					if(x < 0) {
						if(y < 0) {
							blocksJoined.get(i).blocksTouching(2);
							blocksJoined.get(j).blocksTouching(6);
						}
						else if(y > 0) {
							blocksJoined.get(i).blocksTouching(4);
							blocksJoined.get(j).blocksTouching(0);
						}
						else {
							blocksJoined.get(i).blocksTouching(3);
							blocksJoined.get(j).blocksTouching(7);
						}
					}
					else if(x > 0) {
						if(y < 0) {
							blocksJoined.get(i).blocksTouching(0);
							blocksJoined.get(j).blocksTouching(4);
						}
						else if(y > 0) {
							blocksJoined.get(i).blocksTouching(6);
							blocksJoined.get(j).blocksTouching(2);
						}
						else {
							blocksJoined.get(i).blocksTouching(7);
							blocksJoined.get(j).blocksTouching(3);
						}
					}
					else {
						if(y < 0) {
							blocksJoined.get(i).blocksTouching(1);
							blocksJoined.get(j).blocksTouching(5);
						}
						else if(y > 0) {
							blocksJoined.get(i).blocksTouching(5);
							blocksJoined.get(j).blocksTouching(1);
						}
					}
				}
			}
		}
		
		for(Block b : blocksJoined) {
			b.updateSprite();
		}
	}
	
	public boolean getSolved() {
		for(Block b : blocksJoined) {
			if(!b.isSolved()) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<Block> getBlockArray() {
		return blocksJoined;
	}
	
	public Vector2[] getBlockPos() {
		Vector2[] v = new Vector2[blocksJoined.size()];
		
		for(int i = 0; i < blocksJoined.size(); i++) {
			v[i] = blocksJoined.get(i).getPosition();
		}
		return v;
	}
	
	private Vector2[] getBlockPosAfterPush(int direction) {
		int xOffset = (direction % 2 == 0) ? 0 : 1;
		int yOffset = (direction % 2 == 0) ? 1 : 0;
		xOffset *= (direction == 3) ? -1 : 1;
		yOffset *= (direction == 2) ? -1 : 1;
		Vector2[] v = new Vector2[blocksJoined.size()];
		
		for(int i = 0; i < blocksJoined.size(); i++) {
			Vector2 tempV = blocksJoined.get(i).getPosition();
			v[i] = new Vector2(tempV.x+xOffset, tempV.y+yOffset);
		}
		return v;
	}
}
