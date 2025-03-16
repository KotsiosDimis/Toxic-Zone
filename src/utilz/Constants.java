package utilz;

import main.Game;

public class Constants {

	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 25;

        public static class Bullets{
                ////////// Bullets stats  //////////////////
                public static final int BULLET_DEFAULT_WIDTH = 30;
                public static final int BULLET_DEFAULT_HEIGHT = 15;
                public static final int BULLET_WIDTH = (int)(Game.SCALE * BULLET_DEFAULT_WIDTH);
                public static final int BULLET_HEIGHT = (int)(Game.SCALE * BULLET_DEFAULT_HEIGHT);
                public static final float SPEED = 0.9f * Game.SCALE;
	}
     
	public static class ObjectConstants {

                ////////// ints for lvls //////////////////
		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;
		public static final int SPIKE = 4;
		public static final int SENTRY_GUN_LEFT = 5;
		public static final int SENTRY_GUN_RIGHT = 6;
                
                ////////// Widths & Hights /////////////////
		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 25;

		public static final int CONTAINER_WIDTH_DEFAULT = 50;
		public static final int CONTAINER_HEIGHT_DEFAULT = 40;
		public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 32;
		public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
		public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);

		public static final int SENTRY_GUN_WIDTH_DEFAULT = 80;
		public static final int SENTRY_GUN_HEIGHT_DEFAULT = 50;
		public static final int SENTRY_GUN_WIDTH = (int) (SENTRY_GUN_WIDTH_DEFAULT * Game.SCALE);
		public static final int SENTRY_GUN_HEIGHT = (int) (SENTRY_GUN_HEIGHT_DEFAULT * Game.SCALE);

		public static int GetSpriteAmount(int object_type) {
                    switch (object_type) {
                        case RED_POTION:
                        case BLUE_POTION:
                            return 7;
                        case BARREL:
                        case BOX:
                            return 8;
                        case SENTRY_GUN_LEFT:
                        case SENTRY_GUN_RIGHT:
                            return 7;
                        default:
                            return 1;
                    }
                }

	}

	public static class EnemyConstants {
                ////////// ints for lvls //////////
		public static final int ZOMBIE = 0;
                public static final int DOG = 1;
                public static final int BOSS = 2;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
                
                ////////// Widths & Hights & Offsets /////////////////
		public static final int ZOMBIE_WIDTH_DEFAULT = 32; //72
		public static final int ZOMBIE_HEIGHT_DEFAULT = 32;
		public static final int ZOMBIE_WIDTH = (int) (ZOMBIE_WIDTH_DEFAULT * Game.SCALE * 1.9);
		public static final int ZOMBIE_HEIGHT = (int) (ZOMBIE_HEIGHT_DEFAULT * Game.SCALE * 2);
		public static final int ZOMBIE_DRAWOFFSET_X = (int) (21 * Game.SCALE);//20
		public static final int ZOMBIE_DRAWOFFSET_Y = (int) (38 * Game.SCALE);
                
                public static final int DOG_WIDTH_DEFAULT = 32;
		public static final int DOG_HEIGHT_DEFAULT = 32;
		public static final int DOG_WIDTH = (int) (DOG_WIDTH_DEFAULT * Game.SCALE * 1.9);
		public static final int DOG_HEIGHT = (int) (DOG_HEIGHT_DEFAULT * Game.SCALE * 1.5);
		public static final int DOG_DRAWOFFSET_X = (int) (36 * Game.SCALE); //36
		public static final int DOG_DRAWOFFSET_Y = (int) (23 * Game.SCALE); //25

                public static final int BOSS_WIDTH_DEFAULT = 32;
		public static final int BOSS_HEIGHT_DEFAULT = 32;
		public static final int BOSS_WIDTH = (int) (BOSS_WIDTH_DEFAULT * Game.SCALE * 2.8);
		public static final int BOSS_HEIGHT = (int) (BOSS_HEIGHT_DEFAULT * Game.SCALE * 2.8);
		public static final int BOSS_DRAWOFFSET_X = (int) (30 * Game.SCALE); //20
		public static final int BOSS_DRAWOFFSET_Y = (int) (63* Game.SCALE); //38
                                    
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {

			switch (enemy_type) {
			case ZOMBIE:
				switch (enemy_state) {
				case IDLE:
					return 8;
				case RUNNING:
					return 8;
				case ATTACK:
					return 5;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
                                
                        case DOG:
				switch (enemy_state) {
				case IDLE:
					return 4;
				case RUNNING:
					return 6;
				case ATTACK:
					return 6;
				case HIT:
					return 4;
				case DEAD:
					return 4;
				}
                        case BOSS : 
				switch (enemy_state) {
				case IDLE:
					return 5;
				case RUNNING:
					return 8;
				case ATTACK:
					return 4;
				case HIT:
					return 4;
				case DEAD:
					return 4;
				}
			}
                        return 0;
		}
         
		public static int GetMaxHealth(int enemy_type){ //edw me times uetoyme thn zwh twn exurwn
			switch (enemy_type) {
			case ZOMBIE:
				return 80;  //10
                        case  DOG:
				return 40;  //10
                        case BOSS:
                                return 400;
			default:
				return 1;
			}
		}

		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
			case ZOMBIE:
				return 15;
                        case DOG:
				return 10;
                        case BOSS:
                                return 15;
			default:
				return 0;
			}
		}

	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}

		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}

		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
                
            
                ///////////////Stats////////////
                public static final int ATCK_DMG = 30;
            
            
            
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 2;
                public static final int ATTACK_IDEL = 4;
                public static final int ATTACK_RUNNING = 5;
                public static final int ATTACK_JUMP = 6;
                public static final int HIT = 7;
                public static final int DEAD = 8;
                
                public static final int PLAYER_WIDTH_DEFAULT = 48 ; //edw uetoyme to width kai to height toy paixth
                public static final int PLAYER_HEIGHT_DEFAULT = 32;
                public static final int PLAYER_WIDTH = (int) (PLAYER_WIDTH_DEFAULT * Game.SCALE * 1.9);
                public static final int PLAYER_HEIGHT = (int) (PLAYER_HEIGHT_DEFAULT * Game.SCALE * 2);
                
	

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			
                            case IDLE:
				return 10;
			case RUNNING:
                        case ATTACK_RUNNING:
				return 3;
                        case ATTACK_IDEL:
			
                            case ATTACK_JUMP:
				return 2;
			case HIT:
                        case DEAD:
				return 5;
			case JUMP:
                        
                        
				return 6;
	//		case FALLING:
			default:
				return 1;
			}
		}
	}

}