package bricker.brick_strategies;

import bricker.collision.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ChangeCameraStrategy extends RemoveBrickStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;
    private final WindowController windowController;

    public ChangeCameraStrategy(GameObjectCollection gameObjects, Counter bricksCounter, BrickerGameManager gameManager,
                                WindowController windowController) {
        super(gameObjects, bricksCounter);
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (gameManager.getCamera() == null){
            gameManager.setCamera(new Camera(otherObj, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f), windowController.getWindowDimensions()));
            ((Ball)otherObj).getCounter().reset();
        }
    }
}
