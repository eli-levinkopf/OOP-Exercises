package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;


/**
 * A class representing a trunk object in the game world.
 */
public class Trunk {

    public static final String TRUNK_TAG = "trunk";
    public static final Color TRUNK_COLOR = new Color(100, 50, 20);
    public static final int BOUND = 10;
    public static final int HEIGHT_FACTOR = 5;
    private int height;

    /**
     * Creates a new trunk object at the given x and y coordinates, with a random height within the bounds of
     * {@code BOUND} and {@code HEIGHT_FACTOR}, and adds it to the given game object collection at the given layer.
     *
     * @param xCoordinate the x coordinate to create the trunk at
     * @param yCoordinate the y coordinate to create the trunk at
     * @param gameObjects the game object collection to add the trunk to
     * @param random      the random object to use for generating random values
     * @param layer       the layer to add the
     */
    public void create(float xCoordinate, float yCoordinate, GameObjectCollection gameObjects, Random random,
                       int layer) {
        int trunkHeight = random.nextInt(BOUND) + HEIGHT_FACTOR;
        // convert to multiple of Block.SIZE
        int start = (int) (Math.floor(yCoordinate / Block.SIZE) * Block.SIZE);
        int end = (int) (Math.floor(yCoordinate - trunkHeight * Block.SIZE));
        int i;
        for (i = start; i > end; i -= Block.SIZE) {
            Block block = new Block(new Vector2(xCoordinate, i - Block.SIZE),
                    new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
            block.setTag(TRUNK_TAG);
            gameObjects.addGameObject(block, layer);
        }
        height = i - Block.SIZE;
    }

    /**
     * @return height of the trunk.
     */
    public int getHeight() {
        return height;
    }
}
