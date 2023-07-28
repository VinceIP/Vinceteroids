package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

public class Bullet extends Entity implements Pool.Poolable {
    final long LIFETIME = 1500;
    boolean alive;
    int id;

    Circle circle;
    long timeCreated;
    float size = 1.5f;
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
        if (TimeUtils.timeSinceMillis(timeCreated) >= LIFETIME) {
            die();
        }
    }

    public void move() {
        position.x += Math.cos(Math.toRadians(angle)) * speed * game.deltaTime;
        position.y += Math.sin(Math.toRadians(angle)) * speed * game.deltaTime;

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

    @Override
    public void die() {
        super.die();
        game.getGameHandler().getBulletPool().free(this);
        game.getGameHandler().getActiveBullets().removeValue(this, false);
    }
}
