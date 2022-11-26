package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    static final float MOVEMENT_SPEED = 500f;
    public static final int SAFE_SPACE_FROM_EDGE = 10;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistanceFromEdge;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener    The input listener which waits for user inputs and acts on them.
     * @param windowDimensions The dimensions of the screen, to know the limits for paddle
     *                         movements.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions,
                  int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * This method is overwritten from GameObject. If right and/or left key is recognised as
     * pressed by the input listener, it moves the paddle, and check that it doesn't move past
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
        Vector2 movementDirection = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDirection = movementDirection.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDirection = movementDirection.add(Vector2.RIGHT);
        }
        setVelocity(movementDirection.mult(MOVEMENT_SPEED));
        if (getTopLeftCorner().x() < minDistanceFromEdge + SAFE_SPACE_FROM_EDGE) {
            setTopLeftCorner(new Vector2(minDistanceFromEdge + SAFE_SPACE_FROM_EDGE,
                    getTopLeftCorner().y()));
        }
        if (getTopLeftCorner().x() > windowDimensions.x() -
                minDistanceFromEdge - getDimensions().x() - SAFE_SPACE_FROM_EDGE) {
            setTopLeftCorner(new Vector2(windowDimensions.x() - minDistanceFromEdge -
                    getDimensions().x() - SAFE_SPACE_FROM_EDGE, getTopLeftCorner().y()));
        }
    }
    

}
