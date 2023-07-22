package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.vinceteroids.game.handler.GameHandler;

public class Asteroid extends Entity implements Pool.Poolable, Collides {

    GameHandler gameHandler;
    boolean alive;
    float moveAngle;
    float rotateSpeed = 1.5f;
    float speed;
    float speedMax = 2f;
    float speedMin = 0.7f;
    float sizeScaler = 1.5f;
    //1 is smallest, 3 is largest
    int size;

    float[] vertices;

    public Asteroid() {
        System.out.println("creating asteroid");
        alive = false;
        gameHandler = game.getGameHandler();
        create();
    }

    public void create() {
        super.create();
        polygon = new Polygon();
    }

    public void wake() {
        vertices = AsteroidGenerator.generateVertices();
        polygon.setVertices(vertices);
        setRandomStatus();
        alive = true;
    }

    private void setRandomStatus() {
        int rand;
        int randAxis = MathUtils.random(1, 2);
        int randSide = MathUtils.random(0, 1);
        speed = MathUtils.random(speedMin, speedMax);
        float randAngle = MathUtils.random(0, 360);
        polygon.setRotation(randAngle);
        polygon.setScale(polygon.getScaleX() * sizeScaler, polygon.getScaleY() * sizeScaler);
        moveAngle = randAngle;
        //Spawning on x-axis
        if (randAxis == 1) {
            rand = MathUtils.random(0, Gdx.graphics.getWidth());
            polygon.setPosition(rand, randSide * Gdx.graphics.getHeight());
        }
        //Spawning on y-axis
        else {
            rand = MathUtils.random(0, Gdx.graphics.getHeight());
            polygon.setPosition(rand, randSide * Gdx.graphics.getWidth());
        }

    }

    public void render() {
        if (alive) {
            super.render();
            Loopable.loop(polygon);
            rotate();
            move();
            game.getSpriteBatch().begin();
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.polygon(polygon.getTransformedVertices());
            game.getSpriteBatch().end();
        }

    }

    public void rotate() {
        polygon.rotate(rotateSpeed);
    }

    public void move() {
        float moveX = (float) Math.cos(Math.toRadians(moveAngle)) * speed;
        float moveY = (float) Math.sin(Math.toRadians(moveAngle)) * speed;
        polygon.translate(moveX, moveY);

    }

    @Override
    public void reset() {
        alive = false;
    }

    @Override
    public void handleCollision() {
        if(polygon.)
    }
}
