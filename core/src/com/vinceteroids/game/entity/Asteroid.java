package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Pool;
import com.vinceteroids.game.handler.GameHandler;


public class Asteroid extends Entity implements Pool.Poolable {

    private final GameHandler gameHandler;
    private boolean alive;
    private float moveAngle;
    private float speed;
    private final float SIZE_SCALE = 0.5f;
    //1 is smallest, 3 is largest
    private int sizeLevel = 1;

    private float[] vertices;

    public Asteroid() {
        alive = false;
        gameHandler = game.getGameHandler();
        create();
    }

    public void create() {
        super.create();
        polygon = new Polygon();
    }

    public void wake(int size, float speed) {
        //When woken up, generate a new shape and spawn at pos with size
        vertices = AsteroidGenerator.generateRandomVertices();
        polygon.setVertices(vertices);
        this.sizeLevel = size;
        this.speed = speed;
        Vector2 position = GetSafeSpawnZone();
        this.polygon.setPosition(position.x, position.y);
        setStatus();
        gameHandler.getActiveAsteroids().add(this);
        alive = true;
    }

    public void wake(Vector2 position, int size, float speed) {
        //When woken up, generate a new shape and spawn at pos with size
        vertices = AsteroidGenerator.generateRandomVertices();
        polygon.setVertices(vertices);
        this.sizeLevel = size;
        this.speed = speed;
        this.polygon.setPosition(position.x, position.y);
        setStatus();
        gameHandler.getActiveAsteroids().add(this);
        alive = true;
    }


    private void setStatus() {
        float randAngle = MathUtils.random(0, 180);
        polygon.setRotation(polygon.getRotation() + randAngle);
        moveAngle = polygon.getRotation();
        //speed = MathUtils.random(speedMin, speedMax);
        polygon.setScale((sizeLevel) * SIZE_SCALE, (sizeLevel) * SIZE_SCALE);
    }

    private void setRandomStatus() {
        //Setting new random properties for this asteroid
        int rand;
        int randAxis = MathUtils.random(1, 2);
        int randSide = MathUtils.random(0, 1);
        float speedMax = 55f;
        float speedMin = 85f;
        speed = MathUtils.random(speedMin, speedMax);
        sizeLevel = MathUtils.random(1, 3);
        float randAngle = MathUtils.random(0, 360);
        polygon.setRotation(randAngle);
        polygon.setScale((sizeLevel) * SIZE_SCALE, (sizeLevel) * SIZE_SCALE);
        moveAngle = randAngle;
        //Spawning on x-axis
        int offset = 50;
        if (randAxis == 1) {
            rand = MathUtils.random(-offset, Gdx.graphics.getWidth() + offset);
            polygon.setPosition(rand, randSide * Gdx.graphics.getHeight());
        }
        //Spawning on y-axis
        else {
            rand = MathUtils.random(-offset, Gdx.graphics.getHeight() + offset);
            polygon.setPosition(rand, randSide * Gdx.graphics.getWidth());
        }

    }

    public void render() {
        if (alive) {
            super.render();
            handleCollision();
            //Check if I need to loop
            Loopable.loop(this);
            rotate();
            move();
            game.getSpriteBatch().begin();
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.polygon(polygon.getTransformedVertices());
            game.getSpriteBatch().end();
        }

    }

    private static Vector2 GetSafeSpawnZone() {
        Vector2 position = new Vector2();
        int minSpawnDistance = 100;
        int maxSpawnDistance = 500;
        float spawnAngle = MathUtils.random(MathUtils.PI2);
        float spawnDistance = MathUtils.random(minSpawnDistance, maxSpawnDistance);
        position.set(
                Ship.get().polygon.getX() + spawnDistance * MathUtils.cos(spawnAngle),
                Ship.get().polygon.getY() + spawnDistance * MathUtils.sin(spawnAngle)
        );
        return position;
    }

    public void rotate() {
        float rotateSpeed = 1.5f;
        polygon.rotate(rotateSpeed);
    }

    public void move() {
        //Move based on forward vector of initial angle
        float moveX = (float) Math.cos(moveAngle) * speed * game.deltaTime;
        float moveY = (float) Math.sin(moveAngle) * speed * game.deltaTime;
        polygon.translate(moveX, moveY);

    }

    public void handleCollision() {
        for (Bullet b : gameHandler.getActiveBullets()) {
            if (isColliding(polygon, b.circle)) {
                die();
                b.die();
            }
        }
        Polygon ship = Ship.get().polygon;
        if (!Ship.get().dying && !Ship.get().immune && polygon.contains(ship.getX(), ship.getY())) {
            Ship.get().die();
            die();
        }
    }

    @Override
    public void die() {
        int count = 2;
        int randDir = MathUtils.random(-1, 1);
        int randOffset = MathUtils.random(5, 10) * randDir;
        Vector2 position = new Vector2(
                polygon.getX() + randOffset, polygon.getY() + randOffset
        );
        switch (sizeLevel) {
            case 2:
                gameHandler.spawnAsteroid(position, 1, this.speed);
                gameHandler.spawnAsteroid(position, 1, this.speed);
                ;
                break;
            case 3:
                gameHandler.spawnAsteroid(position, 2, this.speed);
                gameHandler.spawnAsteroid(position, 2, this.speed);

        }
        gameHandler.addScore(sizeLevel);
        gameHandler.getAsteroidPool().free(this);
        gameHandler.getActiveAsteroids().removeValue(this, false);
    }

    public boolean isColliding(Polygon p, Circle c) {
        return p.contains(c.x, c.y);
    }


    @Override
    public void reset() {
        gameHandler.getActiveAsteroids().removeValue(this, false);
        alive = false;
    }
}
