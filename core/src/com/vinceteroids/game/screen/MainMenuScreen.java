package com.vinceteroids.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.vinceteroids.game.Vinceteroids;

public class MainMenuScreen implements Screen {

    final Vinceteroids game;
    final int windowWidth = Gdx.graphics.getWidth();
    final int windowHeight = Gdx.graphics.getHeight();

    OrthographicCamera camera;

    public MainMenuScreen(Vinceteroids game) {
        this.game = game;
        camera = game.camera;
        camera.setToOrtho(false, windowWidth, windowHeight);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        camera.update();
        game.spriteBatch.setProjectionMatrix(camera.combined);

        game.spriteBatch.begin();
        game.font.draw(game.spriteBatch, "Welcome to Vinceteroids\n\nPress any key to begin", windowHeight / 2, (windowHeight / 2) + 80);
        game.spriteBatch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
