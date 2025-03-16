package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class LoadSave {
    
    //////////////////////// Player Skins ///////////////////////
    public static final String Alex_ATLAS = "entities/Alex.png";
    public static final String Kotsios_ATLAS = "entities/Kotsios.png";

    //////////////////////// Entities ///////////////////////
    public static final String ZOMBIE_SPRITE = "entities/zombie.png";
    public static final String DOG_SPRITE = "entities/dog.png";
    public static final String BOSS_SPRITE = "entities/monster.png";

    //////////////////////// Objects ///////////////////////
    public static final String TRAP_ATLAS = "objects/trap_atlas.png";
    public static final String CONTAINER_ATLAS = "objects/objects_sprites.png";
    public static final String SENTRY_GUN_ATLAS = "objects/minigun.png";
    public static final String BULLET = "objects/bullet.png";
    public static final String POTION_ATLAS = "objects/potions_sprites.png";
    public static final String ROCK_ATLAS = "objects/rocks_atlas.png";

    //////////////////////// Map ///////////////////////
    public static final String LEVEL_ATLAS = "map/outside_sprites.png";
    public static final String BIG_CLOUDS = "map/big_clouds.png";
    public static final String SMALL_CLOUDS = "map/small_clouds.png";
    public static final String TOXIC_TOP = "map/Toxic_Waste_animation.png";
    public static final String TOXIC_BOTTOM = "map/Toxic_Waste.png";
    public static final String PLAYING_BG_IMG = "map/cave.png";

    //////////////////////// UI ///////////////////////
    public static final String MENU_BUTTONS = "ui/button_atlas.png";
    public static final String MENU_BACKGROUND = "ui/menu_background.png";
    public static final String PAUSE_BACKGROUND = "ui/pause_menu.png";
    public static final String SOUND_BUTTONS = "ui/sound_button.png";
    public static final String URM_BUTTONS = "ui/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "ui/volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "ui/background_menu.png";
    public static final String COMPLETED_IMG = "ui/completed_sprite.png";
    public static final String DEATH_SCREEN = "ui/death_screen.png";
    public static final String OPTIONS_MENU = "ui/options_background.png";
    public static final String CREDITS = "ui/credits-sheet.png";
    public static final String GAME_COMPLETED = "ui/game_completed.png";

    public static final String STATUS_BAR = "health_power_bar.png";

    //////////////////////// Image Loader ///////////////////////
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        try (InputStream is = LoadSave.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                System.out.println("❌ Error: Image file not found - " + fileName);
                return null;
            }
            img = ImageIO.read(is);
            System.out.println("✅ Loaded image: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error loading image: " + fileName);
            e.printStackTrace();
        }
        return img;
    }

    //////////////////////// Level Loader ///////////////////////
    public static BufferedImage[] GetAllLevels() {
        List<BufferedImage> images = new ArrayList<>();
        String[] levelFiles = { "1.png", "2.png", "3.png" }; // Manually list level files

        System.out.println("🔍 Searching for levels in: lvls/");

        for (String levelFile : levelFiles) {
            try (InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("lvls/" + levelFile)) {
                if (is == null) {
                    System.out.println("❌ Error: Level file not found - " + levelFile);
                    continue;
                }
                BufferedImage image = ImageIO.read(is);
                images.add(image);
                System.out.println("✅ Loaded level: " + levelFile);
            } catch (IOException e) {
                System.out.println("❌ Error loading level: " + levelFile);
                e.printStackTrace();
            }
        }

        return images.toArray(new BufferedImage[0]);
    }
}
