package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


import java.awt.event.KeyEvent;

/**
 * The Avatar class represents the player character in the game. It handles user input and
 * updates the avatar's position and appearance based on the input received.
 */
public class Avatar extends GameObject {

    public static final String PATH_TO_AVATAR_WALKING1 = "assets/avatar1.png";
    public static final String PATH_TO_AVATAR_WALKING2 = "assets/avatar2.png";
    public static final String PATH_TO_AVATAR_FLYING = "assets/fly.png";
    public static final String PATH_TO_AVATAR_JUMPING = "assets/jump.png";
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -300;
    private static final float GRAVITY = 500;
    public static final int AVATAR_SIZE = 50;
    public static final Vector2 ENERGY_DIMENSIONS = new Vector2(100, 50);
    public static final int FIRST_FACTOR_FOR_WALKING_RENDER = 10;
    public static final int SECOND_FACTOR_FOR_WALKING_RENDER = 5;
    public static final int INITIALIZE_TO_ZERO = 0;
    public static final int FULL_ENERGY = 100;
    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private static Energy energy;
    private int i = INITIALIZE_TO_ZERO;


    /**
     * Creates a new Avatar object with the given position, renderable, input listener, game objects,
     * and image reader.
     *
     * @param pos           The position of the avatar.
     * @param renderable    The renderable object to be used to display the avatar.
     * @param inputListener The input listener to be used to receive user input.
     * @param gameObjects   The game object collection to which the avatar will be added.
     * @param imageReader   The image reader to be used to read images for the avatar.
     */
    public Avatar(Vector2 pos, Renderable renderable, UserInputListener inputListener, GameObjectCollection gameObjects,
                  ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(AVATAR_SIZE), renderable);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
    }


    /**
     * Creates a new Avatar object and adds it to the given game object collection.
     *
     * @param gameObjects   The game object collection to which the avatar will be added.
     * @param layer         The layer in the game object collection to which the avatar will be added.
     * @param topLeftCorner The top left corner position of the avatar.
     * @param inputListener The input listener to be used to receive user input.
     * @param imageReader   The image reader to be used to read images for the avatar.
     * @return The newly created Avatar object.
     */
    public static Avatar create(GameObjectCollection gameObjects, int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener, ImageReader imageReader) {
        Avatar avatar = new Avatar(topLeftCorner, imageReader.readImage(PATH_TO_AVATAR_WALKING1,
                true), inputListener, gameObjects, imageReader);
        gameObjects.addGameObject(avatar, layer);
        energy = new Energy(Vector2.ZERO, ENERGY_DIMENSIONS, gameObjects, imageReader);
        return avatar;
    }

    /**
     * Overrides the update method of the GameObject class to handle user input and update the
     * avatar's position and appearance based on the input received.
     *
     * @param deltaTime The time in seconds since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = INITIALIZE_TO_ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            renderer().setIsFlippedHorizontally(true);
            xVel -= VELOCITY_X;
            walkingRender();
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            renderer().setIsFlippedHorizontally(false);
            xVel += VELOCITY_X;
            walkingRender();
        }
        transform().setVelocityX(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0) {
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_JUMPING, true));
            transform().setVelocityY(VELOCITY_Y);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)
                && energy.getEnergy() > 0) {
            transform().setVelocityY(VELOCITY_Y);
            energy.decreaseEnergy();
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_FLYING, true));
        }
        if (getVelocity().y() == 0 && energy.getEnergy() < FULL_ENERGY) {
            energy.increaseEnergy();
        }
        if (getVelocity().y() > 0) {
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_WALKING1, true));
        }
        energy.update();
    }

    /**
     * Changes the avatar's renderable object based on the current frame count. This is used to
     * create an animation effect when the avatar is walking.
     */
    private void walkingRender() {
        if (i == FIRST_FACTOR_FOR_WALKING_RENDER) {
            i = INITIALIZE_TO_ZERO;
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_WALKING1, true));
        } else if (i == SECOND_FACTOR_FOR_WALKING_RENDER) {
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_WALKING2, true));
        }
        i++;
    }
}
