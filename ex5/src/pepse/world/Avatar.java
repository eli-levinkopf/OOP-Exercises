package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


import java.awt.event.KeyEvent;

public class Avatar extends GameObject {

    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -300;
    private static final float GRAVITY = 500;
    public static final String PATH_TO_AVATAR_WALKING1 = "assets/avatar1.png";
    public static final String PATH_TO_AVATAR_WALKING2 = "assets/avatar2.png";
    public static final String PATH_TO_AVATAR_FLYING = "assets/fly.png";
    public static final String PATH_TO_AVATAR_JUMPING = "assets/jump.png";
    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private static Energy energy;
    private int i = 0;

    public Avatar(Vector2 pos, Renderable renderable, UserInputListener inputListener, GameObjectCollection gameObjects, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(50), renderable);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);

    }


    public static Avatar create(GameObjectCollection gameObjects, int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener, ImageReader imageReader) {
        Avatar avatar = new Avatar(topLeftCorner, imageReader.readImage(PATH_TO_AVATAR_WALKING1, true), inputListener, gameObjects, imageReader);
        gameObjects.addGameObject(avatar, layer);
        energy = new Energy(Vector2.ZERO, new Vector2(100, 50), gameObjects, imageReader);
        return avatar;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
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
                && energy.getEnergy() > 0 ) {
            transform().setVelocityY(VELOCITY_Y);
            energy.decreaseEnergy();
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_FLYING, true));
        }
        if (getVelocity().y() == 0 && energy.getEnergy() < 100) {
            energy.increaseEnergy();
        }
        if(getVelocity().y() > 0){
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_WALKING1, true));
        }
        energy.update();
    }

    private void walkingRender() {
        if (i == 10){
            i = 0;
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_WALKING1, true));
        }else if(i == 5) {
            renderer().setRenderable(imageReader.readImage(PATH_TO_AVATAR_WALKING2, true));
        }i++;
    }
}
