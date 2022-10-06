package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {
    private final TextRenderable textRenderable;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable);
        this.textRenderable = (TextRenderable) renderable;
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(Integer.toString(livesCounter.value()));
    }
}
