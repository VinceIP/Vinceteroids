package com.vinceteroids.game.entity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.vinceteroids.game.Vinceteroids;
import com.badlogic.gdx.math.Polygon;


/**
 * Renderable, on-screen entity that can have custom behaviours
 */
public class Entity extends ApplicationAdapter {

    Vinceteroids game;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    Vector2 position = new Vector2(0, 0);
    float angle;
    Polygon polygon;

    public Entity() {
        this.game = Vinceteroids.get();
        this.shapeRenderer = game.getShapeRenderer();
        this.camera = game.getCamera();
    }

    public void create() {
        shapeRenderer = game.getShapeRenderer();
        game.addEntity(this);
    }

    public void render() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }

    public void keyEvent(int key) {

    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle + 90;
    }
}
