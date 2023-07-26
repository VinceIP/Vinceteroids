package com.vinceteroids.game.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.entity.Asteroid;
import com.vinceteroids.game.entity.Bullet;
import com.vinceteroids.game.entity.Ship;

import javax.swing.text.Position;

public class GameHandler {

    private final Pool<Bullet> bulletPool;
    private final Array<Bullet> activeBullets;
    private final Pool<Asteroid> asteroidPool;
    private final Array<Asteroid> activeAsteroids;

    private Ship ship;

    private final int maxBullets = 4;

    private int gameLevel = 0;
    private int asteroidSpawnCount = 4;
    private float asteroidSpeedMin = 30f;
    private float asteroidSpeedMax = 95f;

    private int numShips = 3;
    private long score;
    private long bonusShipCounter = 0;
    private long timePassed;
    private long timeCreated;

    public GameHandler() {
        timeCreated = TimeUtils.millis();
        Vinceteroids.get().setGameHandler(this);

        //Setup object pools and object arrays. They sit in memory until needed
        bulletPool = new Pool<Bullet>(4, 4) {
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
        bulletPool.fill(getBulletPool().max);
        asteroidPool.fill(6);
        //Spawn in the player
        ship = new Ship();
    }

    public void update() {
        timePassed = TimeUtils.millis();
        if (activeAsteroids.size < 1) {
            for (int i = 0; i < asteroidSpawnCount; i++) {
                float speed = MathUtils.random(asteroidSpeedMin, asteroidSpeedMax);
                int size = 3;
                spawnAsteroid(size, speed);
            }
        }
    }

    public void spawnAsteroid(int size, float speed) {
        //Get an asteroid from the pool and wake it up. Asteroids know how to reset themselves to new values
        Asteroid a = getAsteroidPool().obtain();
        a.wake(size, speed);
    }

    public void spawnAsteroid(Vector2 position, int size, float speed) {
        //Get an asteroid from the pool and wake it up. Asteroids know how to reset themselves to new values
        Asteroid a = getAsteroidPool().obtain();
        a.wake(position, size, speed);
    }

    public Pool<Bullet> getBulletPool() {
        return bulletPool;
    }

    public Pool<Asteroid> getAsteroidPool() {
        return asteroidPool;
    }

    public Array<Bullet> getActiveBullets() {
        return activeBullets;
    }

    public Array<Asteroid> getActiveAsteroids() {
        return activeAsteroids;
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    public long getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int size) {
        int addScore = 0;
        if (size == 1) addScore = 100;
        else if (size == 2) addScore = 50;
        else if (size == 3) addScore = 20;
        bonusShipCounter += addScore;
        if (bonusShipCounter >= 10000) {
            addNumShips();
            bonusShipCounter = 0;
        }
        score += addScore;
    }

    public int getNumShips() {
        return numShips;
    }

    public void setNumShips(int numShips) {
        this.numShips = numShips;
    }

    public void addNumShips() {
        if (numShips < 5) numShips++;

    }
}
