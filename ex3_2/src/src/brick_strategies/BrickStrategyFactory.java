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

/**
 * Factory for creating a new collision strategy.
 */
public class BrickStrategyFactory {

    public static final int FIRST_STRATEGY = 0;
    public static final int SECOND_STRATEGY = 1;
    public static final int THIRD_STRATEGY = 2;
    public static final int FOURTH_STRATEGY = 3;
    public static final int FIFTH_STRATEGY = 4;
    public static final int SIX_STRATEGY = 5;
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


    /**
     * Construct a new collision strategy instance.
     *
     * @param gameObjectCollection reference to the game collection instance.
     * @param gameManager          reference to the main game manager.
     * @param imageReader          reference to an object used to read images from the disc and
     *                             render them.
     * @param soundReader          reference to an object used to read sound files from the disc and
     *                             render them.
     * @param inputListener        reference to a listener capable of reading user keyboard inputs
     * @param windowController     reference to a listener capable of reading user keyboard inputs
     * @param windowDimensions     dimension of game window.
     * @param graphicLifeCounter   reference to graphicLifeCounter.
     * @param numericLifeCounter   reference to numericLifeCounter.
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager, ImageReader imageReader,
                                SoundReader soundReader, UserInputListener inputListener,
                                WindowController windowController, Vector2 windowDimensions,
                                GraphicLifeCounter graphicLifeCounter,
                                NumericLifeCounter numericLifeCounter) {
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

    /**
     * Randomly creates a new instance of strategy.
     *
     * @return a new instance of strategy.
     */
    public CollisionStrategy getStrategy() {
        int rand = random.nextInt(NUM_OF_STRATEGIES);
        switch (rand) {
            case FIRST_STRATEGY:
                return new RemoveBrickStrategy(gameObjects);
            case SECOND_STRATEGY:
                return new AddPaddleStrategy(removeBrickStrategy, imageReader, inputListener,
                        windowDimensions);
            case THIRD_STRATEGY:
                return new ChangeCameraStrategy(removeBrickStrategy, windowController, gameManager);
            case FOURTH_STRATEGY:
                return new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
            case FIFTH_STRATEGY:
                return new ReturnLifeStrategy(removeBrickStrategy, imageReader,
                        graphicLifeCounter, numericLifeCounter);
            case SIX_STRATEGY:
                return new DoubleStrategy(getStrategyForDouble(NUM_OF_STRATEGIES - 1),
                        getStrategyForDouble(NUM_OF_STRATEGIES - 2));
            default:
                return null;
        }
    }


    /**
     * Randomly creates a new instance of strategy for double strategy.
     *
     * @param numOfStrategies number of strategies to choose for double strategy.
     * @return s new instance of strategy.
     */
    public CollisionStrategy getStrategyForDouble(int numOfStrategies) {
        int rand = random.nextInt(numOfStrategies);
        switch (rand) {
            case FIRST_STRATEGY:
                return new AddPaddleStrategy(removeBrickStrategy, imageReader, inputListener,
                        windowDimensions);
            case SECOND_STRATEGY:
                return new ChangeCameraStrategy(removeBrickStrategy, windowController, gameManager);
            case THIRD_STRATEGY:
                return new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
            case FOURTH_STRATEGY:
                return new ReturnLifeStrategy(removeBrickStrategy, imageReader,
                        graphicLifeCounter, numericLifeCounter);
            case FIFTH_STRATEGY:
                return new DoubleStrategy(getStrategyForDouble(NUM_OF_STRATEGIES - 2),
                        getStrategyForDouble(NUM_OF_STRATEGIES - 2));
            default:
                return null;
        }
    }
}
