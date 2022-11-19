package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class CollisionStrategy {

    private final GameObjectCollection gameObjectCollection;

    /**
     * Creates a new collision strategy instance.
     *
     * @param gameObjects An object which holds all game objects of the game running.
     */
    public CollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjectCollection = gameObjects;
    }

    /**
     * When a brick detects a collision, this method is called and activates the current strategy
     * . It decrements the number of active bricks on the screen and removes the current brick
     * from the screen.
     *
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter the number of active bricks.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        if(gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            bricksCounter.decrement();
        }
    }
}
