package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.*;

import static src.BrickerGameManager.PATH_TO_HEART_PNG;

public class ReturnLifeStrategy extends RemoveBrickStrategyDecorator {


    private final ImageReader imageReader;
    private final GraphicLifeCounter graphicLifeCounter;
    private final NumericLifeCounter numericLifeCounter;

    /**
     * Creates a new instance of ReturnLifeStrategy.
     *
     * @param toBeDecorated      Collision strategy object to be decorated.
     * @param imageReader        an object used to read images from the disc and render them.
     * @param graphicLifeCounter reference to graphicLifeCounter.
     * @param numericLifeCounter reference to numericLifeCounter.
     */
    public ReturnLifeStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                              GraphicLifeCounter graphicLifeCounter,
                              NumericLifeCounter numericLifeCounter) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.graphicLifeCounter = graphicLifeCounter;
        this.numericLifeCounter = numericLifeCounter;
    }


    /**
     * Called when brick collided with other object.
     *
     * @param thisObj  reference to Brick object.
     * @param otherObj reference to other type of object that collided with brick.
     * @param counter  global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        addHeart(thisObj, otherObj);
    }

    /**
     * Adds a new puck heart and set it velocity.
     *
     * @param thisObj  reference to Brick object.
     * @param otherObj reference to other type of object that collided with brick.
     */
    private void addHeart(GameObject thisObj, GameObject otherObj) {
        Renderable heartImage = imageReader.readImage(PATH_TO_HEART_PNG, true);
        GameObject heart = new FlyingHeart(thisObj.getCenter(),
                new Vector2(BrickerGameManager.WIDGET_DIMENSION,
                        BrickerGameManager.WIDGET_DIMENSION), heartImage,
                getGameObjectCollection(), graphicLifeCounter, numericLifeCounter);
        getGameObjectCollection().addGameObject(heart);
        heart.setVelocity(new Vector2(0, 100));
    }
}
