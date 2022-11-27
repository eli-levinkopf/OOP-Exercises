package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {

    protected CollisionStrategy toBeDecorated;

    /**
     * Abstract decorator to add functionality to the remove brick strategy, following the
     * decorator pattern.
     * All strategy decorators should inherit from this class.
     *
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated) {
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * @return a reference to the ObjectCollection.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
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
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }
}
