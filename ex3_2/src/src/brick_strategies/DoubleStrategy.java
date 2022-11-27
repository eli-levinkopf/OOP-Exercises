package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class DoubleStrategy implements CollisionStrategy{

    private final CollisionStrategy strategy1;
    private final CollisionStrategy strategy2;

    /**
     * Construct a new double strategy instance.
     * @param strategy1 reference to the first strategy.
     * @param strategy2 reference to the second strategy.
     */
    public DoubleStrategy(CollisionStrategy strategy1, CollisionStrategy strategy2){
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }


    /**
     * @return a reference to the ObjectCollection.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return strategy1.getGameObjectCollection();
    }

    /**
     * Called when brick collided with other object.
     * @param thisObj  reference to Brick object.
     * @param otherObj reference to other type of object that collided with brick.
     * @param counter  global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        strategy1.onCollision(thisObj, otherObj, counter);
        strategy2.onCollision(thisObj, otherObj, counter);
    }
}
