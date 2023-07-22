package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.handler.GameHandler;
import com.vinceteroids.game.handler.InputHandler;

public class Ship extends Entity {

    InputHandler inputHandler;
    GameHandler gameHandler;
    BitmapFont font;

    final float ROTATE_SPEED = 3.75f;
    final float ACCEL_RATE = 0.25f;
    final float DECEL_RATE = 0.45f;
    final float MAX_SPEED = 5f;

    float thrustX;
    float thrustY;
    float accel = 0;

    float[] vertices = {
            0f, 20f,
            12f, -12f,
            0f, -4f,
            -12f, -12f
    };

    public Ship() {
        super();
        font = game.getFont();
        gameHandler = game.getGameHandler();
    }

    public void create() {
        super.create();
        inputHandler = game.getInputHandler();
        inputHandler.registerListener(this);
        polygon = new Polygon(vertices);

        //Adjust vertices relative to screen
//        for (int i = 0; i < vertices.length; i++) {
//            if (i % 2 == 0) vertices[i] = vertices[i] + game.getScreenCenter().x;
//            else vertices[i] = vertices[i] + game.getScreenCenter().y;
//        }
        polygon.setOrigin(vertices[4], vertices[5]);
        polygon.setPosition(game.getScreenCenter().x, game.getScreenCenter().y);
        vertices = polygon.getTransformedVertices();
    }

    @Override
    public void render() {
        super.render();
        Loopable.loop(polygon);
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
        shapeRenderer.polygon(polygon.getTransformedVertices());

        game.getSpriteBatch().end();

    }

    @Override
    public void keyEvent(int key) {
        if (key == inputHandler.LEFT) angle += ROTATE_SPEED;
        else if (key == inputHandler.RIGHT) angle -= ROTATE_SPEED;
        else if (key == inputHandler.UP) thrust();
        else if (key == inputHandler.FIRE) {
            fire();
        }
    }

    public void fire() {
        if(gameHandler.getActiveBullets().size < gameHandler.getMaxBullets()){
            Bullet bullet = gameHandler.getBulletPool().obtain();
            //Get coords of ship's upper vertex
            Vector2 position = new Vector2(
                    vertices[0], vertices[1]
            );
            bullet.wake(position, polygon.getRotation());
        }

    }

    public void rotate() {
        //Clamp rotation and set
        polygon.setRotation(angle);
    }

    public void move() {
        //Apply unrealistic special space friction and clamp acceleration
        accel -= DECEL_RATE * Gdx.graphics.getDeltaTime();
        if (accel > MAX_SPEED) accel = MAX_SPEED;
        else if (accel < 0) accel = 0;

        //Calculate speed vector using Pythagorean Theorem
        float speedVector = (float) Math.sqrt(thrustX * thrustX + thrustY * thrustY);

        if (speedVector > 0) {
            thrustX -= (thrustX / speedVector) * DECEL_RATE * Gdx.graphics.getDeltaTime();
            thrustY -= (thrustY / speedVector) * DECEL_RATE * Gdx.graphics.getDeltaTime();
        }
        if (speedVector > MAX_SPEED) {
            thrustX = (thrustX / speedVector) * MAX_SPEED;
            thrustY = (thrustY / speedVector) * MAX_SPEED;

        }
        polygon.translate(thrustY, thrustX);
        position = new Vector2(polygon.getX(), polygon.getY());

    }

    public void thrust() {
        //Increase acceleration
        accel += ACCEL_RATE;
        //Calculate forward velocity
        thrustX += (float) Math.cos(Math.toRadians(polygon.getRotation())) * accel * Gdx.graphics.getDeltaTime();
        thrustY += (float) Math.sin(Math.toRadians(-polygon.getRotation())) * accel * Gdx.graphics.getDeltaTime();

    }
}
