package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final int MOVE_SPEED = 500;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 5;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistFromEdge;

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
     * @param minDistFromEdge  Minimum distance allowed for the paddle from the edge of the walls
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
    }

    /**
     * This method is overwritten from GameObject. If right and/or left key is recognised as
     * pressed by the input listener, it moves the paddle, and check that it doesn't move past
     * the borders.
     *
     * @param deltaTime
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
        setVelocity(movementDirection.mult(MOVE_SPEED));
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            setTopLeftCorner(new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE, getTopLeftCorner().y()));
        }
        if (getTopLeftCorner().x() > windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE -
                getDimensions().x()) {
            setTopLeftCorner(new Vector2(windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE -
                    getDimensions().x(), getTopLeftCorner().y()));
        }
    }
}
