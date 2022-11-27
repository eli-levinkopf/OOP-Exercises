package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * General type for brick strategies, part of decorator pattern implementation. All brick
 * strategies implement this interface.
 */
public interface CollisionStrategy {

    /**
     * @return reference to gameCollision.
     */
    GameObjectCollection getGameObjectCollection();

    /**
     * Called when brick collided with other object.
     *
     * @param thisObj  reference to Brick object.
     * @param otherObj reference to other type of object that collided with brick.
     * @param counter  global brick counter.
     */
    void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);
}
