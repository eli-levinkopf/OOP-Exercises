package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static bricker.gameobjects.Paddle.MIN_DISTANCE_FROM_SCREEN_EDGE;

public abstract class Bot extends GameObject {

    private final WindowController windowController;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Bot(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, WindowController windowController) {
        super(topLeftCorner, dimensions, renderable);
        this.windowController = windowController;
    }

    protected void checkEdges() {
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE ) {
            setTopLeftCorner(new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE, getTopLeftCorner().y()));
        }
        if (getTopLeftCorner().x() > windowController.getWindowDimensions().x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x()) {
            setTopLeftCorner(new Vector2(windowController.getWindowDimensions().x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x(), getTopLeftCorner().y()));
        }
    }
}