package objects;

import entities.Enemy;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.CanSentryGunSeePlayer;
import static utilz.Constants.Bullets.*;
import static utilz.HelpMethods.IsBulletHittingLevel;

public class ObjectManager {

	private final Playing playing;
	private BufferedImage[][] potionImgs, containerImgs;
	private BufferedImage[] sentryGunImgs, rocksImgs;
	private BufferedImage spikeImg, bulletImg;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private ArrayList<Spike> spikes;
	public ArrayList<SentryGun> sentryGun;
        private final ArrayList<Bullet> bullets = new ArrayList<>();
        private ArrayList<Level> levels;

        
        private Level currentLevel;
        
	public ObjectManager(Playing playing) {
		this.playing = playing;
                currentLevel = playing.getLevelManager().getCurrentLevel();
		loadImgs();
	}
        
        public ObjectManager() {
        levels = new ArrayList<>();
            this.playing = null;
    }
        
         public void addLevel(Level level) {
        levels.add(level);
    }
        
         public Level getCurrentLevel() {
        if (levels.isEmpty()) {
            // Handle the case when there are no levels available
            // You can log an error message or throw an exception here
            return null; // Or return a default level
        } else {
            return levels.get(0); // Return the current level or adjust the index as needed
        }
    }
        

	public void checkSpikesTouched(Player p) {
		for (Spike s : currentLevel.getSpikes())
			if (s.getHitbox().intersects(p.getHitbox()))
				p.kill();
	}
        
        public void checkSpikesTouched(Enemy e) {
		for (Spike s : currentLevel.getSpikes())
			if (s.getHitbox().intersects(e.getHitbox()))
				e.hurt(200);
	}

