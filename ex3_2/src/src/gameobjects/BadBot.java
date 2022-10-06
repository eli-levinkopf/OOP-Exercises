package src.gameobjects;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static src.BrickerGameManager.BALL_SPEED;

public class BadBot extends Bot {

    private final GameObject objectToFollow;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param windowController
     * @param objectToFollow
     */
    public BadBot(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  WindowController windowController, GameObject objectToFollow) {
        super(topLeftCorner, dimensions, renderable, windowController);
        this.objectToFollow = objectToFollow;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDirection = Vector2.ZERO;

        if (objectToFollow.getCenter().y() > getTopLeftCorner().y()){
            if (objectToFollow.getCenter().x() < getCenter().x()){
                movementDirection = Vector2.LEFT;
            }
            if (objectToFollow.getCenter().x() > getCenter().x()){
                movementDirection = Vector2.RIGHT;
            }
            setVelocity((movementDirection.mult(objectToFollow.getVelocity().x())));
        }

        if (objectToFollow.getCenter().y() < getTopLeftCorner().y()){
            if (objectToFollow.getCenter().x() < getCenter().x()){
                movementDirection = Vector2.RIGHT;
            }
            if (objectToFollow.getCenter().x() > getCenter().x()){
                movementDirection = Vector2.LEFT;
            }
            setVelocity((movementDirection.mult(BALL_SPEED)));
        }
        checkEdges();
    }
}
