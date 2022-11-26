package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class DoubleStrategy implements CollisionStrategy{

    private final CollisionStrategy strategy1;
    private final CollisionStrategy strategy2;

    public DoubleStrategy(CollisionStrategy strategy1, CollisionStrategy strategy2){
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }


    @Override
    public GameObjectCollection getGameObjectCollection() {
        return strategy1.getGameObjectCollection();
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        strategy1.onCollision(thisObj, otherObj, counter);
        strategy2.onCollision(thisObj, otherObj, counter);
    }
}
