package src.brick_strategies;

import danogl.util.Vector2;
import src.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.NumericLifeCounter;
import java.util.Random;

public class BrickStrategyFactory {

    public static final int NUM_OF_STRATEGIES = 6;

    private final GameObjectCollection gameObjects;
    private final SoundReader soundReader;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private final Random random = new Random();
    private final RemoveBrickStrategy removeBrickStrategy;
    private final GraphicLifeCounter graphicLifeCounter;
    private final NumericLifeCounter numericLifeCounter;
    private final Vector2 windowDimensions;


    public BrickStrategyFactory(GameObjectCollection gameObjectCollection, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                                WindowController windowController, Vector2 windowDimensions,
                                GraphicLifeCounter graphicLifeCounter, NumericLifeCounter numericLifeCounter) {
        this.gameObjects = gameObjectCollection;
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        this.gameManager = gameManager;
        this.graphicLifeCounter = graphicLifeCounter;
        this.numericLifeCounter = numericLifeCounter;
        removeBrickStrategy = new RemoveBrickStrategy(gameObjectCollection);
    }

    public CollisionStrategy getStrategy() {
        int rand = random.nextInt(NUM_OF_STRATEGIES);
        switch (rand) {
            case 0:
                return new RemoveBrickStrategy(gameObjects);
            case 1:
                return new AddPaddleStrategy(removeBrickStrategy, imageReader, inputListener,
                    windowDimensions);
            case 2:
                return new ChangeCameraStrategy(removeBrickStrategy, windowController, gameManager);
            case 3:
                return new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
            case 4:
                return new ReturnLifeStrategy(removeBrickStrategy, imageReader, graphicLifeCounter,
                numericLifeCounter);
            case 5:
                return new DoubleStrategy(getStrategyForDouble(NUM_OF_STRATEGIES-1),
                    getStrategyForDouble( NUM_OF_STRATEGIES-2));
            default:
                return null;
        }
    }
    
    public CollisionStrategy getStrategyForDouble(int numOfStrategies) {
        int rand = random.nextInt(numOfStrategies);
        switch (rand) {
            case 0:
                return new AddPaddleStrategy(removeBrickStrategy, imageReader, inputListener,
                    windowDimensions);
            case 1:
                return new ChangeCameraStrategy(removeBrickStrategy, windowController, gameManager);
            case 2:
                return new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
            case 3:
                return new ReturnLifeStrategy(removeBrickStrategy, imageReader, graphicLifeCounter,
                    numericLifeCounter);
            case 4:
                return new DoubleStrategy(getStrategyForDouble(NUM_OF_STRATEGIES-2),
                                         getStrategyForDouble( NUM_OF_STRATEGIES-2));
            default:
                return null;
        }
    }
}
