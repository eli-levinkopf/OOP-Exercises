package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;


/**
 * The Terrain class is responsible for generating and rendering the ground blocks in the game.
 */
public class Terrain {

    // Constants for the ground's color and tag
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    public static final double START_HEIGHT_FACTOR = 1.3;
    public static final int HEIGHT_FACTOR = 7;
    public static final String GROUND_TAG = "ground";

    // Fields for the ground blocks and their properties
    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private final Vector2 windowDimensions;
    private final NoiseGenerator noiseGenerator;

    /**
     * Construct a new Terrain instance.
     *
     * @param gameObjects      the GameObjectCollection to add the ground blocks to
     * @param groundLayer      the layer to render the ground blocks on
     * @param windowDimensions the dimensions of the window, in pixels
     * @param seed             the seed for the noise generator to use
     */
    public Terrain(GameObjectCollection gameObjects, int groundLayer, Vector2 windowDimensions, int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        noiseGenerator = new NoiseGenerator(seed);
    }

    /**
     * Returns the height of the ground at the given x-coordinate.
     *
     * @param x the x-coordinate to check the ground height at
     * @return the height of the ground at the given x-coordinate
     */
    public float groundHeightAt(float x) {
        return (float) (windowDimensions.y() / START_HEIGHT_FACTOR + noiseGenerator.noise(x / Block.SIZE)
                * Block.SIZE * HEIGHT_FACTOR);
    }

    /**
     * Creates ground blocks within the given range of x-coordinates, adding them to the GameObjectCollection.
     *
     * @param minX the minimum x-coordinate to create ground blocks at
     * @param maxX the maximum x-coordinate to create ground blocks at
     */
    public void createInRange(int minX, int maxX) {
        // convert to multiple of Block.SIZE
        maxX = (int) (Math.floor((float) maxX / Block.SIZE) * Block.SIZE);
        minX = (int) (Math.floor((float) minX / Block.SIZE) * Block.SIZE);
        int startAtY;
        Block block;
        for (float x = minX; x < maxX; x += Block.SIZE) {
            startAtY = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);
            for (float y = startAtY; y < windowDimensions.y()+Block.SIZE*10; y += Block.SIZE) {
                block = new Block(new Vector2(x, y),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                block.setTag(GROUND_TAG);
                gameObjects.addGameObject(block, groundLayer);
            }
        }
    }


}
