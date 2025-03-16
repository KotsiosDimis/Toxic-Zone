package entities;

import enemies.Boss;
import enemies.Zombie;
import enemies.Dog;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import levels.Level;
import utilz.LoadSave;
import gamestates.Playing;
import static utilz.Constants.EnemyConstants.*;
import utilz.Constants.PlayerConstants;

public class EnemyManager {

	private final Playing playing;
	private BufferedImage[][] zombieArr,dogArr,bossArr;
        private Level currentLevel;
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
            this.currentLevel = level;
		 
          
	}
        
	public void update(int[][] lvlData) {
		boolean isAnyActive = false;
		for (Zombie c : currentLevel.getZombies())
			if (c.isActive()) {
				c.update(lvlData, playing);
				isAnyActive = true;
			}
                 for (Dog c :  currentLevel.getDogs())
			if (c.isActive()) {
				c.update(lvlData, playing);
				isAnyActive = true;
			}
                   for (Boss c :  currentLevel.getBosses())
			if (c.isActive()) {
				c.update(lvlData, playing);
				isAnyActive = true;
			}
		if (!isAnyActive)
			playing.setLevelCompleted(true);
	}
        
	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		drawZombies(g, xLvlOffset, yLvlOffset);
                    drawDogs(g, xLvlOffset, yLvlOffset);
                    drawBosses(g,xLvlOffset,yLvlOffset);
	}

	private void drawZombies(Graphics g, int xLvlOffset,int yLvlOffset) {
		for (Zombie s : currentLevel.getZombies())
			if (s.isActive()) {
				g.drawImage(zombieArr[s.getState()][s.getAniIndex()], (int) s.getHitbox().x - xLvlOffset - ZOMBIE_DRAWOFFSET_X + s.flipX(),
						(int) s.getHitbox().y - yLvlOffset - ZOMBIE_DRAWOFFSET_Y + (int) s.getPushDrawOffset(), ZOMBIE_WIDTH * s.flipW(), ZOMBIE_HEIGHT, null);
         			//s.drawHitbox(g, xLvlOffset);
				//s.drawAttackBox(g, xLvlOffset);
			}
	}

	private void drawDogs(Graphics g, int xLvlOffset,int yLvlOffset) {
		for (Dog s : currentLevel.getDogs())
			if (s.isActive()) {
				g.drawImage(dogArr[s.getState()][s.getAniIndex()], (int) s.getHitbox().x - xLvlOffset - DOG_DRAWOFFSET_X + s.flipX(),
						(int) s.getHitbox().y - yLvlOffset - DOG_DRAWOFFSET_Y + (int) s.getPushDrawOffset(), DOG_WIDTH * s.flipW(), DOG_HEIGHT, null);
         			//s.drawHitbox(g, xLvlOffset);
				//s.drawAttackBox(g, xLvlOffset);
			}
	}
        
        private void drawBosses(Graphics g, int xLvlOffset,int yLvlOffset) {
		for (Boss b : currentLevel.getBosses())
			if (b.isActive()) {
				g.drawImage(bossArr[b.getState()][b.getAniIndex()], (int) b.getHitbox().x - xLvlOffset - BOSS_DRAWOFFSET_X + b.flipX(),
						(int) b.getHitbox().y - yLvlOffset - BOSS_DRAWOFFSET_Y + (int) b.getPushDrawOffset(), BOSS_WIDTH * b.flipW(), BOSS_HEIGHT, null);
         			//s.drawHitbox(g, xLvlOffset);
				//s.drawAttackBox(g, xLvlOffset);
			}
        }
        
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Zombie c : currentLevel.getZombies())
			if (c.isActive())
				if (c.getState() != DEAD && c.getState() != HIT)
					if (attackBox.intersects(c.getHitbox())) {
						c.hurt(PlayerConstants.ATCK_DMG);//edw uetw poso damage skaw sto zombie allazwntas timh
						return;
					}

		for (Dog p : currentLevel.getDogs())
			if (p.isActive()) {
				if (p.getState() == ATTACK && p.getAniIndex() >= 3)
					return;
				else {
					if (p.getState() != DEAD && p.getState() != HIT)
						if (attackBox.intersects(p.getHitbox())) {
							p.hurt(PlayerConstants.ATCK_DMG);//edw uetw poso damage skaw sto dog
							return;
						}
				}
			}
                
		for (Boss b : currentLevel.getBosses())
			if (b.isActive())
				if (b.getState() != DEAD && b.getState() != HIT)
					if (attackBox.intersects(b.getHitbox())) {
						b.hurt(PlayerConstants.ATCK_DMG); //edw uetw poso damage skaw sto boss
						return;
                                        				}			
	}

	private void loadEnemyImgs() {
            
                zombieArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_SPRITE), 8, 5, ZOMBIE_WIDTH_DEFAULT, ZOMBIE_HEIGHT_DEFAULT);
		dogArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.DOG_SPRITE), 6, 5, DOG_WIDTH_DEFAULT, DOG_HEIGHT_DEFAULT);
		bossArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.BOSS_SPRITE), 8, 5, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
	}
        
        private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
		BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
		for (int j = 0; j < tempArr.length; j++)
			for (int i = 0; i < tempArr[j].length; i++)
				tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
		return tempArr;
	}

	public void resetAllEnemies() {
		for (Zombie c : currentLevel.getZombies())
			c.resetEnemy();
		for (Dog p : currentLevel.getDogs())
			p.resetEnemy();
                for (Boss b : currentLevel.getBosses())
			b.resetEnemy();
	}              
        
}
        