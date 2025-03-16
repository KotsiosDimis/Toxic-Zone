package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import utilz.LoadSave;

public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage[] toxicSprite;
    private final ArrayList<Level> levels;
    private int lvlIndex = 0, aniTick, aniIndex;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        createToxic();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void createToxic() {
        toxicSprite = new BufferedImage[5];
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.TOXIC_TOP);
        for (int i = 0; i < 4; i++)
            toxicSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
        toxicSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.TOXIC_BOTTOM);
    }

    public void loadNextLevel() {
        if (!levels.isEmpty() && lvlIndex >= 0 && lvlIndex < levels.size()) {
            Level newLevel = levels.get(lvlIndex);
            game.getPlaying().getEnemyManager().loadEnemies(newLevel);
            game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
            game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
            game.getPlaying().getObjectManager().loadObjects(newLevel);
        } else {
            System.out.println("The list is empty or the index is out of bounds.");
            // Handle this situation appropriately for your game.
        }
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
        System.out.println("Number of levels: " + levels.size()); // Debugging output
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }

    public void draw(Graphics g, int xlvlOffset, int ylvlOffset) {
        for (int j = 0; j < levels.get(lvlIndex).getLevelData().length; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                int x = Game.TILES_SIZE * i - xlvlOffset;
                int y = Game.TILES_SIZE * j - ylvlOffset;

                switch (index) {
                    case 48:
                        g.drawImage(toxicSprite[aniIndex], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
                        break;
                    case 49:
                        g.drawImage(toxicSprite[4], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
                        break;
                    default:
                        g.drawImage(levelSprite[index], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
                        break;
                }
            }
        }
    }

    public void update() {
        updateToxicAnimation();
    }

    private void updateToxicAnimation() {
        aniTick++;
        if (aniTick >= 40) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= 4)
                aniIndex = 0;
        }
    }

    public Level getCurrentLevel() {
        System.out.println("lvlIndex: " + lvlIndex); // Debugging output
        if (!levels.isEmpty() && lvlIndex >= 0 && lvlIndex < levels.size()) {
            return levels.get(lvlIndex);
        } else {
            System.out.println("The list is empty or the index is out of bounds.");
            // Handle this situation appropriately for your game.
            return levels.isEmpty() ? null : levels.get(0); // Return null if no levels exist
        }
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLevelIndex() {
        return lvlIndex;
    }

    public void setLevelIndex(int lvlIndex) {
        this.lvlIndex = lvlIndex;
    }
}
