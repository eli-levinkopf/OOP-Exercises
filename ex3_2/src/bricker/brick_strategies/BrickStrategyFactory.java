package bricker.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.util.Counter;

public class BrickStrategyFactory {

    private static GameObjectCollection gameObjects;
    private static Counter bricksCounter;
    private static SoundReader soundReader;
    private static ImageReader imageReader;
    private static int i = 0;

    public BrickStrategyFactory(GameObjectCollection gameObjects, Counter bricksCounter, SoundReader soundReader,
                                ImageReader imageReader){
        BrickStrategyFactory.gameObjects = gameObjects;
        BrickStrategyFactory.bricksCounter = bricksCounter;
        BrickStrategyFactory.soundReader = soundReader;
        BrickStrategyFactory.imageReader = imageReader;
    }

    public CollisionStrategy getStrategy(){
        if (i % 19 == 0){
            ++i;
            return new SplitBrickTo3BallsStrategy(gameObjects, bricksCounter, imageReader, soundReader);
        }
        ++i;
        return new RemoveBrickStrategy(gameObjects, bricksCounter);
    }
}
