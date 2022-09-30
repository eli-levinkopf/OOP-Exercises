package src.brick_strategies;

import collision.CollisionStrategy;
import src.BrickerGameManager;
import paddle_strategies.PaddleStrategyFactory;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;

import java.util.Random;

public class BrickStrategyFactory {

    public static final int NUM_OF_STRATEGIES = 5;
    private static GameObjectCollection gameObjects;
    private static Counter bricksCounter;
    private static SoundReader soundReader;
    private static ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final PaddleStrategyFactory paddleStrategyFactory;
    private final BrickerGameManager gameManager;
    private final Random random = new Random();

    public BrickStrategyFactory(GameObjectCollection gameObjects, Counter bricksCounter, SoundReader soundReader,
                                ImageReader imageReader, UserInputListener inputListener,
                                WindowController windowController, PaddleStrategyFactory paddleStrategyFactory,
                                BrickerGameManager gameManager) {
        BrickStrategyFactory.gameObjects = gameObjects;
        BrickStrategyFactory.bricksCounter = bricksCounter;
        BrickStrategyFactory.soundReader = soundReader;
        BrickStrategyFactory.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.paddleStrategyFactory = paddleStrategyFactory;
        this.gameManager = gameManager;
    }

    public CollisionStrategy getStrategy() {
        int rand = random.nextInt(0, NUM_OF_STRATEGIES-1);
        return switch (rand) {
            case 0 -> new AddGoodOrBadBot(gameObjects, bricksCounter, imageReader, windowController);
            case 1 -> new ChangeCameraStrategy(gameObjects, bricksCounter, gameManager, windowController);
            case 2 -> new AdditionalPaddleStrategy(gameObjects, bricksCounter, imageReader, inputListener,
                    windowController, paddleStrategyFactory);
            case 3 -> new SplitBrickTo3BallsStrategy(gameObjects, bricksCounter, imageReader, soundReader, gameManager);
            default -> new RemoveBrickStrategy(gameObjects, bricksCounter);
        };


//        ++i;
//        if (i % 8 == 0) {
//            return new AddGoodOrBadBot(gameObjects, bricksCounter, imageReader, windowController);
//            return new ChangeCameraStrategy(gameObjects, bricksCounter, gameManager, windowController);
//            return new AdditionalPaddleStrategy(gameObjects, bricksCounter, imageReader, inputListener, windowController, paddleStrategyFactory);
//        }
//        else if (i % 16 == 0) {
//            return new SplitBrickTo3BallsStrategy(gameObjects, bricksCounter, imageReader, soundReader, gameManager);
//        }
//        return new RemoveBrickStrategy(gameObjects, bricksCounter);


    }
}
