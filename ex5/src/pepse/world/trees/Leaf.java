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


/**
 * The Leaf class represents a leaf in a game. It extends the GameObject class and has several methods
 * that control its behavior, such as shaking, falling from the tree, and fading out.
 */
public class Leaf extends GameObject {

    public static final String LEAF_TAG = "leaf";
    public static final int LEAF_LIFE_TIME = 150;
    public static final int FADE_OUT_TIME = 30;
    public static final int LEAF_FALL_FROM_TREE_FACTOR = 30;
    public static final int FALL_FROM_TREE_TRANSITION_TIME = 5;
    public static final int LEAF_TIME_TO_RETURN = 10;
    public static final int START_TO_SHAKE_FACTOR = 5;
    public static final int TRANSITION_FACTOR = 25;
    private final Vector2 topLeftCorner;
    private final Renderable renderable;
    private final GameObjectCollection gameObjects;
    private final int layer;
    private final Random random;
    private Transition<Vector2> horizontalTransition;
    private final int lifeTimeOut;


    /**
     * Constructs a new Leaf object at the specified position with the given renderable, game object collection, layer,
     * and random object.
     *
     * @param topLeftCorner the top left corner position of the leaf
     * @param renderable    the renderable object to use for rendering the leaf
     * @param gameObjects   the game object collection to add the leaf to
     * @param layer         the layer to add the leaf to in the game object collection
     * @param random        the random object to use for generating random values
     */
    public Leaf(Vector2 topLeftCorner, Renderable renderable, GameObjectCollection gameObjects, int layer, Random random) {
        super(topLeftCorner, Vector2.ONES.mult(Block.SIZE), renderable);
        this.topLeftCorner = topLeftCorner;
        this.renderable = renderable;
        this.gameObjects = gameObjects;
        this.layer = layer;
        this.random = random;
        lifeTimeOut = random.nextInt(LEAF_LIFE_TIME);
        initializeLeaf();
    }

    /**
     * Called when this leaf object collides with another game object.
     * Removes the horizontal transition component and sets the velocity to zero.
     *
     * @param other     the other game object that this leaf object collided with
     * @param collision the collision object representing the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.removeComponent(horizontalTransition);
        this.setVelocity(Vector2.ZERO);
    }

    /**
     * Initializes the leaf object by setting its physics properties, tag, and adding it to the game object collection.
     * Also starts the shaking, dropout, and fade out tasks.
     */
    private void initializeLeaf() {
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(0f);
        shake();
        dropout();
        fadeOut();
        setTag(LEAF_TAG);
        gameObjects.addGameObject(this, layer);
    }

    /**
     * Schedules a task to start the fade out process for this leaf object after a random amount of
     * time between 0 and LEAF_LIFE_TIME.
     * When the fade out process is complete, the {@code returnLeafToTree} method is called.
     */
    private void fadeOut() {
        new ScheduledTask(this, lifeTimeOut, false,
                () -> renderer().fadeOut(FADE_OUT_TIME, this::returnLeafToTree));
    }

    /**
     * Schedules a task to start the dropout process for this leaf object after a random amount of time
     * between 0 and LEAF_LIFE_TIME.
     * When the dropout process is complete, the fallFromTree method is called.
     */
    private void dropout() {
        new ScheduledTask(this, lifeTimeOut, false, this::fallFromTree);
    }

    /**
     * Starts the fall from tree process for this leaf object by creating a new horizontal transition component that
     * moves the leaf object
     * back and forth horizontally.
     */
    private void fallFromTree() {
        horizontalTransition = new Transition<>(this, this::setVelocity,
                new Vector2(-LEAF_FALL_FROM_TREE_FACTOR, LEAF_FALL_FROM_TREE_FACTOR),
                new Vector2(LEAF_FALL_FROM_TREE_FACTOR, LEAF_FALL_FROM_TREE_FACTOR),
                Transition.LINEAR_INTERPOLATOR_VECTOR, FALL_FROM_TREE_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    /**
     * Returns the leaf object to the tree by removing it from the game object collection,
     * and scheduling a task to create a new leaf object
     * at the same position with a random delay between 0 and LEAF_TIME_TO_RETURN.
     */
    private void returnLeafToTree() {
        gameObjects.removeGameObject(this);
        new ScheduledTask(this, random.nextInt(LEAF_TIME_TO_RETURN), false,
                () -> new Leaf(topLeftCorner, renderable, gameObjects, layer, this.random));
    }

    /**
     * Schedules a task to start the shaking process for this leaf object after a random amount of time between
     * 0 and START_TO_SHAKE_FACTOR.
     * When the shaking process is complete, the applyShake method is called.
     */
    private void shake() {
        new ScheduledTask(this, random.nextInt(START_TO_SHAKE_FACTOR), false, this::applyShake);
    }

    /**
     * Applies the shaking effect to this leaf object by creating a new transition component that changes
     * the leaf object's.
     */
    private void applyShake() {
        // change angle
        new Transition<>(this, this.renderer()::setRenderableAngle,
                random.nextInt(15) + 5f, -(random.nextInt(15) + 5f),
                Transition.CUBIC_INTERPOLATOR_FLOAT, 2, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        // change size
        new Transition<>(this, this::setDimensions, new Vector2(random.nextInt(5) + TRANSITION_FACTOR,
                random.nextInt(5) + TRANSITION_FACTOR), new Vector2(Block.SIZE, Block.SIZE),
                Transition.CUBIC_INTERPOLATOR_VECTOR, 2, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }
}