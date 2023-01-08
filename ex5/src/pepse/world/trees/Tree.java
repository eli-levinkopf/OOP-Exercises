package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;


/**
 * A class representing a tree object in the game world, consisting of a trunk and leaves.
 */
public class Tree {

    public static final double PROBABILITY_FOR_CREATE_TREE = 0.05;
    public static final int MIN_SPACE_BETWEEN_2_TREES = Block.SIZE * 10;
    public static final Color LEAF_COLOR = new Color(50, 200, 30);
    public static final int TREE_TOP_SIZE = 3 * Block.SIZE;
    public static final double PROBABILITY_FOR_CREATE_LEAF = 0.2;

    private final Random random;
    private final Function<Float, Float> groundHeightAtFunc;
    public static int trunkLayer;
    public static int leafLayer;
    private final GameObjectCollection gameObjects;
    private final int seed;

    /**
     * Constructs a new Tree object with the given game object collection, seed value, layer, and ground height function.
     *
     * @param gameObjects        the game object collection to add the tree to
     * @param seed               the seed value to use for generating random values
     * @param layer              the layer to add the tree to in the game object collection
     * @param groundHeightAtFunc a function that returns the height of the ground at a given x coordinate
     */
    public Tree(GameObjectCollection gameObjects, int seed, int layer, Function<Float, Float> groundHeightAtFunc) {

        this.gameObjects = gameObjects;
        trunkLayer = layer + 1;
        leafLayer = layer + 2;
        this.groundHeightAtFunc = groundHeightAtFunc;
        this.seed = seed;
        random = new Random(seed);
    }

    /**
     * Creates trees within the given range of x coordinates, using a probability of PROBABILITY_FOR_CREATE_TREE
     * and a minimum space between trees of MIN_SPACE_BETWEEN_2_TREES.
     *
     * @param minX the minimum x coordinate for the range
     * @param maxX the maximum x coordinate for the range
     */
    public void createInRange(int minX, int maxX) {
        // convert to multiple of Block.SIZE
        maxX = (int) (Math.floor((float) maxX / Block.SIZE) * Block.SIZE);
        minX = (int) (Math.floor((float) minX / Block.SIZE) * Block.SIZE);
        Random random = new Random(Objects.hash(minX, seed));
        for (float x = minX; x < maxX; x += Block.SIZE) {
            if (random.nextFloat() < PROBABILITY_FOR_CREATE_TREE) {
                addTreeAtX(x);
                x += MIN_SPACE_BETWEEN_2_TREES;
            }
        }
    }

    /**
     * Adds a tree at the given x coordinate, creating a trunk and leaves for the tree.
     *
     * @param xCoordinate the x coordinate to add the tree at
     */
    private void addTreeAtX(float xCoordinate) {
        Trunk trunk = new Trunk(); //create Trunk at x
        trunk.create(xCoordinate, groundHeightAtFunc.apply(xCoordinate), gameObjects, random, trunkLayer);
        gameObjects.layers().shouldLayersCollide(trunkLayer, Layer.DEFAULT, true);
        addLeavesToTree(xCoordinate, trunk); //create leaves
    }

    /**
     * Adds leaves to the tree at the given x coordinate and with the given trunk.
     *
     * @param xCoordinate the x coordinate to add the leaves at
     * @param trunk       the trunk object for the tree
     */
    private void addLeavesToTree(float xCoordinate, Trunk trunk) {
        // convert to multiple of Block.SIZE
        int xInBlocks = (int) (Math.floor(xCoordinate / Block.SIZE) * Block.SIZE);
        int yInBlocks = (int) (Math.floor(((float) trunk.getHeight() / Block.SIZE) * Block.SIZE));
        Random random = new Random(Objects.hash(xCoordinate, seed));
        float startAtX = xInBlocks - TREE_TOP_SIZE;
        float endAtX = xInBlocks + TREE_TOP_SIZE;
        float startAtY = yInBlocks - TREE_TOP_SIZE;
        float endAtY = yInBlocks + TREE_TOP_SIZE;

        for (int x = (int) startAtX; x < (int) endAtX; x += Block.SIZE) {
            for (int y = (int) startAtY; y < endAtY; y += Block.SIZE) {
                if (random.nextFloat() > PROBABILITY_FOR_CREATE_LEAF) {
                    new Leaf(new Vector2(x, y),
                            new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR)),
                            gameObjects, leafLayer, random);
                    gameObjects.layers().shouldLayersCollide(leafLayer, PepseGameManager.GROUND_LAYER, true);
                }
            }
        }
    }
}