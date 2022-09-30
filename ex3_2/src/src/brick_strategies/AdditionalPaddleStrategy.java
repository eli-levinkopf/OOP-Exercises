package src.brick_strategies;

import collision.CollisionStrategy;
import src.gameobjects.Paddle;
import paddle_strategies.PaddleStrategyFactory;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static src.BrickerGameManager.*;


public class AdditionalPaddleStrategy extends RemoveBrickStrategy implements CollisionStrategy {

    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final GameObjectCollection gameObjects;
    private final PaddleStrategyFactory paddleStrategyFactory;

    public AdditionalPaddleStrategy(GameObjectCollection gameObjects, Counter bricksCounter, ImageReader imageReader,
                                    UserInputListener inputListener, WindowController windowController,
                                    PaddleStrategyFactory paddleStrategyFactory) {
        super(gameObjects, bricksCounter);
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.paddleStrategyFactory = paddleStrategyFactory;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        createAdditionalPaddle(thisObj);
    }

    private void createAdditionalPaddle(GameObject thisObj) {
        Renderable paddleImage = imageReader.readImage(PATH_TO_PADDLE_PNG, true);
        GameObject newPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_DIMENSION_X, PADDLE_DIMENSION_Y), paddleImage,
                inputListener, windowController.getWindowDimensions(), paddleStrategyFactory.getStrategy(), false);
        newPaddle.setCenter(new Vector2(thisObj.getCenter().x(), windowController.getWindowDimensions().y()*.75f));
        gameObjects.addGameObject(newPaddle);
    }
}
