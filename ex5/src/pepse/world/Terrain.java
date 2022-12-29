package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;

public class Terrain {

    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    public static final double START_HEIGHT_FACTOR = 1.3;
    public static final int HEIGHT_FACTOR = 7;
    public static final String GROUND_TAG = "ground";
    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private final Vector2 windowDimensions;
    private final NoiseGenerator noiseGenerator;

    public Terrain(GameObjectCollection gameObjects, int groundLayer, Vector2 windowDimensions, int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        noiseGenerator = new NoiseGenerator(seed);
    }

    public float groundHeightAt(float x) {
        return (float) (windowDimensions.y() / START_HEIGHT_FACTOR + noiseGenerator.noise(x / Block.SIZE)
                * Block.SIZE * HEIGHT_FACTOR);
    }

    public void createInRange(int minX, int maxX) {
        // convert to multiple of Block.SIZE
        maxX = (int) (Math.floor((float) maxX / Block.SIZE) * Block.SIZE);
        minX = (int) (Math.floor((float) minX / Block.SIZE) * Block.SIZE);
        int startAtY;
        Block block;
        for (float x = minX; x < maxX; x += Block.SIZE) {
            startAtY = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);
            for (float y = startAtY; y < windowDimensions.y(); y += Block.SIZE) {
                block = new Block(new Vector2(x, y),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                block.setTag(GROUND_TAG);
                gameObjects.addGameObject(block, groundLayer);
            }
        }
    }


}
