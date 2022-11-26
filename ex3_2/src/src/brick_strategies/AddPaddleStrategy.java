package src.brick_strategies;

import src.BrickerGameManager;
import src.gameobjects.MockPaddle;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static src.BrickerGameManager.*;


public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {

    public static final int NUM_COLLISIONS_TO_DISAPPEAR = 3;
    private static final String PATH_TO_SECOND_PADDLE_PNG = "assets/botGood.png";
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
     * All strategy decorators should inherit from this class.
     *
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader
     * @param inputListener
     * @param windowDimensions
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                             UserInputListener inputListener, Vector2 windowDimensions) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;


    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (!BrickerGameManager.mockPaddle){
            createAdditionalPaddle(thisObj);
            BrickerGameManager.mockPaddle = true;
        }
    }

    private void createAdditionalPaddle(GameObject thisObj) {
        Renderable paddleImage = imageReader.readImage(PATH_TO_SECOND_PADDLE_PNG, true);
        GameObject mockPaddle = new MockPaddle(Vector2.ZERO, new Vector2(PADDLE_DIMENSION_X,
                PADDLE_DIMENSION_Y), paddleImage,
                inputListener, windowDimensions,  getGameObjectCollection(),
                MIN_DISTANCE_FROM_SCREEN_EDGE, NUM_COLLISIONS_TO_DISAPPEAR);
        mockPaddle.setCenter(new Vector2(thisObj.getCenter().x(), windowDimensions.y()*.5f));
        getGameObjectCollection().addGameObject(mockPaddle);
    }
}
