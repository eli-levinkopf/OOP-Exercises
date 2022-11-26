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
                       GraphicLifeCounter graphicLifeCounter, NumericLifeCounter numericLifeCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.graphicLifeCounter = graphicLifeCounter;
        this.numericLifeCounter = numericLifeCounter;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other)){
            gameObjectCollection.removeGameObject(this);
            graphicLifeCounter.addGraphicLife();
            numericLifeCounter.addNumericLife();
        }

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getCenter().x() > BrickerGameManager.WINDOW_DIMENSION_X
                || getCenter().y() > BrickerGameManager.WINDOW_DIMENSION_Y) {
            gameObjectCollection.removeGameObject(this);
        }
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        super.shouldCollideWith(other);
        return other instanceof Paddle && !(other instanceof MockPaddle);
    }
}
