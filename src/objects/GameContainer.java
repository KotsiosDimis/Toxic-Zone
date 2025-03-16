package objects;

import static utilz.Constants.ObjectConstants.*;

import main.Game;

public class GameContainer extends GameObject {

	public GameContainer(int x, int y, int objType) {
		super(x, y, objType);
		createHitbox();
	}

	private void createHitbox() {
		if (objType == BOX) {
			initHitbox(25, 18);

			xDrawOffset = (int) (7 * Game.SCALE);//7 12
			yDrawOffset = (int) (12 * Game.SCALE);

		} else {
			initHitbox(40, 25);
			xDrawOffset = (int) (8 * Game.SCALE);
			yDrawOffset = (int) (5 * Game.SCALE);
		}

		hitbox.y += yDrawOffset + (int) (Game.SCALE * -2); //2 //edw allazw to poso panw h katw ua einai ta koutia
		hitbox.x += xDrawOffset / 2; //edw poso deksia arristera einai ta koytia
	}

	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}
}
