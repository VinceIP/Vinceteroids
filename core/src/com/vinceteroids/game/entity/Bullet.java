package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
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
    float speed = 9f;

    public Bullet() {
        alive = false;
        create();
    }

    public void create() {
        super.create();
        circle = new Circle(position, size);
    }

    public void wake(Vector2 position, float angle) {
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
        game.getGameHandler().getActiveBullets().removeIndex(
                game.getGameHandler().getActiveBullets().size - 1
        );
        alive = false;
    }

    @Override
    public void render() {
        if (alive) {
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
            System.out.println("free");
            game.getGameHandler().getBulletPool().free(this);
        }
    }

    public void move() {
        position.x += Math.cos(Math.toRadians(angle)) * speed;
        position.y += Math.sin(Math.toRadians(angle)) * speed;

        // Implement looping manually
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


}
