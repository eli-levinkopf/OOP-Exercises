package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * A class representing a block object in the game world. A block is an immovable object that can be rendered.
 */
public class Block extends GameObject {

    public static final int SIZE = 30;

    /**
     * Constructs a new Block object with the given top left corner position and renderable.
     *
     * @param topLeftCorner the top left corner position of the block in window coordinates (pixels)
     * @param renderable    the renderable representing the block. Can be null, in which case the block
     *                      will not be rendered
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
