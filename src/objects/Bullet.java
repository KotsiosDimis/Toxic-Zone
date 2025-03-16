package objects;

import objects.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

import static utilz.Constants.Bullets.*;

public class Bullet {
	private Rectangle2D.Float hitbox;
	private int dir;
	private boolean active = true;

	public Bullet(int x, int y, int dir) {
		int xOffset = (int) (-3 * Game.SCALE);
		int yOffset = (int) (15 * Game.SCALE);  //5

		if (dir == 1)
			xOffset = (int) (29 * Game.SCALE);

		hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, BULLET_WIDTH, BULLET_HEIGHT);
		this.dir = dir;
	}

	public void updatePos() {
		hitbox.x += dir * SPEED;
	}

	public void setPos(int x, int y) {
		hitbox.x = x;
		hitbox.y = y;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
        
        public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(30,30,16,16);
        }

}
