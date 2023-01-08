package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;


/**
 * The SunHalo class represents the halo around the sun in the game. It creates a GameObject with
 * an OvalRenderable that represents the halo and has a component that keeps it centered on the sun.
 */
public class SunHalo {

    public static final int SUN_HALO_DIAMETER = 200;
    public static final String SUN_HALO_TAG = "sunHalo";


    /**
     * Creates a new SunHalo object and adds it to the given game object collection. The SunHalo
     * object will have a component that keeps it centered on the given sun object.
     *
     * @param gameObjects The game object collection to which the SunHalo object will be added.
     * @param layer       The layer in the game object collection to which the SunHalo object will be
     *                    added.
     * @param sun         The sun object on which the SunHalo object will be centered.
     * @param color       The color of the SunHalo object.
     * @return The newly created SunHalo object.
     */
    public static GameObject create(GameObjectCollection gameObjects, int layer, GameObject sun, Color color) {
        GameObject sunHalo = new GameObject(Vector2.ZERO, new Vector2(SUN_HALO_DIAMETER, SUN_HALO_DIAMETER),
                new OvalRenderable(color));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO_TAG);
        sunHalo.setCenter(sun.getCenter());
        sunHalo.addComponent(update -> sunHalo.setCenter(sun.getCenter()));
        gameObjects.addGameObject(sunHalo, layer);
        return sunHalo;
    }
}
