package pepse;


import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;
import pepse.world.trees.Trunk;

import java.awt.*;

import static pepse.world.trees.Tree.leafLayer;

public class PepseGameManager extends GameManager {

    public static final int SEED = 2;
    public static final int MIN_X = 0;
    public static final int CYCLE_LENGTH = 30;
    public static final int SUN_LAYER = Layer.BACKGROUND + 1;
    public static final int SUN_HALO_LAYER = Layer.BACKGROUND + 10;
    public static final int GROUND_LAYER = Layer.STATIC_OBJECTS;
    public static final int TREE_LAYER = GROUND_LAYER + 1;
    private ImageReader imageReader;
    private Terrain terrain;
    private Tree tree;
    private Avatar avatar;
    private float frameLength;
    private float endOfWorldFromRight;
    private float endOfWorldFromLeft;


    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        frameLength = windowController.getWindowDimensions().x();
        endOfWorldFromRight = windowController.getWindowDimensions().x();
        endOfWorldFromLeft = 0;
        // create sky
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        // create ground
        terrain = new Terrain(gameObjects(), GROUND_LAYER, windowController.getWindowDimensions(), SEED);
        terrain.createInRange(MIN_X, (int) windowController.getWindowDimensions().x());

        // create night
        Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), CYCLE_LENGTH);

        // create sun
        GameObject sun = Sun.create(gameObjects(), SUN_LAYER, windowController.getWindowDimensions(), CYCLE_LENGTH);

        // create sunHalo
        SunHalo.create(gameObjects(), SUN_HALO_LAYER, sun, new Color(255, 255, 0, 20));

        // create trees
        tree = new Tree(gameObjects(), SEED, TREE_LAYER, terrain::groundHeightAt);
        tree.createInRange(MIN_X, (int) windowController.getWindowDimensions().x());

        // create avatar
        Vector2 avatarStartPoint = new Vector2(windowController.getWindowDimensions().x() / 2,
                terrain.groundHeightAt(windowController.getWindowDimensions().x() / 2) - 50);
        avatar = Avatar.create(gameObjects(), Layer.DEFAULT, avatarStartPoint, inputListener, imageReader);
        gameObjects().layers().shouldLayersCollide(Layer.DEFAULT, GROUND_LAYER, true);
        gameObjects().layers().shouldLayersCollide(Layer.DEFAULT, Tree.trunkLayer, true);
        setCamera(new Camera(
                avatar,
                windowController.getWindowDimensions().mult(0.5f).subtract(avatarStartPoint).subtract(new Vector2(6, 10)),
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (avatar.getCenter().y() > terrain.groundHeightAt(avatar.getCenter().x())) {
            avatar.setCenter(new Vector2(avatar.getCenter().x(),
                    terrain.groundHeightAt(avatar.getCenter().x()) - avatar.getDimensions().y() / 2 - Block.SIZE));
            avatar.setVelocity(Vector2.ZERO);
        }

        if (avatar.getCenter().x() + frameLength/2 > endOfWorldFromRight) {
            addRightFrame();

        } else if (avatar.getCenter().x() - frameLength/2 < endOfWorldFromLeft) {
            addLeftFrame();
        }
    }

    private void addLeftFrame() {
        terrain.createInRange((int) (endOfWorldFromLeft - frameLength), (int) (endOfWorldFromLeft));
        tree.createInRange((int) (endOfWorldFromLeft - frameLength), (int) (endOfWorldFromLeft));
        removeRightFrame();
        endOfWorldFromLeft -= frameLength;
    }

    private void removeRightFrame() {
        boolean remove = false;
        for (GameObject object : gameObjects()) {
            if (object.getCenter().x() > avatar.getCenter().x() + frameLength * 1.5) {
                if (!remove){
                    remove = true;
                }
                removeObjectsFromVisibleWorld(object);
            }
        }
        if (remove){
            endOfWorldFromRight = avatar.getCenter().x() + frameLength * 1.5f;
        }
    }


    private void addRightFrame() {
        terrain.createInRange((int) (endOfWorldFromRight), (int) (endOfWorldFromRight + frameLength));
        tree.createInRange((int) (endOfWorldFromRight), (int) (endOfWorldFromRight + frameLength));
        removeLeftFrame();
        endOfWorldFromRight += frameLength;


    }

    private void removeLeftFrame() {
        boolean remove = false;
        for (GameObject object : gameObjects()) {
            if (object.getCenter().x() < avatar.getCenter().x() - frameLength * 1.5) {
                if (!remove){
                    remove = true;
                }
                removeObjectsFromVisibleWorld(object);
            }
        }
        if (remove){
            endOfWorldFromLeft = avatar.getCenter().x() - frameLength * 1.5f;
        }
    }
    private void removeObjectsFromVisibleWorld(GameObject object) {
        if (object.getTag().equals(Terrain.GROUND_TAG)) {
            gameObjects().removeGameObject(object, GROUND_LAYER);
        } else if (object.getTag().equals(Trunk.TRUNK_TAG)) {
            gameObjects().removeGameObject(object, Tree.trunkLayer);
        } else if (object.getTag().equals(Leaf.LEAF_TAG)) {
            gameObjects().removeGameObject(object, leafLayer);
        }
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}





