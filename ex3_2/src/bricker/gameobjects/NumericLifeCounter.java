package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {
    private final TextRenderable textRenderable;
    private final Counter livesCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter livesCounter,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable);
        this.textRenderable = (TextRenderable) renderable;
        this.livesCounter = livesCounter;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(Integer.toString(livesCounter.value()));
    }
}
