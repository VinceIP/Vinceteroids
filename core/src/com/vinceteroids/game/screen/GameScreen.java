package com.vinceteroids.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.entity.Asteroid;
import com.vinceteroids.game.entity.Entity;
import com.vinceteroids.game.entity.Ship;
import com.vinceteroids.game.handler.GameHandler;

public class GameScreen extends ScreenAdapter {

    final Vinceteroids game;
    GameHandler gameHandler;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Ship ship;

    public GameScreen() {
        this.game = Vinceteroids.get();
        this.gameHandler = game.getGameHandler();
        this.ship = new Ship();
        this.camera = game.getCamera();
        this.shapeRenderer = game.getShapeRenderer();
        gameHandler.getBulletPool().fill(gameHandler.getBulletPool().max);
        gameHandler.getAsteroidPool().fill(3);
        ship.create();
        for (int i = 0; i < 4; i++) {
            Asteroid tester = gameHandler.getAsteroidPool().obtain();
            tester.wake();
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();
        for (Entity e : game.getEntityList()) {
            e.render();
        }
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
