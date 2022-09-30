package bricker.paddle_strategies;

import bricker.collision.CollisionStrategy;
import danogl.collisions.GameObjectCollection;

public class PaddleStrategyFactory {

    private static GameObjectCollection gameObjects;

    public PaddleStrategyFactory(GameObjectCollection gameObjects){
        PaddleStrategyFactory.gameObjects = gameObjects;
    }

    public CollisionStrategy getStrategy(){
        return new PaddleDisappearsAfter3CollisionsStrategy(gameObjects);
    }
}
