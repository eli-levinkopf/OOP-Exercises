package src;

import src.brick_strategies.CollisionStrategy;
import src.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    public static final int SPACE_BETWEEN_WIDGETS = 10;
    public static final int LIFE_WIDGETS_LAYER = -1;

    private static final String PATH_TO_BRICK_PNG = "assets/brick.png";
    private static final String PATH_TO_BACKGROUND_JPEG = "assets/DARK_BG2_small.jpeg";
    private static final String PATH_TO_PADDLE_PNG = "assets/paddle.png";
    private static final String PATH_TO_BALL_PNG = "assets/ball.png";
    private static final String PATH_TO_BLOP_WAV = "assets/blop_cut_silenced.wav";
    private static final String PATH_TO_HEART_PNG = "assets/heart.png";
    private static final String LOSE_MSG = "You lose!\n";
    private static final String WIN_MSG = "You win!\n";
    private static final String PLAY_AGAIN_MSG = "Play again?";

    private static final float BORDER_WIDTH = 500;
    private static final float BALL_SPEED = 150f;
    private static final int FRAME_THICKNESS = 15;
    private static final int BRICK_THICKNESS = 15;
    private static final int WIDGET_DIMENSION = 20;
    private static final int NUM_OF_ROWS = 7;
    private static final int NUM_OF_BRICKS_IN_ROW = 8;
    private static final int SPACE_BETWEEN_BRICKS = 1;
    private static final int NUM_OF_LIFE = 3;
    private static final float BALL_DIMENSION = 20;
    private static final float PADDLE_DIMENSION_Y = 15;
    private static final float PADDLE_DIMENSION_X = 100;
    private static final float WINDOW_DIMENSION_X = 700;
    private static final float WINDOW_DIMENSION_Y = 500;
    private Vector2 windowDimensions;
    private GameObject ball;
    private WindowController windowController;
    private final Counter bricksCounter = new Counter();
    private final Counter livesCounter = new Counter(NUM_OF_LIFE);
    private final GameObjectCollection gameObjectsCollection = new GameObjectCollection();
    private UserInputListener inputListener;
    private GraphicLifeCounter graphicLifeCounter;


    /**
     * Constructs a new instance of the BrickGameManager by calling its super (GameManager)'s
     * constructor.
     *
     * @param windowTitle      name for the game.
     * @param windowDimensions dimension of game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * Initializes a new game. It creates all game objects, sets their values and initial
     * positions and allow the start of a game.
     *
     * @param imageReader      an object used to read images from the disc and render them.
     * @param soundReader      an object used to read sound files from the disc and render them.
     * @param inputListener    a listener capable of reading user keyboard inputs
     * @param windowController a controller used to control the window and its attributes
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        addBall(imageReader, soundReader);
        addPaddles(imageReader, inputListener);
        addFrame();
        addBricks(imageReader);
        addGraphicLifeCounter(imageReader);
        addNumericLifeCounter();
        addBackground(imageReader);
    }

    /**
     * Adds a new numericLifeCounterText to the game and displays it in the screen.
     */
    private void addNumericLifeCounter() {
        NumericLifeCounter numericLifeCounter =
                new NumericLifeCounter(livesCounter, new Vector2(FRAME_THICKNESS,
                        windowDimensions.y() - FRAME_THICKNESS - WIDGET_DIMENSION -
                                SPACE_BETWEEN_WIDGETS - WIDGET_DIMENSION),
                        new Vector2(WIDGET_DIMENSION, WIDGET_DIMENSION), gameObjectsCollection);
        gameObjects().addGameObject(numericLifeCounter, LIFE_WIDGETS_LAYER);
    }

    /**
     * Adds a new life counter to the game and displays it in the screen.
     *
     * @param imageReader an object used to read images from the disc and render them.
     */
    private void addGraphicLifeCounter(ImageReader imageReader) {
        Renderable GraphicLifeCounterImage = imageReader.readImage(PATH_TO_HEART_PNG, true);
        Vector2 widgetTopLeftCorner = new Vector2(FRAME_THICKNESS,
                windowDimensions.y() - FRAME_THICKNESS - WIDGET_DIMENSION);
        Vector2 widgetDimensions = new Vector2(WIDGET_DIMENSION, WIDGET_DIMENSION);
        graphicLifeCounter = new GraphicLifeCounter(widgetTopLeftCorner,
                widgetDimensions, livesCounter, GraphicLifeCounterImage, gameObjects(), NUM_OF_LIFE);
        gameObjectsCollection.addGameObject(graphicLifeCounter);
    }

    /**
     * This method overrides the GameManager update method.
     *
     * @param deltaTime used in the super's update method.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        graphicLifeCounter.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * This method checks if the game end.
     */
    private void checkForGameEnd() {
        String prompt = "";
        boolean wPressed = false;
        if (ball.getCenter().y() > windowDimensions.y()) {
            livesCounter.decrement();
            setBall();
        if (livesCounter.value() == 0) {
                prompt += LOSE_MSG;
            }
        } else if (bricksCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            wPressed = true;
            prompt += WIN_MSG;
        }
        if (!prompt.isEmpty() && (livesCounter.value() == 0 || bricksCounter.value() == 0)
                || wPressed) {
            prompt += PLAY_AGAIN_MSG;
            if (windowController.openYesNoDialog(prompt)) {
                livesCounter.reset();
                livesCounter.increaseBy(NUM_OF_LIFE);
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * Adds a new brick to the game and displays it in the screen.
     *
     * @param imageReader an object used to read images from the disc and render them.
     */
    private void addBricks(ImageReader imageReader) {
        Renderable brickImage = imageReader.readImage(PATH_TO_BRICK_PNG, false);
        float brickLength = (windowDimensions.x() - (float) FRAME_THICKNESS * 2
                - (float) NUM_OF_ROWS) / (float) NUM_OF_ROWS;
        Vector2 brickDimensions = new Vector2(brickLength, BRICK_THICKNESS);
        Vector2 start = Vector2.ZERO.add(new Vector2(FRAME_THICKNESS, FRAME_THICKNESS));
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_BRICKS_IN_ROW; j++) {
                GameObject brick = new Brick(start, brickDimensions, brickImage,
                        new CollisionStrategy(gameObjects()), bricksCounter);
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                bricksCounter.increment();
                start = start.add(new Vector2(0, BRICK_THICKNESS + SPACE_BETWEEN_BRICKS));
            }
            start = start.add(new Vector2(brickLength + SPACE_BETWEEN_BRICKS,
                    -NUM_OF_BRICKS_IN_ROW * (BRICK_THICKNESS + SPACE_BETWEEN_BRICKS)));
        }
    }

    /**
     * Adds a background to the game.
     *
     * @param imageReader an object used to read images from the disc and render them.
     */
    private void addBackground(ImageReader imageReader) {
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions,
                imageReader.readImage(PATH_TO_BACKGROUND_JPEG, false));
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    /**
     * Adds a frame to the game board.
     */
    private void addFrame() {
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(FRAME_THICKNESS,
                windowDimensions.y()), null));
        gameObjects().addGameObject(new GameObject(new Vector2(windowDimensions.x(), 0),
                new Vector2(FRAME_THICKNESS, windowDimensions.y()), null));
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x()
                , FRAME_THICKNESS), null));
    }

    /**
     * Adds a new paddle to the game board and displays it in the screen.
     *
     * @param imageReader   an object used to read images from the disc and render them.
     * @param inputListener a listener capable of reading user keyboard inputs
     */
    private void addPaddles(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(PATH_TO_PADDLE_PNG, true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_DIMENSION_X,
                PADDLE_DIMENSION_Y), paddleImage, inputListener, windowDimensions, FRAME_THICKNESS);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 25));
        this.gameObjects().addGameObject(paddle);
    }

    /**
     * Adds a ball to the game and displays it in the screen.
     * @param imageReader an object used to read images from the disc and render them.
     * @param soundReader an object used to read sound files from the disc and render them.
     */
    private void addBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(PATH_TO_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(PATH_TO_BLOP_WAV);
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_DIMENSION, BALL_DIMENSION),
                ballImage, collisionSound);
        this.gameObjects().addGameObject(ball);
        this.ball = ball;
        setBall();
    }

    /**
     * Set the ball to the center of the board.
     * */
    private void setBall() {
        float ballVelocityX = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean()) {
            ballVelocityX *= -1;
        }
        ball.setVelocity(new Vector2(ballVelocityX, BALL_SPEED));
        ball.setCenter(windowDimensions.mult(0.5f));
    }

    /**
     * This function drives the program.
     */
    public static void main(String[] args) {
        BrickerGameManager brickerGameManager = new BrickerGameManager("Bricker",
                new Vector2(WINDOW_DIMENSION_X, WINDOW_DIMENSION_Y));
        brickerGameManager.run();
    }
}
       