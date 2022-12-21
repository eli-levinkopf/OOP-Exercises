package pepse;


import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Sky;

public class PepseGameManager extends GameManager {

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // create sky
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

    }



    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}