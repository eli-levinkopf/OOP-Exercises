package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {

    private Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;
    private int numOfLives;

    /**
     * Construct a new GameObject instance.
     *
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions    Width and height in window coordinates.
     * @param widgetRenderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param livesCounter - global lives counter of game.
     * @param gameObjectsCollection - global game object collection managed by game manager.
     * @param numOfLives - global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter, Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection, int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);

        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
