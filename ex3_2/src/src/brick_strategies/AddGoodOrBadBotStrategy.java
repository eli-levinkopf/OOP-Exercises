package src.brick_strategies;

import src.gameobjects.BadBot;
import src.gameobjects.GoodBot;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

import static src.BrickerGameManager.PADDLE_DIMENSION_X;
import static src.BrickerGameManager.PADDLE_DIMENSION_Y;

public class AddGoodOrBadBotStrategy extends RemoveBrickStrategyDecorator {


    private final ImageReader imageReader;
    private final WindowController windowController;

    /**
     * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
     * All strategy decorators should inherit from this class.
     *
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public AddGoodOrBadBotStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                                   WindowController windowController) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        addBot(thisObj, otherObj);
    }

    private void addBot(GameObject thisObj, GameObject otherObj) {
        Random random = new Random();
        Renderable botImage;
        GameObject bot;
        if (random.nextBoolean()){
            botImage = imageReader.readImage("assets/botGood.png", true);
            bot = new GoodBot(Vector2.ZERO, new Vector2(PADDLE_DIMENSION_X, PADDLE_DIMENSION_Y ), botImage, windowController, otherObj);
            bot.setCenter(new Vector2(otherObj.getCenter().x(), windowController.getWindowDimensions().y()*random.nextFloat(.4f ,.8f)));
            getGameObjectCollection().addGameObject(bot);
            return;
        }
        botImage = imageReader.readImage("assets/botBad.png", true);
        bot = new BadBot(Vector2.ZERO, new Vector2(PADDLE_DIMENSION_X, PADDLE_DIMENSION_Y ), botImage, windowController, otherObj);
        bot.setCenter(new Vector2(thisObj.getCenter().x(), windowController.getWindowDimensions().y()*random.nextFloat(.4f ,.8f)));
        getGameObjectCollection().addGameObject(bot);

    }
}
