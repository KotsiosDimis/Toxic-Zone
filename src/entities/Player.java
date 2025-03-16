package entities;

import audio.AudioPlayer;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

	private BufferedImage[][] animations;
	public boolean moving = false, attacking = false;
	private boolean left, right, jump;
	private int[][] lvlData;
	private final float xDrawOffset = 21  * Game.SCALE;//peirazwntas ta noymera edw allazei to poso panw katw deksia h aristera apta blocks ua einai o paixths
	private final float yDrawOffset = 30  * Game.SCALE; //30
        private int aniTick = 0;
	private int aniIndex = 0;
	private final int aniSpeed = 25;
//	private int playerAction = IDLE;
	private final float playerSpeed = 1.0f * Game.SCALE;



	// Jumping / Gravity
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
        private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private boolean inAir = false;

        

	// StatusBarUI
	private BufferedImage statusBarImg;

	private final int statusBarWidth = (int) (192 * Game.SCALE);
	private final int statusBarHeight = (int) (58 * Game.SCALE);
	private final int statusBarX = (int) (10 * Game.SCALE);
	private final int statusBarY = (int) (10 * Game.SCALE);

	private final int healthBarWidth = (int) (150 * Game.SCALE);
	private final int healthBarHeight = (int) (4 * Game.SCALE);
	private final int healthBarXStart = (int) (34 * Game.SCALE);
	private final int healthBarYStart = (int) (14 * Game.SCALE);
	private int healthWidth = healthBarWidth;
        
	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private final Playing playing;

	private int tileY = 0;

	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 100;
		this.currentHealth = 35;
		this.walkSpeed = Game.SCALE * 1.0f;
		loadAnimations();
		initHitbox(20, 27);
		initAttackBox();
                                
	}
            
	

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (38* Game.SCALE), (int) (38 * Game.SCALE));//edw allazoyme to poso megalo ua einais se mhkos to atackbox dld mexri poso makria ua ftanei na baresei
                                  resetAttackBox();
	}

        ////////////////////////Update////////////////////////////
	public void update() {
		updateHealthBar();

		if (currentHealth <= 0) {
			if (state != DEAD) {
                                state = DEAD;
                                aniTick = 0;
                                aniIndex = 0;
                                playing.setPlayerDying(true);//edw bazoyme to death animation
                                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);//edw akougetai o hxos otan peuainei o player
			}else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
                            playing.setGameOver(true);
                            playing.getGame().getAudioPlayer().stopSong();
                            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);//edw akougetai o hxos otan einai gameover
			}else
				updateAnimationTick();
			return;
				}
          
		updateAttackBox();

		updatePos();
		if (moving) {
			checkPotionTouched();
			checkSpikesTouched();
                        checkInsideToxic();
			tileY = (int) (hitbox.y / Game.TILES_SIZE);
			}
                                    if (attacking)
			checkAttack();

		updateAnimationTick();
		setAnimation(true);
	}
        
        
        private void updatePos() {
		moving = false;

		if (jump)
                        jump();
	
		if (!inAir)
			if ((!left && !right) || (right && left))
				return;

		float xSpeed = 0;

		if (left) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
                        
		} else
			updateXPos(xSpeed);
		moving = true;
	}
                

        private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
	}
        
        private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}
        
        
        private void updateAttackBox(){ //edw uetw to koytaki tou atack box sto size poy to exw uesei apo pio prin se poio shmeio ua brisketai sto map
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 15);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 7);
                
		attackBox.y = hitbox.y + (Game.SCALE * 0); //edw uetw se poso ypsos
	}
        
        private void updateHealthBar() {
            healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}
        
        
        ////////////////////////Check////////////////////////////
        private void checkInsideToxic() {
		if (IsEntityInToxic(hitbox, playing.getLevelManager().getCurrentLevel().getLevelData()))
			currentHealth = 0;
	}

	private void checkSpikesTouched() {
		playing.checkSpikesTouched(this);

	}

	private void checkPotionTouched() {
		playing.checkPotionTouched(hitbox);
	}

	private void checkAttack() {
		if (attackChecked || aniIndex != 1)//edw chekaroume an sto atack barame kapoion enemy
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
                playing.getGame().getAudioPlayer().playAttackSound();
	}
        
        
        ////////////////////////Animation////////////////////////////
	public void render(Graphics g, int xlvlOffset , int ylvlOffset) {
		g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffset) - xlvlOffset + flipX, (int) (hitbox.y - yDrawOffset) - ylvlOffset, width * flipW, height, null);
	//	drawHitbox(g, lvlOffset);
	//	drawAttackBox(g, xlvlOffset,ylvlOffset); //edw emfanizw to koytaki toy atack
		drawUI(g);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	
	public void setAnimation(boolean par) {
		int startAni = state;
                
                if (state == HIT)
                    
			return;
                
                if (moving)
			state = RUNNING;        
		 else
			state = IDLE;
           
                if (inAir) {
			if (airSpeed < 0)
				state = JUMP;
			else
				state = FALLING;
		}
                
                if(attacking && inAir){
                    if (airSpeed < 0)
                            state = ATTACK_JUMP;
                    else
                        state = ATTACK_JUMP;
                        if (startAni != ATTACK_JUMP) {
				aniIndex = 1;
				aniTick = 0;
				return;}   
                
                }else if (attacking && moving){
                        state = ATTACK_RUNNING;
                        if (startAni != ATTACK_RUNNING) {
				aniIndex = 1;
				aniTick = 0;
				return; }  
                        
                }else if (attacking ){
			state = ATTACK_IDEL;
			if (startAni != ATTACK_IDEL) {
				aniIndex = 1;
				aniTick = 0;
				return;}
			}
                    	
		if (startAni != state)
			resetAniTick();
	}
        
        ////////////////////////Load////////////////////////////

	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.Alex_ATLAS);
		animations = new BufferedImage[9][10];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 48, j * 32, 48, 32);  //Player sprite dimensions 

		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
        
        
    ////////////////////////Actions////////////////////////////
        private void jump() {
            if (inAir)
		return;
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);//otan phdame akougetai o hxos poy kanei jump
	    inAir = true;
            airSpeed = jumpSpeed;
	}

	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}

	public void kill() {
		currentHealth = 0;
	}

	public void changePower(int value) {
		System.out.println("Added power!");
	}
         
    ////////////////////////Reset////////////////////////////
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		state = IDLE;
		currentHealth = maxHealth;
                airSpeed=0f; //ayto to bazoyme wste otan kanoyme restrt na mhn phdaei kai na peftei katw epeidh htan bug
		hitbox.x = x;
		hitbox.y = y;

               resetAttackBox();
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
               
        private void resetAttackBox(){
             if (flipW == 1)
		attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 15);
		else 
		attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 7); 
        }
        
        private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}
        
        private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}
        
        public void resetDirBooleans() {
		left = false;
		right = false;
	}
	

	
    ////////////////////////SET & GET & IS////////////////////////////   
        public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}
        
        public void setAttacking(boolean attacking) {
		this.attacking = attacking;          
	}
        
        public void setLeft(boolean left) {
		this.left = left;
	}
        
	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

        
        public int getTileY() {
		return tileY;
	}
        
        public boolean getAttacking(){
            return attacking;
        }    
        
        
        public boolean isRight() {
		return right;
	}
        
        public boolean isLeft() {
		return left;
	}
    
        
      
}