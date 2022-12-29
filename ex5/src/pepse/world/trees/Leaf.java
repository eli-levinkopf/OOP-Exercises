package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.Random;

public class Leaf extends GameObject {

    public static final String LEAF_TAG = "leaf";
    private final Vector2 topLeftCorner;
    private final Renderable renderable;
    private final GameObjectCollection gameObjects;
    private final int layer;
    private final Random random;
    private Transition<Vector2> horizontalTransition;
    private final int lifeTimeOut;


    public Leaf(Vector2 topLeftCorner, Renderable renderable, GameObjectCollection gameObjects, int layer, Random random) {
        super(topLeftCorner, Vector2.ONES.mult(Block.SIZE), renderable);
        this.topLeftCorner = topLeftCorner;
        this.renderable = renderable;
        this.gameObjects = gameObjects;
        this.layer = layer;
        this.random = random;
        lifeTimeOut = random.nextInt(150);
        initializeLeaf();
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.removeComponent(horizontalTransition);
        this.setVelocity(Vector2.ZERO);
    }

    private void initializeLeaf() {
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(0f);
        shake();
        dropout();
        fadeOut ();
        setTag(LEAF_TAG);
        gameObjects.addGameObject(this, layer);
    }

    private void fadeOut() {
        new ScheduledTask(this, lifeTimeOut, false, ()->renderer().fadeOut(20, this::returnLeafToTree));
    }

    private void dropout() {
        new ScheduledTask(this, lifeTimeOut, false, this::fallFromTree);
    }

    private void fallFromTree() {
        horizontalTransition = new Transition<>(this, this::setVelocity, new Vector2(-30, 30), new Vector2(30, 30),
                Transition.LINEAR_INTERPOLATOR_VECTOR, 5, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    private void returnLeafToTree() {
        gameObjects.removeGameObject(this);
        new ScheduledTask(this, random.nextInt(5), false,
                () -> new Leaf(topLeftCorner, renderable, gameObjects, layer, this.random));
    }


    private void shake() {
        new ScheduledTask(this, random.nextInt(5), false, this::applyShake);
    }

    private void applyShake() {
        // change angle
        new Transition<>(this, this.renderer()::setRenderableAngle,
                random.nextInt(15) + 5f, -(random.nextInt(15) + 5f),
                Transition.CUBIC_INTERPOLATOR_FLOAT, 2, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        // change size
        new Transition<>(this, this::setDimensions, new Vector2(random.nextInt(5) + 25,
                random.nextInt(5) + 25), new Vector2(Block.SIZE, Block.SIZE), Transition.CUBIC_INTERPOLATOR_VECTOR,
                2, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }
}