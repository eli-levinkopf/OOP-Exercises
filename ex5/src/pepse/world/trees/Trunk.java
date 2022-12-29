package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Trunk {

    public static final String TRUNK_TAG = "trunk";
    private int height;
    public void create(float xCoordinate, float yCoordinate, GameObjectCollection gameObjects, Random random, int layer) {
        int trunkHeight = random.nextInt(10) + 5;
        // convert to multiple of Block.SIZE
        int start = (int) (Math.floor(yCoordinate / Block.SIZE) * Block.SIZE);
        int end = (int) (Math.floor(yCoordinate - trunkHeight * Block.SIZE));
        int i;
        for (i = start; i > end; i -= Block.SIZE) {
            Block block = new Block(new Vector2(xCoordinate, i - Block.SIZE),
                    new RectangleRenderable(ColorSupplier.approximateColor(new Color(100, 50, 20))));
            block.setTag(TRUNK_TAG);
            gameObjects.addGameObject(block, layer);
        }
        height = i-Block.SIZE;
    }

    public int getHeight() {
        return height;
    }
}
