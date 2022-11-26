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


    public ReturnLifeStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                              GraphicLifeCounter graphicLifeCounter, NumericLifeCounter numericLifeCounter) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.graphicLifeCounter = graphicLifeCounter;
        this.numericLifeCounter = numericLifeCounter;
    }


    /**
     * @param thisObj  brick
     * @param otherObj ball
     * @param counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        addHeart(thisObj, otherObj);
    }

    private void addHeart(GameObject thisObj, GameObject otherObj) {
        Renderable heartImage = imageReader.readImage(PATH_TO_HEART_PNG, true);
        GameObject heart = new FlyingHeart(thisObj.getCenter(),
                new Vector2(BrickerGameManager.WIDGET_DIMENSION,
                        BrickerGameManager.WIDGET_DIMENSION), heartImage,
                getGameObjectCollection(), graphicLifeCounter, numericLifeCounter);
        getGameObjectCollection().addGameObject(heart);
        heart.setVelocity(new Vector2 (0, 100));
    }
}
