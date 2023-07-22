package com.vinceteroids.game.handler;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.vinceteroids.game.entity.Asteroid;
import com.vinceteroids.game.entity.Bullet;

public class GameHandler {

    private final Pool<Bullet> bulletPool;
    private final Array<Bullet> activeBullets;
    private final Pool<Asteroid> asteroidPool;
    private final Array<Asteroid> activeAsteroids;

    private final int maxBullets = 4;

    private int score;

    public GameHandler() {
        bulletPool = new Pool<Bullet>(4,4) {
            @Override
            protected Bullet newObject() {
                return new Bullet();
            }
        };
        activeBullets = new Array<>();
        asteroidPool = new Pool<Asteroid>() {
            @Override
            protected Asteroid newObject() {
                return new Asteroid();
            }
        };
        activeAsteroids = new Array<>();
    }

    public Pool<Bullet> getBulletPool() {
        return bulletPool;
    }

    public Pool<Asteroid> getAsteroidPool(){
        return asteroidPool;
    }

    public Array<Bullet> getActiveBullets() {
        return activeBullets;
    }

    public Array<Asteroid> getActiveAsteroids(){
        return activeAsteroids;
    }

    public int getMaxBullets() {
        return maxBullets;
    }
}
