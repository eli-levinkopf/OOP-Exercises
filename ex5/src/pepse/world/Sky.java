package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 The Sky class is responsible for creating and rendering the sky in the game.
 */
public class Sky{

    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    public static final String SKY_TAG = "sky";

    /**
     Creates a new sky GameObject and adds it to the given GameObjectCollection.
     The sky will cover the entire window and will be rendered on the specified layer.
     @param gameObjects the GameObjectCollection to add the sky to
     @param windowDimensions the dimensions of the window, in pixels
     @param skyLater the layer to render the sky on
     @return the newly created sky GameObject
     */
    public static GameObject create(GameObjectCollection gameObjects, Vector2 windowDimensions, int skyLater){
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions, new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sky, skyLater);
        sky.setTag(SKY_TAG);
        return sky;
    }
}
