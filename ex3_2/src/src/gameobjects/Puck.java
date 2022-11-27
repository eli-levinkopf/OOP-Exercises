package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.BrickerGameManager;

public class Puck extends Ball {
    private final GameObjectCollection gameObjectCollection;

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
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.gameObjectCollection = gameObjectCollection;
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
        if (getCenter().x() > BrickerGameManager.WINDOW_DIMENSION_X || getCenter().y() > BrickerGameManager.WINDOW_DIMENSION_Y) {
            gameObjectCollection.removeGameObject(this);
        }
    }
}
