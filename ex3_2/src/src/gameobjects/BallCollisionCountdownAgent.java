package src.gameobjects;

import danogl.GameObject;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * An object of this class is instantiated on collision of ball with a brick with a change camera
 * strategy.
 * It checks ball's collision counter every frame, and once it finds the ball has collided
 * countDownValue times
 * since instantiation, it calls the strategy to reset the camera to normal.
 */
public class BallCollisionCountdownAgent extends GameObject {

    private final Ball ball;
    private final ChangeCameraStrategy owner;
    private final int countDownValue;
    private final int countInStart;

    /**
     * Construct a new GameObject instance.
     *
     * @param ball           Ball object whose collisions are to be counted.
     * @param owner          Object asking for countdown notification.
     * @param countDownValue Number of ball collisions. Notify caller object that the ball
     *                       collided countDownValue
     *                       times since instantiation.
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue) {
        super(ball.getTopLeftCorner(), ball.getDimensions(), null);
        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
        countInStart = ball.getCollisionCount();
    }

    /**
     * This method is overwritten from GameObject. If right and/or left key is recognised as
     * pressed by the input listener, it moves the paddle, and check that it doesn't move past
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ball.getCollisionCount() >= countInStart + countDownValue) {
            owner.turnOffCameraChange();
        }
    }
}
