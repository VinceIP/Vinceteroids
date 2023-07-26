package com.vinceteroids.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.entity.Entity;
import com.vinceteroids.game.handler.GameHandler;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    final Vinceteroids game;
    GameHandler gameHandler;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;
    BitmapFont font;

    float[] vertices = {
            0f, 20f,
            12f, -12f,
            0f, -4f,
            -12f, -12f
    };

    Polygon bonusShip;

    private boolean renderUi;

    public GameScreen() {
        this.game = Vinceteroids.get();
        //Game objects are created here
        this.gameHandler = new GameHandler();
        this.camera = game.getCamera();
        this.shapeRenderer = game.getShapeRenderer();
        this.font = game.getFont();
        this.spriteBatch = game.getSpriteBatch();
        bonusShip = new Polygon(vertices);
        renderUi = true;

    }

    @Override
    public void render(float delta) {
        //Process game logic
        gameHandler.update();

        //Safe handling of the entity list
        List<Entity> entityListCopy;
        synchronized (game.getEntityList()) {
            entityListCopy = new ArrayList<>(game.getEntityList());
        }

        //Erase the previous frame and render a new one
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();
        //Each entity (game object) in the list renders/updates itself here
        for (Entity e : entityListCopy) {
            e.render();
        }
        shapeRenderer.end();

        drawUi();

    }

    private void drawUi(){
        spriteBatch.begin();
        if (renderUi) {
            float uiX = game.getScreenCenter().x * 0.02f;
            float uiY = game.getScreenCenter().y * 1.98f;
            font.draw(spriteBatch,
                    "Score: " + gameHandler.getScore() +
                            "\nShips: ",
                    uiX, uiY
            );
            spriteBatch.end();
            shapeRenderer.begin();
            for (int i = 0; i < gameHandler.getNumShips(); i++) {
                Polygon p = new Polygon(vertices);
                p.setScale(0.45f, 0.45f);
                p.setPosition(uiX + 55 + (i * 22f), uiY + (uiY * -0.043f));
                shapeRenderer.polygon(p.getTransformedVertices());
            }
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
