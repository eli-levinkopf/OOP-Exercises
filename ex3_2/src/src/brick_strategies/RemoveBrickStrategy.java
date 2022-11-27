package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjectCollection;

    /**
     * Construct a new RemoveBrickStrategy instance.
     *
     * @param gameObjectCollection reference to the game collection instance.
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * @return a reference to the ObjectCollection.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    /**
     * Called when brick collided with other object.
     *
     * @param thisObj       reference to Brick object.
     * @param otherObj      reference to other type of object that collided with brick.
     * @param bricksCounter global brick counter.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        if (gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)) {
            bricksCounter.decrement();
        }
    }

}
