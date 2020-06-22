package sokoswitch.game.entities;

import java.util.*;
import com.badlogic.gdx.math.Vector2;

public class BlockWrapper{
	
	protected ArrayList<Block> blocksJoined;
	
	public BlockWrapper(Block b) {
		this.blocksJoined = new ArrayList<>();
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
	
	public void switchStates() {
		for(Block b : blocksJoined) {
			b.switchState();
		}
	}
	
	public void connect(BlockWrapper newB) {
		this.blocksJoined.addAll(newB.blocksJoined);
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
	
	public Vector2[] getBlockPosAfterPush(int direction) {
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
