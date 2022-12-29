package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Energy {
    public static final String ENERGY_100 = "assets/energyBar100.png";
    public static final String ENERGY_80 = "assets/energyBar80.png";
    public static final String ENERGY_60 = "assets/energyBar60.png";
    public static final String ENERGY_40 = "assets/energyBar40.png";
    public static final String ENERGY_20 = "assets/energyBar20.png";
    public static final String ENERGY_0 = "assets/energyBar0.png";
    private float energy = 100;
    private final GameObjectCollection gameObjects;
    private final Vector2 topLeftCorner;
    private TextRenderable textRenderable;
    private ImageRenderable imageRenderable;
    private GameObject energyBar;
    private final ImageReader imageReader;
    private final Vector2 dimensions;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public Energy(Vector2 topLeftCorner, Vector2 dimensions, GameObjectCollection gameObjects, ImageReader imageReader) {
        this.imageReader = imageReader;
        this.dimensions = dimensions;
        this.topLeftCorner = topLeftCorner;
        this.gameObjects = gameObjects;
        initializeEnergy();
    }

    private void initializeEnergy() {
        imageRenderable = imageReader.readImage(ENERGY_100, true);
        energyBar = new GameObject(topLeftCorner.add(new Vector2(0, 30)), dimensions, imageRenderable);
        textRenderable = new TextRenderable("Energy: " + energy);
        textRenderable.setColor(Color.RED);
        GameObject energyText = new GameObject(topLeftCorner, new Vector2(100, 20), textRenderable);
        energyText.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyBar.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(energyText, Layer.BACKGROUND + 2);
        gameObjects.addGameObject(energyBar, Layer.BACKGROUND + 2);
    }

    public void decreaseEnergy() {
        energy -= 0.5f;
    }

    public void increaseEnergy() {
        energy += 0.5f;
    }

    public float getEnergy() {
        return energy;
    }

    public void update() {
        textRenderable.setString("Energy: " + energy);
        if (energy == 100) {
            imageRenderable = imageReader.readImage(ENERGY_100, true);

        } else if (energy < 100 && energy > 80) {
            imageRenderable = imageReader.readImage(ENERGY_80, true);
        } else if (energy <= 80 && energy > 60) {
            imageRenderable = imageReader.readImage(ENERGY_60, true);
        } else if (energy <= 60 && energy > 40) {
            imageRenderable = imageReader.readImage(ENERGY_40, true);
        } else if (energy <= 40 && energy >= 20) {
            imageRenderable = imageReader.readImage(ENERGY_20, true);
        } else {
            imageRenderable = imageReader.readImage(ENERGY_0, true);
        }
        energyBar.renderer().setRenderable(imageRenderable);
    }
}
