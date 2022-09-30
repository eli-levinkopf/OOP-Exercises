package bricker.paddle_strategies;

import bricker.collision.CollisionStrategy;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class PaddleDisappearsAfter3CollisionsStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjects;

    public PaddleDisappearsAfter3CollisionsStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        //thisObj = paddle, otherObj = ball
        Paddle tmpPaddle = (Paddle) thisObj;
        if (tmpPaddle.isMainPaddle()){
            return;
        }
        tmpPaddle.getCounter().increment();
        if (tmpPaddle.getCounter().value() == 3){
            gameObjects.removeGameObject(thisObj);
        }
    }
}
