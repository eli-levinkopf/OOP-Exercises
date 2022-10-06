package src.brick_strategies;

import src.BrickerGameManager;
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
    private static SoundReader soundReader;
    private static ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private final Random random = new Random();
    private final RemoveBrickStrategy removeBrickStrategy;

    public BrickStrategyFactory(GameObjectCollection gameObjects, SoundReader soundReader,
                                ImageReader imageReader, UserInputListener inputListener,
                                WindowController windowController,
                                BrickerGameManager gameManager) {
        BrickStrategyFactory.gameObjects = gameObjects;
        BrickStrategyFactory.soundReader = soundReader;
        BrickStrategyFactory.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.gameManager = gameManager;
        removeBrickStrategy = new RemoveBrickStrategy(gameObjects);
    }

    public CollisionStrategy getStrategy() {
        int rand = random.nextInt(0, NUM_OF_STRATEGIES-1);
        return switch (rand) {
            case 0 -> new AddGoodOrBadBotStrategy(removeBrickStrategy, imageReader, windowController);
            case 1 -> new ChangeCameraStrategy(removeBrickStrategy, windowController, gameManager);
            case 2 -> new AddPaddleStrategy(removeBrickStrategy, imageReader, inputListener,
                    windowController.getWindowDimensions());
            case 3 -> new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
            default -> new RemoveBrickStrategy(gameObjects);
        };
    }
}
