package collision;

import danogl.GameObject;

public interface CollisionStrategy {
    void onCollision(GameObject thisObj, GameObject otherObj);
}
