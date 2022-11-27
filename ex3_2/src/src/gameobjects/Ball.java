package src.gameobjects;

import src.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static src.BrickerGameManager.WINDOW_DIMENSION_X;

public class Ball extends GameObject {

    private final Sound collisionSound;
    private final Counter ballCounter = new Counter(0);

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound sound that will be played when the object is colliding with other
     *                       object.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Override this method to remove the ball from the if it out of frame.
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
        if (getCenter().x() > WINDOW_DIMENSION_X) {
            setCenter(new Vector2(WINDOW_DIMENSION_X - 30, getCenter().y()));
        }
        if (getCenter().x() < 0) {
            setCenter(new Vector2(30, getCenter().y()));
        }
    }

    /**
     * This method overwrites the OnCollisionEnter of GameObject. When it collides with another
     * object, it flips its direction.
     *
     * @param other     the object that the ball collided with.
     * @param collision the collision parameters.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        //TODO: instanceof?
        if (other instanceof Ball || other instanceof Brick || other instanceof Paddle) {
            ballCounter.increment();
        }
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
    }

    /**
     * @return the number of collisions that have been created with this object.
     */
    public int getCollisionCount() {
        return ballCounter.value();
    }
}
