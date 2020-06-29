package sokoswitch.game.entities;

import java.util.ArrayList;
import java.util.HashSet;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import sokoswitch.game.GameAssetManager;
import sokoswitch.game.level.Tiles;

public class LevelTile extends Entity{

	private BitmapFont font;
	private String displayText;
	private float stringWidth;
	private int connectedLevel;
	private ArrayList<Long> prereq;
	private boolean isShown;
	
	public LevelTile(int x, int y, GameAssetManager manager, String displayText, int connectedLevel, ArrayList<Long> prereq) {
		super(-1, x, y, manager);

		this.font = manager.getFont(0);
		try {
	        int displayNum = Integer.parseInt(displayText);
	        this.displayText = (displayNum < 10) ? "0"+displayNum : ""+displayNum;
	    } catch (NumberFormatException e) {
	    	this.displayText = displayText;
	    }
		
		this.stringWidth = new GlyphLayout(font, this.displayText).width;
		this.connectedLevel = connectedLevel;
		this.prereq = prereq;
		this.isShown = false;
		
		setSpritePos();
	}

	public void update(HashSet<Long> completed) {
		if(!isShown) {
			if(prereq.contains((long)0)) {
				isShown = true;
			}
			else {
				for(Long comp : completed) {
					if(prereq.contains(comp)) {
						isShown = true;
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void render(Batch batch) {
		sprite.draw(batch);
		font.draw(batch, displayText, sprite.getX()+(Tiles.SIZE/2)-(stringWidth/2)-5, sprite.getY()+170);
	}
	
	public boolean isShown() {
		return isShown;
	}
	
	public int getConnectedLevel() {
		return connectedLevel;
	}
	
	public ArrayList<Long> getPrereq() {
		return prereq;
	}
	
	public void setIsShown(boolean isShown) {
		this.isShown = isShown;
	}
}
