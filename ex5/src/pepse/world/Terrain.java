package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.util.NoiseGenerator;

import java.awt.*;

public class Terrain {

    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);

    private GameObjectCollection gameObjects;
    private int groundLayer;
    private Vector2 windowDimensions;
    private NoiseGenerator noiseGenerator;

    public Terrain(GameObjectCollection gameObjects, int groundLayer, Vector2 windowDimensions, int seed) {

        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        noiseGenerator = new NoiseGenerator(seed);
    }

    public float groundHeightAt(float x) {
        return (float) (windowDimensions.y()/2 + noiseGenerator.noise(x / Block.SIZE) * Block.SIZE);
    }

    public void createInRange(int minA, int maxX) {
        
    }


}
