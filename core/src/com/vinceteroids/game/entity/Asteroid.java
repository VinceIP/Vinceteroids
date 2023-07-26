package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.vinceteroids.game.handler.GameHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Asteroid extends Entity implements Pool.Poolable {

    GameHandler gameHandler;
    boolean alive;
    float moveAngle;
    float rotateSpeed = 1.5f;
    float speed;
    float speedMax = 55f;
    float speedMin = 85f;
    float sizeScaler = 0.5f;
    //1 is smallest, 3 is largest
    int size = 1;
    int offset = 50;

    float[] vertices;

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
        vertices = AsteroidGenerator.generateVertices();
        polygon.setVertices(vertices);
        this.size = size;
        this.speed = speed;
        Vector2 position = getSafeSpawnZone();
        this.polygon.setPosition(position.x, position.y);
        setStatus();
        gameHandler.getActiveAsteroids().add(this);
        alive = true;
    }

    public void wake(Vector2 position, int size, float speed) {
        //When woken up, generate a new shape and spawn at pos with size
        vertices = AsteroidGenerator.generateVertices();
        polygon.setVertices(vertices);
        this.size = size;
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
        polygon.setScale((size) * sizeScaler, (size) * sizeScaler);
    }

    private void setRandomStatus() {
        //Setting new random properties for this asteroid
        int rand;
        int randAxis = MathUtils.random(1, 2);
        int randSide = MathUtils.random(0, 1);
        speed = MathUtils.random(speedMin, speedMax);
        size = MathUtils.random(1, 3);
        float randAngle = MathUtils.random(0, 360);
        polygon.setRotation(randAngle);
        polygon.setScale((size) * sizeScaler, (size) * sizeScaler);
        moveAngle = randAngle;
        //Spawning on x-axis
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

    private Vector2 getSafeSpawnZone() {
        Vector2 position = new Vector2();
        float halfScreenWidth = Gdx.graphics.getWidth() / 2f;
        float halfScreenHeight = Gdx.graphics.getHeight() / 2f;
        int quadrant = MathUtils.random(1, 4);
        float modifier = 0.45f;
        switch (quadrant) {
            case 1: // Top-left quadrant
                position.set(
                        MathUtils.random(-halfScreenWidth, -halfScreenWidth * modifier),
                        MathUtils.random(halfScreenHeight * modifier, halfScreenHeight)
                );
                break;
            case 2: // Top-right quadrant
                position.set(
                        MathUtils.random(halfScreenWidth * modifier, halfScreenWidth),
                        MathUtils.random(halfScreenHeight * modifier, halfScreenHeight)
                );
                break;
            case 3: // Bottom-left quadrant
                position.set(
                        MathUtils.random(-halfScreenWidth, -halfScreenWidth * modifier),
                        MathUtils.random(-halfScreenHeight, -halfScreenHeight * modifier)
                );
                break;
            case 4: // Bottom-right quadrant
                position.set(
                        MathUtils.random(halfScreenWidth * modifier, halfScreenWidth),
                        MathUtils.random(-halfScreenHeight, -halfScreenHeight * modifier)
                );
                break;
        }
        return position;
    }

    public void rotate() {
        polygon.rotate(rotateSpeed);
    }

    public void move() {
        //Move based on forward vector of initial angle
        float moveX = (float) Math.cos(moveAngle) * speed * game.deltaTIme;
        float moveY = (float) Math.sin(moveAngle) * speed * game.deltaTIme;
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
        if (!Ship.get().dying && polygon.contains(ship.getX(), ship.getY())) {
            Ship.get().die();
        }
    }

    @Override
    public void die() {
        int count = 2;
        Vector2 position = new Vector2(
                polygon.getX(), polygon.getY()
        );
        switch (size) {
            case 2:
                gameHandler.spawnAsteroid(position, 1, this.speed);
                gameHandler.spawnAsteroid(position, 1, this.speed);
                ;
                break;
            case 3:
                gameHandler.spawnAsteroid(position, 2, this.speed);
                gameHandler.spawnAsteroid(position, 2, this.speed);

        }
        gameHandler.addScore(size);
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