        public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Potion p : potions)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
	}
	
	public void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else
			playing.getPlayer().changeHealth(BLUE_POTION_VALUE);//kanonika edw pairnei energy apla epeidh exoyme sto bareli na emfanizetai
                                                     //to mple potion to kaname to mple potion me to poy to pairneis na allazeis zwh                                 
	}

	public void checkObjectHit(Rectangle2D.Float attackbox) {
		for (GameContainer gc : containers)
			if (gc.isActive() && !gc.doAnimation) {
				if (gc.getHitbox().intersects(attackbox)) {
					gc.setAnimation(true);
					int type = 0;
					if (gc.getObjType() == BARREL)
						type = 1;
					potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
					return;
				}
			}
	}

	public void loadObjects(Level newLevel) {
                currentLevel = newLevel;
		potions = new ArrayList<>(newLevel.getPotions());
		containers = new ArrayList<>(newLevel.getContainers());
		spikes = newLevel.getSpikes();
		sentryGun = newLevel.getSentryGun();
                bullets.clear();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[2][7];

		for (int j = 0; j < potionImgs.length; j++)
			for (int i = 0; i < potionImgs[j].length; i++)
				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
		containerImgs = new BufferedImage[2][8];

		for (int j = 0; j < containerImgs.length; j++)
			for (int i = 0; i < containerImgs[j].length; i++)
				containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

		spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

		sentryGunImgs = new BufferedImage[8];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SENTRY_GUN_ATLAS);
                
		for (int i = 0; i < sentryGunImgs.length; i++)
			sentryGunImgs[i] = temp.getSubimage(i * 32, 0, 32, 32);

		
                bulletImg = LoadSave.GetSpriteAtlas(LoadSave.BULLET);
                
                BufferedImage grassTemp = LoadSave.GetSpriteAtlas(LoadSave.ROCK_ATLAS);
		rocksImgs = new BufferedImage[2];
		for (int i = 0; i < rocksImgs.length; i++)
			rocksImgs[i] = grassTemp.getSubimage(32 * i, 0, 32, 32);
	
                
	}

	public void update(int[][] lvlData, Player player) {
		for (Potion p : potions){
			if (p.isActive())
				p.update();
                                        }
		for (GameContainer gc : containers)
			if (gc.isActive())
				gc.update();

		updateSentryGun(lvlData, player);
                                    updateBullets(lvlData, player);
                
	}
        
        private void updateBullets(int[][] lvlData, Player player) {
        for (Bullet b : bullets)
			if (b.isActive()) {
				b.updatePos();
                                if (b.getHitbox().intersects(player.getHitbox())) {
                      player.changeHealth(-25);
                      b.setActive(false);
                }   else if (IsBulletHittingLevel(b, lvlData))
                       b.setActive(false);
            }
			
                        
        }
	
	private boolean isPlayerInRange(SentryGun c, Player player) {
		int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
		return absValue <= Game.TILES_SIZE * 100;
	}

	private boolean isPlayerInfrontOfSentryGun(SentryGun c, Player player) {
		if (c.getObjType() == SENTRY_GUN_LEFT) {
			if (c.getHitbox().x > player.getHitbox().x)
				return true;

		} else if (c.getHitbox().x < player.getHitbox().x)
			return true;
		return false;
	}

	private void updateSentryGun(int[][] lvlData, Player player) {
		for (SentryGun c : sentryGun) {
			if (!c.doAnimation)
				if (c.getTileY() == player.getTileY())
					if (isPlayerInRange(c, player))
						if (isPlayerInfrontOfSentryGun(c, player))
							if (CanSentryGunSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY()))
								c.setAnimation(true);
                                                        

			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0)//{
				shootSentryGun(c);
  //                              shoot(c);}
		}
	}
        
 
	public void shootSentryGun(SentryGun c) {
		int dir = 1;
		if (c.getObjType() == SENTRY_GUN_LEFT)
			dir = -1;
                 bullets.add(new Bullet((int) c.getHitbox().x, (int) c.getHitbox().y, dir));
	}
        
	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		drawPotions(g, xLvlOffset,yLvlOffset);
		drawContainers(g, xLvlOffset,yLvlOffset);
		drawTraps(g, xLvlOffset,yLvlOffset);
		drawSentryGun(g, xLvlOffset,yLvlOffset);
                drawBullets(g,xLvlOffset,yLvlOffset);
                drawRocks(g,xLvlOffset,yLvlOffset);
	}
        
        private void drawRocks(Graphics g, int xLvlOffset, int yLvlOffset) {
            for (Rocks grass : currentLevel.getRocks())
			g.drawImage(rocksImgs[grass.getType()], grass.getX() - xLvlOffset, grass.getY() - yLvlOffset, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), null);
	}
        
	

        private void drawBullets(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Bullet b : bullets)
			if (b.isActive())
				g.drawImage(bulletImg, (int) (b.getHitbox().x - xLvlOffset), (int) (b.getHitbox().y - yLvlOffset ), BULLET_WIDTH, BULLET_HEIGHT, null);

	}
             

	private void drawSentryGun(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (SentryGun c : sentryGun) {
			int x = (int) (c.getHitbox().x - xLvlOffset);
                        int y = (int) (c.getHitbox().y - yLvlOffset);
			int width = SENTRY_GUN_WIDTH;

			if (c.getObjType() == SENTRY_GUN_RIGHT) {
				x += width; 
				width *= -1;
			}

			g.drawImage(sentryGunImgs[c.getAniIndex()], x, y  , width, SENTRY_GUN_HEIGHT, null);
		}

	}

	private void drawTraps(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Spike s : spikes)
			g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset() - yLvlOffset ), SPIKE_WIDTH, SPIKE_HEIGHT, null);

	}

	private void drawContainers(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (GameContainer gc : containers)
			if (gc.isActive()) {
				int type = 0;
				if (gc.getObjType() == BARREL)
					type = 1;
				g.drawImage(containerImgs[type][gc.getAniIndex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset() - yLvlOffset), CONTAINER_WIDTH,
						CONTAINER_HEIGHT, null);
			}
	}

	private void drawPotions(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Potion p : potions)
			if (p.isActive()) {
				int type = 0;
				if (p.getObjType() == RED_POTION)
					type = 1;
				g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset() - yLvlOffset ), POTION_WIDTH, POTION_HEIGHT,
						null);
			}
	}

	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for (Potion p : potions)
			p.reset();
		for (GameContainer gc : containers)
			gc.reset();
		for (SentryGun c : sentryGun)
			c.reset();
	}
        

    

    
   
   
}
