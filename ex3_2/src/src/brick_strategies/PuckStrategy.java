package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

import static src.BrickerGameManager.*;


public class PuckStrategy extends RemoveBrickStrategyDecorator {

    private static final int NUM_OF_BALLS = 3;
    public static final String PATH_TO_MOCK_BALL_PNG = "assets/mockBall.png";
    public static final float PUCK_BALL_SIZE_FACTOR = 1.3f;
    public static final int NUM_OF_DIRECTIONS = 4;
    public static final int ROTATE_BY_QUARTER = 90;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Random random = new Random();

    /**
     * Creates a new instance of a PuckStrategy.
     *
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader   an object used to read images from the disc and render them.
     * @param soundReader   reference to an object used to read sound files from the disc and
     *                      render them.
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                        SoundReader soundReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Called when brick collided with other object.
     *
     * @param thisObj  reference to Brick object.
     * @param otherObj reference to other type of object that collided with brick.
     * @param counter  global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        add3Balls(thisObj, otherObj);
    }

    /**
     * Adds 3 new puck balls.
     *
     * @param thisObj  reference to Brick object.
     * @param otherObj reference to other type of object that collided with brick.
     */
    private void add3Balls(GameObject thisObj, GameObject otherObj) {
        Renderable ballImage = imageReader.readImage(PATH_TO_MOCK_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(PATH_TO_BLOP_WAV);
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            GameObject mockBall = new Puck(thisObj.getCenter().add(new Vector2(BALL_DIMENSION / 2
                    , BALL_DIMENSION / 2)), new Vector2(BALL_DIMENSION * PUCK_BALL_SIZE_FACTOR,
                    BALL_DIMENSION * PUCK_BALL_SIZE_FACTOR), ballImage, collisionSound,
                    getGameObjectCollection());
            getGameObjectCollection().addGameObject(mockBall);
            setBall(otherObj, mockBall);
        }
    }

    /**
     * Initializes the new puck ball.
     *
     * @param mainBall reference to the main ball.
     * @param mockBall reference to the new puck ball.
     */
    private void setBall(GameObject mainBall, GameObject mockBall) {
        float degrees = (float) random.nextInt(NUM_OF_DIRECTIONS) * ROTATE_BY_QUARTER;
        Vector2 ballVelocity = mainBall.getVelocity();
        ballVelocity = ballVelocity.rotated(degrees);
        mockBall.setVelocity(ballVelocity);
    }
}
