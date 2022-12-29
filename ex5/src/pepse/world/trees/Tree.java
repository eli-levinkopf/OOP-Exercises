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

public class Tree {

    private final Random random;
    private final Function<Float, Float> groundHeightAtFunc;
    public static int trunkLayer;
    public static int leafLayer;
    private final GameObjectCollection gameObjects;
    private int seed;

    public Tree(GameObjectCollection gameObjects, int seed, int layer, Function<Float, Float> groundHeightAtFunc) {

        this.gameObjects = gameObjects;
        trunkLayer = layer + 1;
        leafLayer = layer + 2;
        this.groundHeightAtFunc = groundHeightAtFunc;
        this.seed = seed;
        random = new Random(seed);
    }

    public void createInRange(int minX, int maxX) {
        // convert to multiple of Block.SIZE
        maxX = (int) (Math.floor((float) maxX / Block.SIZE) * Block.SIZE);
        minX = (int) (Math.floor((float) minX / Block.SIZE) * Block.SIZE);
        Random random = new Random(Objects.hash(minX, seed));
        for (float x = minX; x < maxX; x += Block.SIZE) {
            if (random.nextFloat() < 0.05) {
                addTreeAtX(x);
                x += Block.SIZE * 10;
            }
        }
    }

    private void addTreeAtX(float xCoordinate) {
        Trunk trunk = new Trunk(); //create Trunk at x
        trunk.create(xCoordinate, groundHeightAtFunc.apply(xCoordinate), gameObjects, random, trunkLayer);
        gameObjects.layers().shouldLayersCollide(trunkLayer, Layer.DEFAULT, true);
        addLeavesToTree(xCoordinate, trunk); //create leaves
    }

    private void addLeavesToTree(float xCoordinate, Trunk trunk) {
        // convert to multiple of Block.SIZE
        int xInBlocks = (int) (Math.floor(xCoordinate / Block.SIZE) * Block.SIZE);
        int yInBlocks = (int) (Math.floor(((float) trunk.getHeight() / Block.SIZE) * Block.SIZE));
        Random random = new Random(Objects.hash(xCoordinate, seed));
        float startAtX = xInBlocks - 3 * Block.SIZE;
        float endAtX = xInBlocks + 3 * Block.SIZE;
        float startAtY = yInBlocks - 3 * Block.SIZE;
        float endAtY = yInBlocks + 3 * Block.SIZE;

        for (int x = (int) startAtX; x < (int) endAtX; x += Block.SIZE) {
            for (int y = (int) startAtY; y < endAtY; y += Block.SIZE) {
                if (random.nextFloat() > 0.2) {
                    new Leaf(new Vector2(x, y),
                            new RectangleRenderable(ColorSupplier.approximateColor(new Color(50, 200, 30))),
                            gameObjects, leafLayer, random);
                    gameObjects.layers().shouldLayersCollide(leafLayer, PepseGameManager.GROUND_LAYER, true);
                }
            }
        }
    }
}






