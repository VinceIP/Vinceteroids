package com.vinceteroids.game.handler;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.vinceteroids.game.entity.Bullet;

public class GameHandler {

    private final Pool<Bullet> bulletPool;
    private final Array<Bullet> activeBullets;

    private int maxBullets = 4;

    private int score;

    public GameHandler() {
        bulletPool = new Pool<Bullet>(4,4) {
            @Override
            protected Bullet newObject() {
                return new Bullet();
            }
        };
        activeBullets = new Array<>();
    }

    public Pool<Bullet> getBulletPool() {
        return bulletPool;
    }

    public Array<Bullet> getActiveBullets() {
        return activeBullets;
    }

    public int getMaxBullets() {
        return maxBullets;
    }
}
