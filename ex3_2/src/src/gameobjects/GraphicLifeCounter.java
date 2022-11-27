package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;


public class GraphicLifeCounter extends GameObject {

    public static final int X_DIR = 0;
    private Vector2 widgetTopLeftCorner;
    private final Renderable widgetRenderable;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectsCollection;
    private final GameObject[] livesWidgets;
    private int numOfWidgets = 0;

    /**
     * This is the constructor for the graphic lives counter. It creates a 0x0 sized object (to
     * be able to call its update method in game), Creates numOfLives hearts, and adds them to
     * the game.
     *
     * @param widgetTopLeftCorner   Position of the object, in window coordinates (pixels).
     *                              Note that (0,0) is the top-left corner of the window.
     * @param widgetRenderable      The renderable representing the object. Can be null, in which
     *                              case
     *                              the GameObject will not be rendered.
     * @param livesCounter          - global lives counter of game.
     * @param gameObjectsCollection - global game object collection managed by game manager.
     * @param numOfLives            - global setting of number of lives a player will have in a
     *                              game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection, int numOfLives) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.widgetRenderable = widgetRenderable;
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        livesWidgets = new GameObject[numOfLives + 1];

        GameObject heart;
        for (int i = 0; i < numOfLives; i++) {
            heart = new GameObject(this.widgetTopLeftCorner,
                    new Vector2(BrickerGameManager.WIDGET_DIMENSION,
                            BrickerGameManager.WIDGET_DIMENSION), widgetRenderable);
            gameObjectsCollection.addGameObject(heart, BrickerGameManager.LIFE_WIDGETS_LAYER);
            livesWidgets[i] = heart;
            numOfWidgets++;
            this.widgetTopLeftCorner =
                    this.widgetTopLeftCorner.add(new Vector2(BrickerGameManager.WIDGET_DIMENSION
                            + BrickerGameManager.SPACE_BETWEEN_WIDGETS, X_DIR));
        }
    }

    /**
     * This method is overwritten from GameObject It removes hearts from the screen if there are
     * more hearts than there are lives left.
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
        if (numOfWidgets > livesCounter.value()) {
            gameObjectsCollection.removeGameObject(livesWidgets[livesCounter.value()],
                    BrickerGameManager.LIFE_WIDGETS_LAYER);
            numOfWidgets--;
            widgetTopLeftCorner =
                    widgetTopLeftCorner.subtract(new Vector2(BrickerGameManager.WIDGET_DIMENSION
                            + BrickerGameManager.SPACE_BETWEEN_WIDGETS, X_DIR));
        }
    }

    /**
     * Adds a new graphic life to the game if number of lives is not greater than MAX_NUM_OF_LIVES.
     */
    public void addGraphicLife() {
        if (livesCounter.value() < BrickerGameManager.MAX_NUM_OF_LIVES) {
            GameObject heart = new GameObject(widgetTopLeftCorner,
                    new Vector2(BrickerGameManager.WIDGET_DIMENSION,
                            BrickerGameManager.WIDGET_DIMENSION), widgetRenderable);
            gameObjectsCollection.addGameObject(heart, BrickerGameManager.LIFE_WIDGETS_LAYER);
            livesWidgets[livesCounter.value()] = heart;
            numOfWidgets++;
            widgetTopLeftCorner =
                    widgetTopLeftCorner.add(new Vector2(BrickerGameManager.WIDGET_DIMENSION
                            + BrickerGameManager.SPACE_BETWEEN_WIDGETS, X_DIR));
        }
    }
}