package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

public class Bullet extends Entity implements Pool.Poolable {
    final long LIFETIME = 1000;
    boolean alive;
    int id;

    Circle circle;
    long timeCreated;
    float size = 3.5f;
    float speed = 500f;

    public Bullet() {
        alive = false;
        create();
    }

    public void create() {
        super.create();
        circle = new Circle(position, size);
    }

    public void wake(Vector2 position, float angle) {
        //Sets new angle and position from whatever fired it
        this.setPosition(position);
        this.setAngle(angle);
        timeCreated = TimeUtils.millis();
        game.getGameHandler().getActiveBullets().add(this);
        id = game.getGameHandler().getActiveBullets().size - 1;
        alive = true;
    }

    @Override
    public void reset() {
        setAngle(0);
        setPosition(new Vector2(0, 0));
        game.getGameHandler().getActiveBullets().removeValue(this, false);
        alive = false;
    }

    @Override
    public void render() {
        if (alive) {
            //Checks for collision with asteroids and dies when time has expired
            handleCollision();
            checkForTimeout();
            super.render();
            move();
            game.getSpriteBatch().begin();
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(circle.x, circle.y, circle.radius);
            game.getSpriteBatch().end();
        }
    }

    public void checkForTimeout() {
        if (TimeUtils.millis() - timeCreated >= LIFETIME) {
            game.getGameHandler().getBulletPool().free(this);
        }
    }

    public void move() {
        position.x += Math.cos(Math.toRadians(angle)) * speed * game.deltaTIme;
        position.y += Math.sin(Math.toRadians(angle)) * speed * game.deltaTIme;

        // Implement looping manually, because bullets are basic circles and not polygons
        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();

        if (position.x > screenW) {
            position.x = 0;
        } else if (position.x < 0) {
            position.x = screenW;
        }

        if (position.y > screenH) {
            position.y = 0;
        } else if (position.y < 0) {
            position.y = screenH;
        }
        circle.setPosition(position);
    }

    public void handleCollision() {
        //Not super precise collision right now. Circles and polygons work differently
        //For each active asteroid, check to see if this bullet is colliding with any asteroid vertices
        for (Asteroid a : game.getGameHandler().getActiveAsteroids()) {
            float[] vertices = a.polygon.getTransformedVertices();
            int numVertices = a.polygon.getTransformedVertices().length;
            for (int i = 0; i < numVertices; i = i + 2) {
                //We need to check a line based on 2 points from the float array
                int nextIndex = (i + 2) % numVertices;
                Vector2 point1 = new Vector2(vertices[i], vertices[i + 1]);
                Vector2 point2 = new Vector2(vertices[nextIndex], vertices[nextIndex + 1]);
                if (Intersector.intersectSegmentCircle(
                        point1, point2, getPosition(), (circle.radius * circle.radius))
                ) {
                    game.getGameHandler().getAsteroidPool().free(a);
                    game.getGameHandler().getBulletPool().free(this);
                }
            }

        }
    }


}
