package src.brick_strategies;

import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import src.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.util.Counter;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {

    public static final int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;
    private final BrickerGameManager gameManager;
    private final WindowController windowController;
    private BallCollisionCountdownAgent ballCollisionCountdownAgent;

    /**
     * Abstract decorator to add functionality to the remove brick strategy, following the
     * decorator pattern.
     * All strategy decorators should inherit from this class.
     *
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                WindowController windowController, BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
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
        if (gameManager.getCamera() == null && (otherObj instanceof Ball && !(otherObj instanceof Puck))) {
            ballCollisionCountdownAgent = new BallCollisionCountdownAgent((Ball) otherObj, this,
                    NUM_BALL_COLLISIONS_TO_TURN_OFF);
            getGameObjectCollection().addGameObject(ballCollisionCountdownAgent);
            gameManager.setCamera(new Camera(otherObj, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));
        }
    }

    /**
     * return the camera to defuel state.
     */
    public void turnOffCameraChange() {
        gameManager.setCamera(null);
        getGameObjectCollection().removeGameObject(ballCollisionCountdownAgent);
    }
}
