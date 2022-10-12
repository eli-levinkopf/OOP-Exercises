package src.brick_strategies;

import danogl.util.Vector2;
import src.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;

import java.util.Random;

public class BrickStrategyFactory {

    public static final int NUM_OF_STRATEGIES = 6;
    private static GameObjectCollection gameObjects;
    private static SoundReader soundReader;
    private static ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private final Random random = new Random();
    private final RemoveBrickStrategy removeBrickStrategy;
    private final Vector2 windowDimensions;


    public BrickStrategyFactory(GameObjectCollection gameObjectCollection, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                                WindowController windowController, Vector2 windowDimensions) {
        BrickStrategyFactory.gameObjects = gameObjectCollection;
        BrickStrategyFactory.soundReader = soundReader;
        BrickStrategyFactory.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        this.gameManager = gameManager;
        removeBrickStrategy = new RemoveBrickStrategy(gameObjectCollection);
    }

    public CollisionStrategy getStrategy(boolean isDoubleInstance) {
        //TODO: check if we can add parameters (isDoubleInstance)
        //TODO: Support 3 strategies
        int rand;
        if (isDoubleInstance) {
            rand = random.nextInt(1, NUM_OF_STRATEGIES - 1);
        } else {
            rand = random.nextInt(0, NUM_OF_STRATEGIES);
        }
        return switch (rand) {
            case 0 -> new RemoveBrickStrategy(gameObjects);
            case 1 -> new AddPaddleStrategy(removeBrickStrategy, imageReader, inputListener, windowDimensions);
            case 2 -> new ChangeCameraStrategy(removeBrickStrategy, windowController, gameManager);
            case 3 -> new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
            case 4 -> new AddGoodOrBadBotStrategy(removeBrickStrategy, imageReader, windowController);
//            case 5 -> new DoubleStrategy(gameObjects, soundReader, imageReader, inputListener, windowController,
//                    gameManager);
            case 5 -> new DoubleStrategy();
            default -> null;
        };
    }
}
