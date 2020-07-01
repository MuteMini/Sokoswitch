package sokoswitch.game.entities;

import java.util.ArrayList;
import java.util.HashSet;
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
	private boolean isSolved;
	
	public LevelTile(int x, int y, GameAssetManager manager, String displayText, int connectedLevel, ArrayList<Long> prereq) {
		super(-3, x, y, manager);

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
		this.isSolved = false;
		
		setSpritePos();
	}

	public void update(HashSet<Long> completed) {
		if(!isShown) {
			if(prereq.contains((long)0)) {
				setChangedSprite(-2);
				isShown = true;
			}
			else {
				for(Long comp : completed) {
					if(prereq.contains(comp)) {
						setChangedSprite(-2);
						isShown = true;
						break;
					}
				}
			}
		}
		if(!isSolved) {
			if(completed.contains((long)connectedLevel)) {
				setChangedSprite(-1);
				isSolved = true;
			}
		}
	}
	
	@Override
	public void render(Batch batch) {
		sprite.draw(batch);
		if(isShown)
			font.draw(batch, displayText, sprite.getX()+(Tiles.SIZE/2)-(stringWidth/2)-120, sprite.getY()+60);
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
	
	private void setChangedSprite(int id) {
		setSprite(id);
		setSpritePos();
	}
}
