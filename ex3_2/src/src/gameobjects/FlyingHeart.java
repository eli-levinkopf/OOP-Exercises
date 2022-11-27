package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.BrickerGameManager;


public class FlyingHeart extends GameObject {

    private final GameObjectCollection gameObjectCollection;
    private final GraphicLifeCounter graphicLifeCounter;
    private final NumericLifeCounter numericLifeCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public FlyingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       GameObjectCollection gameObjectCollection,
                       GraphicLifeCounter graphicLifeCounter,
                       NumericLifeCounter numericLifeCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.graphicLifeCounter = graphicLifeCounter;
        this.numericLifeCounter = numericLifeCounter;
    }

    /**
     * Called on the first frame of a collision.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other)) {
            gameObjectCollection.removeGameObject(this);
            graphicLifeCounter.addGraphicLife();
            numericLifeCounter.addNumericLife();
        }

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
        if (getCenter().x() > BrickerGameManager.WINDOW_DIMENSION_X ||
                getCenter().y() > BrickerGameManager.WINDOW_DIMENSION_Y) {
            gameObjectCollection.removeGameObject(this);
        }
    }

    /**
     * Should this object be allowed to collide the specified other object.
     * If both this object returns true for the other, and the other returns true
     * for this one, the collisions may occur when they overlap, meaning that their
     * respective onCollisionEnter/onCollisionStay/onCollisionExit will be called.
     * Note that this assumes that both objects have been added to the same
     * GameObjectCollection, and that its handleCollisions() method is invoked.
     *
     * @param other The other GameObject.
     * @return true if the objects should collide. This does not guarantee a collision
     * would actually collide if they overlap, since the other object has to confirm
     * this one as well.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        super.shouldCollideWith(other);
        return other instanceof Paddle && !(other instanceof MockPaddle);
    }
}
