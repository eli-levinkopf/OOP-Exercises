package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.BrickerGameManager.*;


public class SplitBrickTo3BallsStrategy extends RemoveBrickStrategy implements CollisionStrategy {

    private static final int NUM_OF_BALLS = 3;
    public static final float TWENTY_PERCENT_SLOWER = .8f;
    public static final float TWENTY_PERCENT_FASTER = 1.2f;
    public static final int ROTATE_LEFT = -180;
    public static final int ROTATE_RIGHT = 180;
    public static final String PATH_TO_MOCK_BALL_PNG = "assets/mockBall.png";
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Random random = new Random();

    public SplitBrickTo3BallsStrategy(GameObjectCollection gameObjects, Counter bricksCounter,
                                      ImageReader imageReader, SoundReader soundReader) {
        super(gameObjects, bricksCounter);
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        add3Balls(thisObj, otherObj);
    }

    private void add3Balls(GameObject thisObj, GameObject otherObj) {
        Renderable ballImage = imageReader.readImage(PATH_TO_MOCK_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(PATH_TO_BLOP_WAV);
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            GameObject mockBall = new Ball(thisObj.getCenter().add(new Vector2(BALL_DIMENSION / 2, BALL_DIMENSION / 2)),
                    new Vector2(BALL_DIMENSION, BALL_DIMENSION), ballImage, collisionSound);
            gameObjects.addGameObject(mockBall);
            setBall(otherObj, mockBall);
        }
    }

    private void setBall(GameObject mainBall, GameObject mockBall) {
        Vector2 ballVelocity = mainBall.getVelocity().mult(random.nextFloat(TWENTY_PERCENT_SLOWER, TWENTY_PERCENT_FASTER));
        ballVelocity = ballVelocity.rotated(random.nextFloat(ROTATE_LEFT, ROTATE_RIGHT));
        mockBall.setVelocity(ballVelocity);
    }
}
