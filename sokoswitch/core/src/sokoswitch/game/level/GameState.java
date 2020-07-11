package sokoswitch.game.level;

import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.*;
import sokoswitch.game.entities.blocks.*;
import sokoswitch.game.entities.blocks.abstracts.*;

public class GameState {

	private int[][] playerData;
	private float[][][] blockPos;
	private int[][][] blockType;
	
	public GameState(ArrayList<Player> players, ArrayList<BlockWrapper> pushable) {
		this.playerData = new int[players.size()][5];
		for(int i = 0; i < playerData.length; i++) {
			playerData[i][0] = (int)players.get(i).getPosition().x;
			playerData[i][1] = (int)players.get(i).getPosition().y;
			
			if(players.get(i) instanceof InversePlayer) playerData[i][2] = 2;
			else playerData[i][2] = 1;
			
			playerData[i][3] = (int)players.get(i).getFacing();
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
				int blockId = bwArrb.get(j).getBlockId();
				switch(blockId) {
					case 1:
					case 2:
						blockType[i][j] = new int[2];
						blockType[i][j][0] = blockId;
						blockType[i][j][1] = bwArrb.get(j).getState() ? 1 : 0;
						break;
					case 3:
					case 4:
						blockType[i][j] = new int[3];
						blockType[i][j][0] = blockId;
						blockType[i][j][1] = bwArrb.get(j).getState() ? 1 : 0;
						blockType[i][j][2] = ((NumberedBlock)bwArrb.get(j)).getSwitchAmount();
						break;
				}
			}
		}
	}
	
	public ArrayList<Player> getPlayerArray(GameAssetManager manager) {
		ArrayList<Player> players = new ArrayList<>();
		for(int i = 0; i < playerData.length; i++) {
			Player p = null;
			
			if(playerData[i][2] == 1) p = new Player(playerData[i][0], playerData[i][1], playerData[i][3], manager);
			else p = new InversePlayer(playerData[i][0], playerData[i][1], playerData[i][3], manager);
			
			p.setSpritePos();
			players.add(p);
		}
		return players;
	}
	
	public ArrayList<BlockWrapper> getBlockArray(GameAssetManager manager) {
		ArrayList<BlockWrapper> pushable = new ArrayList<>();
		for(int i = 0; i < blockPos.length; i++) {
			BlockWrapper bw = new BlockWrapper();
			for(int j = 0; j < blockPos[i].length; j++) {
				Block b = null;
				switch(blockType[i][j][0]) {
					case 1:
						b = new NormalBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][1] == 1), manager);
						break;
					case 2:
						b = new LockedBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][1] == 1), manager);
						break;
					case 3:
						b = new LimitedBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][1] == 1), (int)blockType[i][j][2], manager);
						break;
					case 4:
						b = new AttentionBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][1] == 1), (int)blockType[i][j][2], manager);
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
