package pepse;


import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.awt.*;

public class PepseGameManager extends GameManager {

    public static final int SEED = 1;
    public static final int MIN_X = 0;
    public static final int CYCLE_LENGTH = 10;
    public static final int SUN_LAYER = Layer.BACKGROUND + 1;
    public static final int SUN_HALO_LAYER = Layer.BACKGROUND + 10;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // create sky
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        // create ground
        Terrain terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS, windowController.getWindowDimensions(), SEED);
        terrain.createInRange(MIN_X, (int) windowController.getWindowDimensions().x());

        // create night
        Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), CYCLE_LENGTH);

        // create sun
        GameObject sun = Sun.create(gameObjects(), SUN_LAYER, windowController.getWindowDimensions(),
                CYCLE_LENGTH);

        // create sunHalo
        SunHalo.create(gameObjects(), SUN_HALO_LAYER, sun, new Color(255, 255, 0, 20));

    }


    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}