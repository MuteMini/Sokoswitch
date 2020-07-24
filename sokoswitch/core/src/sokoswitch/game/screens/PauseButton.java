package sokoswitch.game.screens;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import sokoswitch.game.screens.PauseStage.PauseStates;

public class PauseButton extends Image{
	
	private BitmapFont font;
	private TextureRegionDrawable[] textures;
	private float textWidth;
	private float textHeight;
	private PauseStates state;

	public PauseButton(BitmapFont font, TextureRegion[] sprites, PauseStates state) {
		super(new TextureRegionDrawable(sprites[0]), Scaling.stretch, Align.center);
		this.font = font;
		this.textures = new TextureRegionDrawable[2];
		this.textures[0] = new TextureRegionDrawable(sprites[0]);
		this.textures[1] = new TextureRegionDrawable(sprites[1]);
		GlyphLayout displayLayout = new GlyphLayout(font, state.getDisplayText());
		this.textWidth = displayLayout.width;
		this.textHeight = displayLayout.height;
		this.state = state;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		font.draw(batch, getState().getDisplayText(), (getX()+(getImageWidth()/2))-textWidth/2, (getY()+(getImageHeight()/2))+textHeight/2);
	}
	
	public void setHovering(boolean isHovering) {
		if(isHovering) setDrawable(textures[1]);
		else setDrawable(textures[0]);
	}
	
	public float getTextWidth() {
		return textWidth;
	}
	
	public float getTextHeight() {
		return textHeight;
	}
	
	public PauseStates getState() {
		return state;
	}
}
