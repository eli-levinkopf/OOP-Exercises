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


/**
 * The Energy class represents the energy bar of the player character in the game.
 * It is responsible for displaying the energy bar on the game screen and updating it
 * as the player's energy level changes.
 */
public class Energy {

    // Constants and fields for the energy bar images and dimensions
    public static final String ENERGY = "Energy: ";
    public static final String ENERGY_100 = "assets/energyBar100.png";
    public static final String ENERGY_80 = "assets/energyBar80.png";
    public static final String ENERGY_60 = "assets/energyBar60.png";
    public static final String ENERGY_40 = "assets/energyBar40.png";
    public static final String ENERGY_20 = "assets/energyBar20.png";
    public static final String ENERGY_0 = "assets/energyBar0.png";
    public static final int ENERGY_CAPACITY_100 = 100;
    public static final int ENERGY_CAPACITY_80 = 80;
    public static final int ENERGY_CAPACITY_60 = 60;
    public static final int ENERGY_CAPACITY_40 = 40;
    public static final int ENERGY_CAPACITY_20 = 20;
    public static final float ENERGY_FACTOR = 0.5f;
    public static final Vector2 ENERGY_BAR_OFFSET = new Vector2(0, 30);
    public static final Vector2 TEXT_DIMENSIONS = new Vector2(100, 20);
    public static final int ENERGY_LAYER = Layer.BACKGROUND + 2;

    private float energy = 100;
    private final GameObjectCollection gameObjects;
    private final Vector2 topLeftCorner;
    private TextRenderable textRenderable;
    private ImageRenderable imageRenderable;
    private GameObject energyBar;
    private final ImageReader imageReader;
    private final Vector2 dimensions;


    /**
     * Construct a new `Energy` instance.
     *
     * @param topLeftCorner the top-left corner position of the energy bar, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    the width and height of the energy bar, in window coordinates.
     * @param gameObjects   the `GameObjectCollection` to add the energy bar and its text to.
     * @param imageReader   the `ImageReader` to use for reading the energy bar images.
     */
    public Energy(Vector2 topLeftCorner, Vector2 dimensions, GameObjectCollection gameObjects,
                  ImageReader imageReader) {
        this.imageReader = imageReader;
        this.dimensions = dimensions;
        this.topLeftCorner = topLeftCorner;
        this.gameObjects = gameObjects;
        initializeEnergy();
    }

    /**
     * Initializes the energy bar and its text by adding them to the `GameObjectCollection`
     * and setting their initial renderables and coordinate spaces.
     */
    private void initializeEnergy() {
        imageRenderable = imageReader.readImage(ENERGY_100, true);
        energyBar = new GameObject(topLeftCorner.add(ENERGY_BAR_OFFSET), dimensions, imageRenderable);
        textRenderable = new TextRenderable(ENERGY + energy);
        textRenderable.setColor(Color.RED);
        GameObject energyText = new GameObject(topLeftCorner, TEXT_DIMENSIONS, textRenderable);
        energyText.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyBar.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(energyText, ENERGY_LAYER);
        gameObjects.addGameObject(energyBar, ENERGY_LAYER);
    }

    /**
     * Decreases the player's energy level by a fixed factor.
     */
    public void decreaseEnergy() {
        energy -= ENERGY_FACTOR;
    }

    /**
     * Increases the player's energy level by a fixed factor.
     */
    public void increaseEnergy() {
        energy += ENERGY_FACTOR;
    }

    /**
     * Returns the current energy level of the player.
     *
     * @return the current energy level of the player
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * Updates the energy bar and its text to reflect the current energy level of the player.
     */
    public void update() {
        textRenderable.setString(ENERGY + energy);
        // Determine which energy bar image to display based on the current energy level
        if (energy == ENERGY_CAPACITY_100) {
            imageRenderable = imageReader.readImage(ENERGY_100, true);

        } else if (energy < ENERGY_CAPACITY_100 && energy > ENERGY_CAPACITY_80) {
            imageRenderable = imageReader.readImage(ENERGY_80, true);
        } else if (energy <= ENERGY_CAPACITY_80 && energy > ENERGY_CAPACITY_60) {
            imageRenderable = imageReader.readImage(ENERGY_60, true);
        } else if (energy <= ENERGY_CAPACITY_60 && energy > ENERGY_CAPACITY_40) {
            imageRenderable = imageReader.readImage(ENERGY_40, true);
        } else if (energy <= ENERGY_CAPACITY_40 && energy >= ENERGY_CAPACITY_20) {
            imageRenderable = imageReader.readImage(ENERGY_20, true);
        } else {
            imageRenderable = imageReader.readImage(ENERGY_0, true);
        }
        energyBar.renderer().setRenderable(imageRenderable);
    }
}
