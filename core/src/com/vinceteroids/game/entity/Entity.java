package com.vinceteroids.game.entity;

import com.badlogic.gdx.ApplicationAdapter;
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

    public Entity(Vinceteroids game){
        this.game = game;
        this.shapeRenderer = game.shapeRenderer;
        this.camera = game.camera;
    }

    public void create(){
    }

    public void render(){}

    public void pause(){}

    public void resume(){}

    public void dispose(){}

}
