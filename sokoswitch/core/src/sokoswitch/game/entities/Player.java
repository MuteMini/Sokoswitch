package sokoswitch.game.entities;

public class Player extends Entity{
	
	public Player(int x, int y, int xMax, int yMax) {
		super(2, x, y, xMax, yMax);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void dispose() {
		texture.getTexture().dispose();
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
