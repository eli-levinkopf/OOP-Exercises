package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;
    private final Counter counter;

    /**
     * Construct a new GameObject instance. This constructor extends the super's GameObject
     * constructor, and also saves the strategy given.
     *
     * @param topLeftCorner     the position in the window the top left corner of the object will be
     *                          placed.
     * @param dimensions        the 2d dimensions of the object on the screen.
     * @param renderable        the image object to display on the screen.
     * @param collisionStrategy the strategy that will be used when the brick breaks.
     * @param counter           the number of active bricks in the game.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
    }

    /**
     * This is an override method for GameObject's onCollisionEnter. When the game detects a
     * collision between the two objects, it activates the strategy of the brick.
     *
     * @param other     the object this brick has collided with
     * @param collision the attributes of the collision that occurred.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, counter);
    }
}
