package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;


/**
 * The Sun class represents the sun in the game. It creates a GameObject with an OvalRenderable
 * that represents the sun and has a transition that moves the sun in an elliptical path over a
 * given length of time.
 */
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

    /**
     * Creates a new Sun object and adds it to the given game object collection. The Sun object
     * will have a transition that moves it in an elliptical path over the given length of time.
     *
     * @param gameObjects      The game object collection to which the Sun object will be added.
     * @param layer            The layer in the game object collection to which the Sun object will be added.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength      The length of time in seconds over which the Sun object will move in an
     *                         elliptical path.
     * @return The newly created Sun object.
     */
    public static GameObject create(GameObjectCollection gameObjects, int layer,
                                    Vector2 windowDimensions, float cycleLength) {
        GameObject sun = new GameObject(new Vector2((windowDimensions.x() - SUN_DIAMETER) / TOP_LEFT_CORNER_FACTOR,
                (windowDimensions.y() - SUN_DIAMETER) / TOP_LEFT_CORNER_FACTOR),
                new Vector2(SUN_DIAMETER, SUN_DIAMETER), new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        gameObjects.addGameObject(sun, layer);
        Vector2 startPoint = sun.getCenter();
        new Transition<>(sun,
                theta -> sun.setCenter(startPoint.add(new Vector2((float) (-windowDimensions.x() / ELLIPTIC_FACTOR_X
                        * Math.cos((theta + PHASE) * TO_RAD)), (float) (windowDimensions.y() / ELLIPTIC_FACTOR_Y *
                        Math.sin((theta + PHASE) * TO_RAD))))),
                START_POINT, END_POINT, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }
}
