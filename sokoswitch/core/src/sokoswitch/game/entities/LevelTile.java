package sokoswitch.game.entities;

import java.util.ArrayList;
import java.util.HashSet;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import sokoswitch.game.GameAssetManager;

public class LevelTile extends Entity{

	private BitmapFont font;
	private String displayText;
	private float stringWidth;
	private float stringHeight;
	private int connectedLevel;
	private ArrayList<Long> prereq;
	private int prereqSize;
	private boolean isShown;
	private boolean isSolved;
	
	public LevelTile(int x, int y, GameAssetManager manager, String displayText, int connectedLevel, ArrayList<Long> prereq, int prereqSize) {
		super(-3, x, y, manager);

		this.font = (displayText.length() <= 2) ? manager.getFont(0) : manager.getFont(1);
        this.displayText = displayText;
        GlyphLayout gl = new GlyphLayout(font, this.displayText);
		this.stringWidth = (displayText.length() < 2) ? gl.width/3.4f : gl.width/2.8f;
		this.stringHeight = gl.height/1.5f;
		this.connectedLevel = connectedLevel;
		this.prereq = prereq;
		this.prereqSize = prereqSize;
		this.isShown = false;
		this.isSolved = false;
		
		setSpritePos();
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
			setChangedSprite(-1);
		else if(isShown)
			setChangedSprite(-2);
	}
	
	@Override
	public void render(Batch batch) {
		sprite.draw(batch);
		if(isShown) font.draw(batch, displayText, sprite.getX()-stringWidth, sprite.getY()+stringHeight);
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
	
	private void setChangedSprite(int id) {
		setSprite(id);
		setSpritePos();
	}
}
