package sokoswitch.game.level;

import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.GameAssetManager;
import sokoswitch.game.entities.*;
import sokoswitch.game.entities.blocks.*;

public class GameState {

	private int[][] playerData;
	private float[][][] blockPos;
	private int[][][] blockType;
	
	public GameState(ArrayList<Player> players, ArrayList<BlockWrapper> pushable) {
		this.playerData = new int[players.size()][5];
		for(int i = 0; i < playerData.length; i++) {
			playerData[i][0] = (int)players.get(i).getPosition().x;
			playerData[i][1] = (int)players.get(i).getPosition().y;
			if(players.get(i) instanceof InversePlayer)
				playerData[i][2] = 2;
			else
				playerData[i][2] = 1;
			playerData[i][3] = (int)players.get(i).getTag();
			playerData[i][4] = (int)players.get(i).getFacing();
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
				blockType[i][j] = new int[2];
				blockType[i][j][0] = bwArrb.get(j).getId();
				blockType[i][j][1] = bwArrb.get(j).getState() ? 1 : 0;
			}
		}
	}
	
	public ArrayList<Player> getPlayerArray(GameAssetManager manager) {
		ArrayList<Player> players = new ArrayList<>();
		for(int i = 0; i < playerData.length; i++) {
			Player p = null;
			if(playerData[i][2] == 1)
				p = new Player(playerData[i][0], playerData[i][1], playerData[i][3], playerData[i][4], manager);
			else
				p = new InversePlayer(playerData[i][0], playerData[i][1], playerData[i][3], playerData[i][4], manager);
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
					case 4:
					case 5:
						b = new NormalBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][1] == 1), manager);
						break;
					case 6:
					case 7:
						b = new LockedBlock((int)blockPos[i][j][0], (int)blockPos[i][j][1], (blockType[i][j][1] == 1), manager);
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
