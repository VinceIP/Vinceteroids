package com.vinceteroids.game.handler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.entity.Asteroid;
import com.vinceteroids.game.entity.Bullet;
import com.vinceteroids.game.entity.Ship;

public class GameHandler {

    private final Pool<Bullet> bulletPool;
    private final Array<Bullet> activeBullets;
    private final Pool<Asteroid> asteroidPool;
    private final Array<Asteroid> activeAsteroids;

    private Ship ship;

    private final int maxBullets = 4;

    private int score;
    private int difficultyLevel = 0;
    private long timePassed;
    private long timeCreated;

    public GameHandler() {
        //We'll adjust difficulty depending on how long the game has been active
        timeCreated = TimeUtils.millis();
        Vinceteroids.get().setGameHandler(this);

        //Setup object pools and object arrays. They sit in memory until needed
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
        bulletPool.fill(getBulletPool().max);
        asteroidPool.fill(6);
        //Spawn in the player
        ship = new Ship();
    }

    public void update(){
        timePassed = TimeUtils.millis();
        //At one second, spawn in asteroids and increase the level
        if(timePassed - timeCreated > 1000 && difficultyLevel < 1){
            difficultyLevel++;
            spawnAsteroids(5);
        }
        //For now, spawn 2 asteroids if there are less than 7 on screen
        if(difficultyLevel > 0 && activeAsteroids.size < 7){
            spawnAsteroids(2);
        }
    }

    public void spawnAsteroids(int count){
        for (int i = 0; i < count; i++) {
            //Get an asteroid from the pool and wake it up. Asteroids know how to reset themselves to new values
            Asteroid a = getAsteroidPool().obtain();
            a.wake();
        }
    }

    public void spawnAsteroids(Vector2 position, int count, int size){
        for (int i = 0; i < count; i++) {
            Asteroid a = getAsteroidPool().obtain();
            a.wake(position, size);
        }
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
