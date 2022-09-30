package bricker.brick_strategies;

import bricker.collision.CollisionStrategy;
import bricker.gameobjects.BadBot;
import bricker.gameobjects.GoodBot;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.BrickerGameManager.PADDLE_DIMENSION_X;
import static bricker.main.BrickerGameManager.PADDLE_DIMENSION_Y;

public class AddGoodOrBadBot extends RemoveBrickStrategy implements CollisionStrategy {

    private final ImageReader imageReader;
    private final WindowController windowController;
    GameObjectCollection gameObjects;

    public AddGoodOrBadBot(GameObjectCollection gameObjects, Counter bricksCounter, ImageReader imageReader,
                           WindowController windowController) {
        super(gameObjects, bricksCounter);
        this.imageReader = imageReader;
        this.windowController = windowController;
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
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
            gameObjects.addGameObject(bot);
            return;
        }
        botImage = imageReader.readImage("assets/botBad.png", true);
        bot = new BadBot(Vector2.ZERO, new Vector2(PADDLE_DIMENSION_X, PADDLE_DIMENSION_Y ), botImage, windowController, otherObj);
        bot.setCenter(new Vector2(thisObj.getCenter().x(), windowController.getWindowDimensions().y()*random.nextFloat(.4f ,.8f)));
        gameObjects.addGameObject(bot);

    }
}
