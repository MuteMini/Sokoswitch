package sokoswitch.game.screens;

public interface PauseMenu {
	
	boolean getPaused();
	void setPaused(boolean paused);
	void reset();
	void leave();
}
