package com.vinceteroids.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.vinceteroids.game.Vinceteroids;

public class MainMenuScreen extends ScreenAdapter {

    Vinceteroids game;
    OrthographicCamera camera;
    SpriteBatch spriteBatch;
    BitmapFont font;

    public MainMenuScreen() {
        game = Vinceteroids.get();
        camera = game.getCamera();
        spriteBatch = game.getSpriteBatch();
        font = game.getFont();
        font.setColor(Color.WHITE);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        font.draw(spriteBatch, "Welcome to Vinceteroids\n\nPress any key to begin", game.getScreenCenter().x, game.getScreenCenter().y);
        spriteBatch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            //Switch to game screen and terminate this one
            game.setScreen(new GameScreen());
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
