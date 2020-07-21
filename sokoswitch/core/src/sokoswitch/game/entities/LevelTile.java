package sokoswitch.game.entities;

import java.util.ArrayList;
import java.util.HashSet;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

import sokoswitch.game.GameAssetManager;

public class LevelTile extends Entity{

	private BitmapFont font;
	private String displayText;
	private GlyphLayout displayLayout;
	private Vector2 spriteCenter;
	private int connectedLevel;
	private ArrayList<Long> prereq;
	private int prereqSize;
	private boolean isShown;
	private boolean isSolved;
	
	public LevelTile(int x, int y, GameAssetManager manager, String displayText, int connectedLevel, ArrayList<Long> prereq, int prereqSize) {
		super(0, 11, x, y, manager);

		this.font = manager.getFont(0);
        this.displayText = displayText;
        this.displayLayout = new GlyphLayout(font, displayText);
		this.connectedLevel = connectedLevel;
		this.prereq = prereq;
		this.prereqSize = prereqSize;
		this.isShown = false;
		this.isSolved = false;
		
		setSpritePos();
		this.spriteCenter = new Vector2(sprite.getX()+sprite.getWidth()/2, sprite.getY()+sprite.getHeight()/2);
	}

	public void update(HashSet<Long> completed) {
		if(!isShown) {
			if(prereq.contains((long)0))
				isShown = true;
			else {
				int reqCount = 0;
				for(Long comp : completed) {
					if(prereq.contains(comp)) {
						reqCount++;
					}
				}
				if(reqCount >= prereqSize) {
					isShown = true;
				}
			}
		}
		if(!isSolved) {
			if(completed.contains((long)connectedLevel)) {
				isSolved = true;
			}
		}
		
		if(isSolved)
			setChangedSprite(13);
		else if(isShown)
			setChangedSprite(12);
	}
	
	@Override
	public void render(Batch batch) {
		sprite.draw(batch);
		if(isShown) font.draw(batch, displayText, spriteCenter.x-displayLayout.width/2, spriteCenter.y+displayLayout.height/2);
			//font.draw(batch, displayText, sprite.getX(), sprite.getY()-(stringHeight/2)+(Tiles.SIZE/2));
		
	}
	
	public boolean isShown() {
		return isShown;
	}
	
	public boolean isSolved() {
		return isSolved;
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
	
	public void setIsSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}
	
	private void setChangedSprite(int pos) {
		setSprite(0, pos);
		setSpritePos();
	}
}
