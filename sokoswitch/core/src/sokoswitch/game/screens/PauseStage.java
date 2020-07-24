package sokoswitch.game.screens;

import java.util.Stack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sokoswitch.game.GameAssetManager;

public class PauseStage extends Stage{
	
	private PauseButton[] buttonArr;
	private int cursorPos;
	private PauseMenu pScreen;
	
	public PauseStage(GameAssetManager gam, Camera camera, boolean isLevel, String stageNum, String levelName, PauseMenu pScreen) {
		super(new ScreenViewport(camera));	
		camera.translate(0, 0, 0);
		camera.update();
		
		this.cursorPos = 0;
		this.pScreen = pScreen;
		
		Table table = new Table();
		table.setTransform(true);
		table.setDebug(true, true);
		table.center();
		
		TextureRegion[] sprites = new TextureRegion[]{gam.getTextureRegion(1, 0), gam.getTextureRegion(1, 1)};
        if(isLevel) {
        	this.buttonArr = new PauseButton[3];
            this.buttonArr[0] = new PauseButton(gam.getFont(3), sprites, PauseStates.button_return);
     		this.buttonArr[1] = new PauseButton(gam.getFont(3), sprites, PauseStates.button_reset);
     		this.buttonArr[2] = new PauseButton(gam.getFont(3), sprites, PauseStates.button_leave);
        }
        else {
        	this.buttonArr = new PauseButton[2];
            this.buttonArr[0] = new PauseButton(gam.getFont(3), sprites, PauseStates.button_return);
     		this.buttonArr[1] = new PauseButton(gam.getFont(3), sprites, PauseStates.button_leave);
        }
        
        Label.LabelStyle style1 = new Label.LabelStyle(gam.getFont(1), null);
        	Label label1 = new Label(stageNum, style1);
        Label.LabelStyle style2 = new Label.LabelStyle(gam.getFont(2), null);
        	Label label2 = new Label(levelName, style2);
        table.add(label1).padBottom(10f);
        table.add(label2).padBottom(10f);
        table.row();
        
        for(PauseButton pb : buttonArr) {
        	table.add(pb).size(64*6, 24*6).colspan(2).padBottom(36f);
        	table.row();
        }
        
        table.setOrigin(table.getPrefWidth()/2, table.getPrefHeight()/2);
		table.setPosition(Gdx.graphics.getWidth()/2 - table.getWidth()/2, Gdx.graphics.getHeight()/2 - table.getHeight()/2);
		
		addActor(table);
	}
	
	public void takeInput(Stack<Integer> input) {
		if(!input.isEmpty()) {
			switch(input.peek()) {
				case 1:
					if(cursorPos > 0) cursorPos--;
					input.pop();
					break;
				case 2:
					if(cursorPos < buttonArr.length-1) cursorPos++;
					input.pop();
					break;
				case 5:
					pScreen.setPaused(false);
					switch(buttonArr[cursorPos].getState()) {
						case button_reset:
							pScreen.reset();
							break;
						case button_leave:
							pScreen.leave();
							break;
						default:
					}
					input.pop();
					break;
			}
		}
	}
	
	public void update(float delta) {
		for(int i = 0; i < buttonArr.length; i++) {
			if(i == cursorPos) buttonArr[i].setHovering(true);
			else buttonArr[i].setHovering(false);
		}
	}

	@Override
	public void draw() {
		act(Gdx.graphics.getDeltaTime());
		super.draw();
	}
	
	public enum PauseStates{
		button_return("Return"), 
		button_reset("Reset"), 
		button_leave("Leave");
		
		private String displayText;
		
		private PauseStates(String displayText) {
			this.displayText = displayText;
		}
		
		public String getDisplayText() {
			return displayText;
		}
	}
}
