package objects;

import main.Game;

public class Cannon extends GameObject {

	private int tileY;

	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY = y / Game.TILES_SIZE;
		initHitbox(40, 26);  //40,26
		hitbox.x -= (int) (48* Game.SCALE); //4 //edw to sentrigun me allag timhs paei deksia h aristera
		hitbox.y += (int) (0 * Game.SCALE);  //6 //edw to sentrigun me allagh timhs paei panw h katw
	}

	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	public int getTileY() {
		return tileY;
	}

}
