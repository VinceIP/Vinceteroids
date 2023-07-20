package com.vinceteroids.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.entity.Ship;

public class GameScreen implements Screen {
    final Vinceteroids game;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Ship ship;
    public GameScreen(Vinceteroids game) {
        this.game = game;
        this.ship = new Ship(game);
        this.camera = game.camera;
        this.shapeRenderer = game.shapeRenderer;

        ship.create();
    }

    @Override
    public void show() {
        ship.create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();
        ship.render();
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
