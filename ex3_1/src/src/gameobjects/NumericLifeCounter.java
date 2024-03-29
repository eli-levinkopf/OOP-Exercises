package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    private final TextRenderable textRenderable;
    private final Counter livesCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, new TextRenderable(Integer.toString(livesCounter.value()),
                Font.DIALOG_INPUT, true, true));
        this.textRenderable = (TextRenderable) super.renderer().getRenderable();
        this.livesCounter = livesCounter;
    }

    /**
     * This method is overwritten from GameObject. It sets the string value of the text object to
     * the number of current lives left.
     *
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(Integer.toString(livesCounter.value()));
        switch (livesCounter.value()) {
            case 1:
                textRenderable.setColor(Color.red);
                break;
            case 2:
                textRenderable.setColor(Color.yellow);
                break;
            case 3:
                textRenderable.setColor(Color.green);
        }
    }
}
