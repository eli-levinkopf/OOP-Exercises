package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import src.BrickerGameManager;

public class DoubleStrategy implements CollisionStrategy{

    private final CollisionStrategy strategy1;
    private final CollisionStrategy strategy2;
    private boolean recursivelyDoubleStrategy;

    public DoubleStrategy(){
        strategy1 = BrickerGameManager.brickStrategyFactory.getStrategy(true);
        strategy2 = BrickerGameManager.brickStrategyFactory.getStrategy(true);
    }

//    public DoubleStrategy(GameObjectCollection gameObjects, SoundReader soundReader,
//                          ImageReader imageReader, UserInputListener inputListener, WindowController windowController,
//                          BrickerGameManager gameManager){
//        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects, gameManager, imageReader,
//                soundReader, inputListener, windowController, windowController.getWindowDimensions(),true);
//        strategy1 =  brickStrategyFactory.getStrategy();
//        strategy2 = brickStrategyFactory.getStrategy();
//    }

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
