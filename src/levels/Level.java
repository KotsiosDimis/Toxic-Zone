package levels;

import enemies.Boss;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Color;

import enemies.Zombie;
import enemies.Dog;
import main.Game;
import objects.Cannon;
import objects.GameContainer;
import objects.Potion;
import objects.Rocks;
import objects.Spike;
import objects.SentryGun;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.ObjectConstants.*;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;

    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Dog> dogs = new ArrayList<>();
    private ArrayList<Boss> bosses = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<GameContainer> containers = new ArrayList<>();
    private ArrayList<Cannon> cannons = new ArrayList<>();
    private ArrayList<Rocks> rocks = new ArrayList<>();
    private ArrayList<SentryGun> sentryGuns = new ArrayList<>();
    

    private int lvlTilesWide;
    private int lvlTilesHeight;
    private int maxTilesOffsetX;
    private int maxTilesOffsetY;
    private int maxLvlOffsetX;
    private int maxLvlOffsetY;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;

        lvlData = new int[img.getHeight()][img.getWidth()];
        loadLevel();
        calcLvlOffsets();
    }

    private void loadLevel() {
        // Looping through the image colors once instead of one per object/enemy/etc.
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
                loadObjects(blue, x, y);
            }
        }
    }

    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50)
            lvlData[y][x] = 0;
        else
            lvlData[y][x] = redValue;

        switch (redValue) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                rocks.add(new Rocks((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndRocksType(x)));
                break;
            default:
                break;
        }
    }

    private int getRndRocksType(int xPos) {
        return xPos % 2;
    }

    private void loadEntities(int greenValue, int x, int y) {
        switch (greenValue) {
            case ZOMBIE:
                zombies.add(new Zombie(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
                break;
            case DOG:
                dogs.add(new Dog(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
                break;
            case BOSS:
                bosses.add(new Boss(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
                break;
            case 100:
                playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
                break;
            default:
                break;
        }
    }

    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_POTION:
            case BLUE_POTION:
                potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
                break;
            case BOX:
            case BARREL:
                containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
                break;
            case SPIKE:
                spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
                break;
         //   case CANNON_LEFT:
          //  case CANNON_RIGHT:
         //       cannons.add(new Cannon(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
         //       break;
            default:
                break;
        }
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        lvlTilesHeight = img.getHeight();

        maxTilesOffsetX = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxTilesOffsetY = lvlTilesHeight - Game.TILES_IN_HEIGHT;

        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffsetX;
        maxLvlOffsetY = Game.TILES_SIZE * maxTilesOffsetY;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Dog> getDogs() {
        return dogs;
    }

    public ArrayList<Boss> getBosses() {
        return bosses;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public ArrayList<Rocks> getRocks() {
        return rocks;
    }
    
     public ArrayList<SentryGun> getSentryGun() {
        return sentryGuns;
    }
}
