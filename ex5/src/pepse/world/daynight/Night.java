package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

/*
  The Night class represents the night sky in the game. It creates a GameObject with a
  RectangleRenderable that represents the night sky and has an opacity transition that cycles
  between MIDDAY_OPACITY and MIDNIGHT_OPACITY over a given length of time.
 */
import static danogl.components.Transition.TransitionType.TRANSITION_BACK_AND_FORTH;

public class Night {

    private static final Float MIDNIGHT_OPACITY = 0.5f;
    public static final float MIDDAY_OPACITY = 0f;

    /**
     * Creates a new Night object and adds it to the given game object collection. The Night object
     * will have an opacity transition that cycles between MIDDAY_OPACITY and MIDNIGHT_OPACITY over
     * the given length of time.
     *
     * @param gameObjects      The game object collection to which the Night object will be added.
     * @param layer            The layer in the game object collection to which the Night object will be added.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength      The length of time in seconds over which the Night object's opacity will
     *                         transition between MIDDAY_OPACITY and MIDNIGHT_OPACITY.
     * @return The newly created Night object.
     */
    public static GameObject create(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions,
                                    float cycleLength) {
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(ColorSupplier.approximateColor(Color.black)));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(night, layer);
        new Transition<>(night, night.renderer()::setOpaqueness, MIDDAY_OPACITY, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength / 2, TRANSITION_BACK_AND_FORTH, null);

        return night;
    }
}
