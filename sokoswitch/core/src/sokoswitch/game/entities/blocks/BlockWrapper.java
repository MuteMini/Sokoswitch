package sokoswitch.game.entities.blocks;

import java.util.*;
import com.badlogic.gdx.math.Vector2;

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
			b.move(direction);
		}
	}
	
	public void unpush() {
		for(Block b : blocksJoined) {
			b.setPushed(false);
		}
	}
	
	public boolean collides(int direction, BlockWrapper newB) {
		Vector2[] newBv = newB.getBlockPos();
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
	
	public boolean getBlockStateOn() {
		for(Block b : blocksJoined) {
			if(!b.getState()) {
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
