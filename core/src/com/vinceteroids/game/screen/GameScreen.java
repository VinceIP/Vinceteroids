package com.vinceteroids.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.entity.Entity;
import com.vinceteroids.game.handler.GameHandler;

public class GameScreen extends ScreenAdapter {

    final Vinceteroids game;
    GameHandler gameHandler;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;

    public GameScreen() {
        this.game = Vinceteroids.get();
        //Game objects are created here
        this.gameHandler = new GameHandler();
        this.camera = game.getCamera();
        this.shapeRenderer = game.getShapeRenderer();

    }

    @Override
    public void render(float delta) {
        //Process game logic
        gameHandler.update();
        //Erase the previous frame and render a new one
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();
        //Each entity (game object) in the list renders/updates itself here
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
