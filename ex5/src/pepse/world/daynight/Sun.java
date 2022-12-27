package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {

    public static final double TO_RAD = (Math.PI / 180);
    public static final int SUN_DIAMETER = 100;
    public static final float ELLIPTIC_FACTOR_X = 2.4f;
    public static final float ELLIPTIC_FACTOR_Y = 2.6f;
    public static final float START_POINT = 0f;
    public static final float END_POINT = 360f;
    public static final int PHASE = 250;
    public static final String SUN_TAG = "sun";
    public static final int TOP_LEFT_CORNER_FACTOR = 2;

    public static GameObject create(GameObjectCollection gameObjects, int layer,
                                    Vector2 windowDimensions, float cycleLength) {
        GameObject sun = new GameObject(new Vector2((windowDimensions.x()- SUN_DIAMETER)/ TOP_LEFT_CORNER_FACTOR,
                (windowDimensions.y()- SUN_DIAMETER) / TOP_LEFT_CORNER_FACTOR),
                new Vector2(SUN_DIAMETER, SUN_DIAMETER), new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        gameObjects.addGameObject(sun, layer);
        Vector2 startPoint = sun.getCenter();
        new Transition<>(sun,
                theta -> sun.setCenter(startPoint.add(new Vector2((float) (-windowDimensions.x()/ ELLIPTIC_FACTOR_X
                * Math.cos((theta + PHASE) * TO_RAD)), (float) (windowDimensions.y()/ ELLIPTIC_FACTOR_Y *
                        Math.sin((theta + PHASE) * TO_RAD))))),
                START_POINT, END_POINT, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }
}
