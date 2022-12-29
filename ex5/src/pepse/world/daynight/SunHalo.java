package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {

    public static final int SUN_HALO_DIAMETER = 200;
    public static final String SUN_HALO_TAG = "sunHalo";

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
