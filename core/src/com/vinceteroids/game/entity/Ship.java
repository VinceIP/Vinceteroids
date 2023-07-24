package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.handler.GameHandler;
import com.vinceteroids.game.handler.InputHandler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Ship extends Entity {
    private static Ship ship;

    InputHandler inputHandler;
    GameHandler gameHandler;
    BitmapFont font;

    //Const properties
    final float ROTATE_SPEED = 3.75f;
    final float ACCEL_RATE = 0.25f;
    final float DECEL_RATE = 0.45f;
    final float MAX_SPEED = 5f;

    float thrustX;
    float thrustY;
    float accel = 0;
    public boolean dying = false;
    List<Polygon> splinters;
    List<Circle> explosion;
    float explosionLife = 2f;
    float[] splinterAngles;
    float[] explosionAngles;
    long[] explosionCreateTime;
    long[] explosionLifetime;
    int explosionParticleCount = 80;

    //The vertices of the ship's polygon
    float[] vertices = {
            0f, 20f,
            12f, -12f,
            0f, -4f,
            -12f, -12f
    };

    float[] transformedVertices;

    public Ship() {
        super();
        Ship.ship = this;
        font = game.getFont();
        gameHandler = game.getGameHandler();
        create();
    }

    public static Ship get() {
        return ship;
    }

    public void create() {
        super.create();
        inputHandler = game.getInputHandler();
        //Tell input handler that this ship is listening for key presses
        inputHandler.registerListener(this);
        polygon = new Polygon(vertices);

        //Set the ships origin at its "engine", center it on screen
        // Calculate the center of the polygon
        float centerX = (vertices[0] + vertices[2] + vertices[4] + vertices[6]) / 4f;
        float centerY = (vertices[1] + vertices[3] + vertices[5] + vertices[7]) / 4f;

// Set the ship's polygon origin to the center
        polygon.setOrigin(centerX, centerY);
//        polygon.setOrigin(vertices[4], vertices[5]);
        polygon.setPosition(game.getScreenCenter().x, game.getScreenCenter().y);
        transformedVertices = polygon.getTransformedVertices();
    }

    @Override
    public void render() {
        super.render();
        //Check if the ship needs to loop to the opposite side of screen
        //Handle rotation and movement
        Loopable.loop(this);
        rotate();
        move();
        game.getSpriteBatch().begin();

        //Debug draw
        font.draw(game.getSpriteBatch(), "Ship speed = " + accel + "\n" +
                        "Ship angle: " + angle + "\n" +
                        "x: " + (int) polygon.getX() +
                        "\ny: " + (int) polygon.getY(),
                (game.getScreenCenter().x - (int) (game.getScreenCenter().x * 0.98)),
                (game.getScreenCenter().y + (int) (game.getScreenCenter().y * 0.98)));

        shapeRenderer.setColor(Color.WHITE);
        if (dying && splinters != null) {
            for (Polygon p : splinters) {
                shapeRenderer.polygon(p.getTransformedVertices());
            }
            for (Circle c : explosion) {
                shapeRenderer.circle(c.x, c.y, c.radius);
            }

        } else {
            shapeRenderer.polygon(polygon.getTransformedVertices());

        }

        game.getSpriteBatch().end();

    }

    @Override
    public void keyEvent(int key) {
        //Get keys from inputHandler and do the things
        if (key == inputHandler.LEFT) angle += ROTATE_SPEED;
        else if (key == inputHandler.RIGHT) angle -= ROTATE_SPEED;
        else if (key == inputHandler.UP) thrust();
        else if (key == inputHandler.FIRE) {
            fire();
        }
    }

    public void fire() {
        //If there's less than 4 active bullets on screen, grab a new one from the pool and shoot it
        if (gameHandler.getActiveBullets().size < gameHandler.getMaxBullets()) {
            Bullet bullet = gameHandler.getBulletPool().obtain();
            //Get coords of ship's upper vertex
            Vector2 position = new Vector2(
                    transformedVertices[0], transformedVertices[1]
            );
            bullet.wake(position, polygon.getRotation());
        }

    }

    public void rotate() {
        polygon.setRotation(angle);
    }

    public void move() {
        if (dying && splinters != null) {
            accel = 0;
            for (int i = 0; i < splinters.size(); i++) {
                float moveAngle = splinterAngles[i] * 1.5f * (float) Math.PI;
                float moveX = (float) Math.cos(moveAngle) * 30.5f * game.deltaTIme;
                float moveY = (float) Math.sin(moveAngle) * 30.5f * game.deltaTIme;
                splinters.get(i).translate(moveX, moveY);
                splinters.get(i).rotate(1.8f);
            }
            for (int i = 0; i < explosion.size(); i++) {
                if (TimeUtils.timeSinceMillis(explosionCreateTime[i]) <= explosionLifetime[i]) {
                    Circle c = explosion.get(i);
                    float moveAngle = explosionAngles[i] * c.radius * (float) Math.PI;
                    float moveX = (float) Math.cos(moveAngle) * 88.5f * game.deltaTIme;
                    float moveY = (float) Math.sin(moveAngle) * 88.5f * game.deltaTIme;
                    c.setPosition(c.x + moveX, c.y + moveY);
                } else {
                    explosion.get(i).setPosition(-1000, -1000);
                }

            }
        } else {
            //Apply unrealistic special space friction and clamp acceleration
            accel -= DECEL_RATE * Gdx.graphics.getDeltaTime();
            if (accel > MAX_SPEED) accel = MAX_SPEED;
            else if (accel < 0) accel = 0;

            //Calculate speed vector using Pythagorean Theorem
            //I don't claim to understand the math
            float speedVector = (float) Math.sqrt(thrustX * thrustX + thrustY * thrustY);

            if (speedVector > 0) {
                thrustX -= (thrustX / speedVector) * DECEL_RATE * Gdx.graphics.getDeltaTime();
                thrustY -= (thrustY / speedVector) * DECEL_RATE * Gdx.graphics.getDeltaTime();
            }
            if (speedVector > MAX_SPEED) {
                thrustX = (thrustX / speedVector) * MAX_SPEED;
                thrustY = (thrustY / speedVector) * MAX_SPEED;

            }
            //Apply new translation each frame
            polygon.translate(thrustY, thrustX);
            position = new Vector2(polygon.getX(), polygon.getY());

        }


    }

    public void thrust() {
        //Increase acceleration
        accel += ACCEL_RATE;
        //Calculate forward velocity
        thrustX += (float) Math.cos(Math.toRadians(polygon.getRotation())) * accel * Gdx.graphics.getDeltaTime();
        thrustY += (float) Math.sin(Math.toRadians(-polygon.getRotation())) * accel * Gdx.graphics.getDeltaTime();

    }

    public void die() {
        dying = true;
        splinter();
    }

    public void splinter() {
        splinters = new ArrayList<>();
        explosion = new ArrayList<>();
        //float[] vertices = polygon.getTransformedVertices();
        int numVertices = transformedVertices.length;
        for (int i = 0; i < numVertices; i += 2) {
            int nextIndex = (i + 2) % numVertices;
            float x = transformedVertices[i];
            float y = transformedVertices[i + 1];
            float x2 = transformedVertices[nextIndex];
            float y2 = transformedVertices[nextIndex + 1];
            float x3 = (x + x2) / 2;
            float y3 = (y + y2) / 2;
            float[] newVerts = new float[]{x, y, x3, y3, x2, y2};
            Polygon p = new Polygon(newVerts);
            float[] tv = p.getTransformedVertices();
            float cx = (tv[0] + tv[2] + tv[4]) / 2;
            float cy = (tv[1] + tv[3] + tv[5]) / 2;
            p.setOrigin(p.getTransformedVertices()[2], p.getTransformedVertices()[3]);
            p.setPosition(0, 0);
            p.setScale(1, 1);
            splinters.add(p);
        }
        splinterAngles = new float[splinters.size()];
        for (int i = 0; i < splinters.size(); i++) {
            splinterAngles[i] = (float) Math.pow(Math.PI, 2) * i;
        }
        explosionCreateTime = new long[explosionParticleCount];
        explosionLifetime = new long[explosionParticleCount];
        explosionAngles = new float[explosionParticleCount];
        for (int i = 0; i < explosionAngles.length; i++) {
            Circle c = new Circle();
            explosionCreateTime[i] = TimeUtils.millis();
            c.radius = MathUtils.random(0.05f, 0.15f);
            c.setPosition(position);
            explosion.add(c);
            explosionAngles[i] = (float) Math.pow(Math.PI, 2) * i;
            explosionLifetime[i] = MathUtils.random(5, 3500);

        }
    }
}
