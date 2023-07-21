package com.vinceteroids.game.entity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vinceteroids.game.Vinceteroids;

/**
 * Renderable, on-screen entity that can have custom behaviours
 */
public class Entity extends ApplicationAdapter {

    Vinceteroids game;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    float x, y;

    public Entity(){
        this.game = Vinceteroids.get();
        this.shapeRenderer = game.getShapeRenderer();
        this.camera = game.getCamera();
    }

    public void create(){
    }

    public void render(){}

    public void pause(){}

    public void resume(){}

    public void dispose(){}

    public void keyEvent(int key){

    }

    public void keyEventUp(int key){

    }

}
