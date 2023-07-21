package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.vinceteroids.game.handler.InputHandler;
import jdk.internal.access.SharedSecrets;

public class Ship extends Entity implements Loopable{

    InputHandler inputHandler;
    BitmapFont font;
    ShapeRenderer shapeRenderer;

    final float ROTATE_SPEED = 1.95f;
    final float ACCEL_RATE = 0.25f;
    final float DECEL_RATE = 0.15f;
    final float MAX_SPEED = 5f;

    float thrustX;
    float thrustY;
    float thrustPower = 0f;
    float vX, vY;
    float accel = 0;
    float decel = 0;
    float angle = 0;

    float[] vertices = {
            0f, 20f,      // Top vertex
            12f, -12f,    // Bottom-right vertex
            0f, -4f,      // Inner vertex
            -12f, -12f    // Bottom-left vertex
    };

    Polygon polygon;

    public Ship() {
        super();
        font = game.getFont();
    }

    public void create(){
        inputHandler = game.getInputHandler();
        inputHandler.registerListener(this);
        shapeRenderer = game.getShapeRenderer();
        polygon = new Polygon(vertices);
        //Adjust vertices relative to screen
        for (int i = 0; i < vertices.length; i++) {
            if(i%2 == 0) vertices[i] = vertices[i] + game.getScreenCenter().x;
            else vertices[i] = vertices[i] + game.getScreenCenter().y;
        }
        polygon.setOrigin(game.getScreenCenter().x,game.getScreenCenter().y);
        vertices = polygon.getTransformedVertices();
    }

    public void render(){
        rotate();
        move();
        loop();
        game.getSpriteBatch().begin();

        //Debug draw
        font.draw(game.getSpriteBatch(), "Ship speed = " + String.valueOf(accel) + "\n" +
                "Ship angle: " + String.valueOf(angle) + "\n" +
                "x: " + x + "\ny: " + y,
                (game.getScreenCenter().x - (int)(game.getScreenCenter().x * 0.98)),
                (game.getScreenCenter().y + (int)(game.getScreenCenter().y * 0.98)));

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.polygon(polygon.getTransformedVertices());

        game.getSpriteBatch().end();

    }

    public void loop(){
        x = polygon.getX();
        y = polygon.getY();
        int screenW = Gdx.graphics.getWidth() / 2;
        int screenH = Gdx.graphics.getHeight() / 2;
        if(x > screenW) x = -screenW;
        else if(x < -screenW) x = screenW;
        if(y > screenH) y = -screenH;
        else if(y < -screenH) y = screenH;
        polygon.setPosition(x,y);
    }

    @Override
    public void keyEvent(int key){
        if(key == inputHandler.LEFT) angle += ROTATE_SPEED;
        else if(key == inputHandler.RIGHT) angle -= ROTATE_SPEED;
        else if(key == inputHandler.UP){
            thrust();
        }
    }

    @Override
    public void keyEventUp(int key){
    }

    public void rotate(){
        //Clamp rotation and set
        if(angle > 360) angle = 0;
        else if (angle < 0) angle = 360;
        polygon.setRotation(angle);
    }

    public void move(){
        //Apply unrealistic special space friction and clamp acceleration
        accel -= DECEL_RATE * Gdx.graphics.getDeltaTime();
        if(accel > MAX_SPEED) accel = MAX_SPEED;
        else if(accel < 0) accel = 0;

        //Calculate speed vector using Pythagorean Theorem
        float speedVector = (float)Math.sqrt(thrustX * thrustX + thrustY * thrustY);

        if(speedVector > 0){
            thrustX -= (thrustX / speedVector) * DECEL_RATE * Gdx.graphics.getDeltaTime();
            thrustY -= (thrustY / speedVector) * DECEL_RATE * Gdx.graphics.getDeltaTime();
        }
        if(speedVector > MAX_SPEED){
            thrustX = (thrustX / speedVector) * MAX_SPEED;
            thrustY = (thrustY / speedVector) * MAX_SPEED;

        }
        polygon.translate(thrustY,thrustX);

    }

    public void thrust(){
        //Increase acceleration
        accel += ACCEL_RATE;
        //Calculate forward velocity
        thrustX += (float) Math.cos(Math.toRadians(polygon.getRotation())) * accel * Gdx.graphics.getDeltaTime();
        thrustY += (float) Math.sin(Math.toRadians(-polygon.getRotation())) * accel * Gdx.graphics.getDeltaTime();

    }
}
