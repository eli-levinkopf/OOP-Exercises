package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final float BALL_SPEED = 150f;
    private static final int FRAME_THICKNESS = 15;
    private static final int BRICK_THICKNESS = 15;
    private static final int WIDGET_DIMENSION = 20;
    private static final int SPACE_BETWEEN_WIDGETS = 10;
    private static final int NUM_OF_ROWS = 5;
    private static final int NUM_OF_BRICKS_IN_ROW = 8;
    private static final int SPACE_BETWEEN_BRICKS = 1;
    private static final int NUM_OF_LIFE = 3;
    private static final int LIFE_WIDGETS_LAYER = -1;
    private static final float BALL_DIMENSION = 20;
    private static final float PADDLE_DIMENSION_Y = 15;
    private static final float PADDLE_DIMENSION_X = 100;
    private static final float WINDOW_DIMENSION_X = 700;
    private static final float WINDOW_DIMENSION_Y = 500;
    public static final String PATH_TO_BRICK_PNG = "assets/brick.png";
    public static final String PATH_TO_BACKGROUND_JPEG = "assets/DARK_BG2_small.jpeg";
    public static final String PATH_TO_PADDLE_PNG = "assets/paddle.png";
    public static final String PATH_TO_BALL_PNG = "assets/ball.png";
    public static final String PATH_TO_BLOP_WAV = "assets/blop_cut_silenced.wav";
    public static final String PATH_TO_HEART_PNG = "assets/heart.png";
    public static final String LOSE_MSG = "You lose!\n";
    public static final String WIN_MSG = "You win!\n";
    public static final String PLAY_AGAIN_MSG = "Play again?";
    private Vector2 windowDimensions;
    private GameObject ball;
    private WindowController windowController;
    private Counter bricksCounter = new Counter();
    private Counter livesCounter = new Counter(NUM_OF_LIFE);
    private GameObjectCollection gameObjectsCollection = new GameObjectCollection();
    private GameObject[] livesWidgets = new GameObject[NUM_OF_LIFE];


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        addBall(imageReader, soundReader);
        addPaddles(imageReader, inputListener);
        addFrame();
        addBricks(imageReader);
        addGraphicLifeCounter(imageReader);
        addNumericLifeCounter();
        addBackground(imageReader);
    }

    private void addNumericLifeCounter() {
        TextRenderable numericLifeCounterText = new TextRenderable(Integer.toString(livesCounter.value()), Font.DIALOG_INPUT, true, true);
        numericLifeCounterText.setColor(Color.red);
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(new Vector2(FRAME_THICKNESS,
                windowDimensions.y() - FRAME_THICKNESS - WIDGET_DIMENSION - SPACE_BETWEEN_WIDGETS - WIDGET_DIMENSION),
                new Vector2(WIDGET_DIMENSION, WIDGET_DIMENSION), numericLifeCounterText, livesCounter, gameObjectsCollection);
        gameObjects().addGameObject(numericLifeCounter, LIFE_WIDGETS_LAYER);
    }

    private void addGraphicLifeCounter(ImageReader imageReader) {
        Renderable GraphicLifeCounterImage = imageReader.readImage(PATH_TO_HEART_PNG, true);
        Vector2 widgetTopLeftCorner = new Vector2(FRAME_THICKNESS, windowDimensions.y() - FRAME_THICKNESS - WIDGET_DIMENSION);
        Vector2 widgetDimensions = new Vector2(WIDGET_DIMENSION, WIDGET_DIMENSION);
        for (int i = 0; i < NUM_OF_LIFE; i++) {
            GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(widgetTopLeftCorner, widgetDimensions,
                    GraphicLifeCounterImage, livesCounter, gameObjects(), NUM_OF_LIFE);
            gameObjects().addGameObject(graphicLifeCounter, LIFE_WIDGETS_LAYER);
            //TODO: gameObjectsCollection?
//            gameObjectsCollection.addGameObject(graphicLifeCounter);
            livesWidgets[i] = graphicLifeCounter;
            widgetTopLeftCorner = widgetTopLeftCorner.add(new Vector2(WIDGET_DIMENSION + SPACE_BETWEEN_WIDGETS, 0));
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void checkForGameEnd() {
        String prompt = "";
        if (ball.getCenter().y() > windowDimensions.y()) {
            livesCounter.decrement();
            setBall();
            if (livesCounter.value() >= 0) {
                gameObjects().removeGameObject(livesWidgets[livesCounter.value()], LIFE_WIDGETS_LAYER);
            }
            else if (livesCounter.value() == -1) {
                prompt += LOSE_MSG;
            }
        }
        else if (bricksCounter.value() == 0){
            prompt += WIN_MSG;
        }
        if (!prompt.isEmpty() && (livesCounter.value() == -1 || bricksCounter.value() == 0)) {
            prompt += PLAY_AGAIN_MSG;
            if (windowController.openYesNoDialog(prompt)) {
                livesCounter.increaseBy(NUM_OF_LIFE+1);
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    private void addBricks(ImageReader imageReader) {
        Renderable brickImage = imageReader.readImage(PATH_TO_BRICK_PNG, false);
        float brickLength = (windowDimensions.x() - (float) FRAME_THICKNESS * 2 - (float) NUM_OF_ROWS) / (float) NUM_OF_ROWS;
        Vector2 brickDimensions = new Vector2(brickLength, BRICK_THICKNESS);
        Vector2 start = Vector2.ZERO.add(new Vector2(FRAME_THICKNESS, FRAME_THICKNESS));
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_BRICKS_IN_ROW; j++) {
                GameObject brick = new Brick(start, brickDimensions, brickImage, new CollisionStrategy(gameObjects(), bricksCounter));
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                bricksCounter.increment();
                start = start.add(new Vector2(0, BRICK_THICKNESS + SPACE_BETWEEN_BRICKS));
            }
            start = start.add(new Vector2(brickLength + SPACE_BETWEEN_BRICKS,
                    -NUM_OF_BRICKS_IN_ROW * (BRICK_THICKNESS + SPACE_BETWEEN_BRICKS)));
        }
    }

    private void addBackground(ImageReader imageReader) {
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions,
                imageReader.readImage(PATH_TO_BACKGROUND_JPEG, true));
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    private void addFrame() {
        //TODO: for loop
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(FRAME_THICKNESS, windowDimensions.y()),
                null));
        gameObjects().addGameObject(new GameObject(new Vector2(windowDimensions.x(), 0),
                new Vector2(FRAME_THICKNESS, windowDimensions.y()), null));
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), FRAME_THICKNESS),
                null));
    }

    private void addPaddles(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(PATH_TO_PADDLE_PNG, true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_DIMENSION_X, PADDLE_DIMENSION_Y), paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 25));
        this.gameObjects().addGameObject(paddle);
    }

    private void addBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(PATH_TO_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(PATH_TO_BLOP_WAV);
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_DIMENSION, BALL_DIMENSION), ballImage, collisionSound);
        this.gameObjects().addGameObject(ball);
        this.ball = ball;
        setBall();
    }

    private void setBall() {
        float ballVelocityX = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean()) {
            ballVelocityX *= -1;
        }
        ball.setVelocity(new Vector2(ballVelocityX, BALL_SPEED));
        ball.setCenter(windowDimensions.mult(0.5f));
    }


    public static void main(String[] args) {
        BrickerGameManager brickerGameManager = new BrickerGameManager("Bricker", new Vector2(WINDOW_DIMENSION_X, WINDOW_DIMENSION_Y));
        brickerGameManager.run();
    }
}
