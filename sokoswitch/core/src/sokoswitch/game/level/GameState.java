package sokoswitch.game.level;

import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.entities.*;
import sokoswitch.game.entities.blocks.*;

public class GameState {

	private int[][] playerPos;
	private float[][][] blockPos;
	private int[][][] blockType;
	
	public GameState(ArrayList<Player> players, ArrayList<BlockWrapper> pushable) {
		this.playerPos = new int[players.size()][3];
		for(int i = 0; i < playerPos.length; i++) {
			playerPos[i][0] = (int)players.get(i).getPosition().x;
			playerPos[i][1] = (int)players.get(i).getPosition().y;
			playerPos[i][2] = (int)players.get(i).getFacing();
		}
		
		this.blockPos = new float[pushable.size()][][];
		this.blockType = new int[pushable.size()][][];
		for(int i = 0; i < blockPos.length; i++) {
			ArrayList<Block> bwArrb = pushable.get(i).getBlockArray();
			Vector2[] bwArrv = pushable.get(i).getBlockPos();	
			blockPos[i] = new float[bwArrv.length][2];
			blockType[i] = new int[bwArrv.length][];
			for(int j = 0; j < bwArrv.length; j++) {
				blockPos[i][j][0] = bwArrv[j].x;
				blockPos[i][j][1] = bwArrv[j].y;
				blockType[i][j] = new int[1];
				blockType[i][j][0] = bwArrb.get(j).getId();
			}
		}
	}
	
	public ArrayList<Player> getPlayerArray() {
		ArrayList<Player> players = new ArrayList<>();
		for(int i = 0; i < playerPos.length; i++) {
			Player p = new Player(playerPos[i][0], playerPos[i][1]);
			p.setFacing((byte)playerPos[i][2]);
			p.setSpritePos();
			players.add(p);
		}
		return players;
	}
	
	public ArrayList<BlockWrapper> getBlockArray() {
		ArrayList<BlockWrapper> pushable = new ArrayList<>();
		for(int i = 0; i < blockPos.length; i++) {
			BlockWrapper bw = new BlockWrapper();
			for(int j = 0; j < blockPos[i].length; j++) {
				Block b = null;
				switch(blockType[i][j][0]) {
					case 3:
					case 4:
						b = new NormalBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][0] == 4));
						break;
					case 5:
					case 6:
						b = new LockedBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][0] == 6));
						break;
				}
				b.setSpritePos();
				bw.addBlock(b);
			}
			pushable.add(bw);
		}
		return pushable;
	}
}
