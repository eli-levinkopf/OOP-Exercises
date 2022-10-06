package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjectCollection;

    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        if(gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            bricksCounter.decrement();
        }
    }

}
