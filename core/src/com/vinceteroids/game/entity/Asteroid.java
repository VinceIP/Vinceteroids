package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.vinceteroids.game.handler.GameHandler;

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
    Array<Vector2> verticesArray;

    public Asteroid() {
        alive = false;
        gameHandler = game.getGameHandler();
        create();
    }

    public void create() {
        super.create();
        verticesArray = new Array<>();
        polygon = new Polygon();
    }

    public void wake() {
        //When woken up, generate a new shape and spawn into a random screen edge
        vertices = AsteroidGenerator.generateVertices();
        polygon.setVertices(vertices);
        for (int i = 0; i < vertices.length; i=i+2) {
            verticesArray.add(
                    new Vector2(i, i+1)
            );
        }
        setRandomStatus();
        gameHandler.getActiveAsteroids().add(this);
        alive = true;
    }

    private void setRandomStatus() {
        //Setting new random properties for this asteroid
        int rand;
        int randAxis = MathUtils.random(1, 2);
        int randSide = MathUtils.random(0, 1);
        speed = MathUtils.random(speedMin, speedMax);
        size = MathUtils.random(1,3);
        float randAngle = MathUtils.random(0, 360);
        polygon.setRotation(randAngle);
        polygon.setScale((size)*sizeScaler, (size)*sizeScaler);
        moveAngle = randAngle;
        //Spawning on x-axis
        if (randAxis == 1) {
            rand = MathUtils.random(-offset, Gdx.graphics.getWidth()+offset);
            polygon.setPosition(rand, randSide * Gdx.graphics.getHeight());
        }
        //Spawning on y-axis
        else {
            rand = MathUtils.random(-offset, Gdx.graphics.getHeight()+offset);
            polygon.setPosition(rand, randSide * Gdx.graphics.getWidth());
        }

    }

    public void render() {
        if (alive) {
            super.render();
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

    public void rotate() {
        polygon.rotate(rotateSpeed);
    }

    public void move() {
        //Move based on forward vector of initial angle
        float moveX = (float) Math.cos(Math.toRadians(moveAngle)) * speed * game.deltaTIme;
        float moveY = (float) Math.sin(Math.toRadians(moveAngle)) * speed * game.deltaTIme;
        polygon.translate(moveX, moveY);

    }

    @Override
    public void reset() {
        gameHandler.getActiveAsteroids().removeValue(this, false);
        alive = false;
    }
}
