package com.vinceteroids.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.vinceteroids.game.entity.Entity;
import com.vinceteroids.game.handler.GameHandler;
import com.vinceteroids.game.handler.InputHandler;
import com.vinceteroids.game.screen.MainMenuScreen;

import java.util.ArrayList;
import java.util.List;

public class Vinceteroids extends Game {
    private static Vinceteroids game;
    public static final int MAX_BULLETS = 4;
    public int activeBullets = 0;
    public int bulletCount = 0;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    private InputHandler inputHandler;
    private GameHandler gameHandler;

    private Vector2 screenCenter;
    private List<Entity> entityList;

    public float deltaTIme;

    public static Vinceteroids get() {
        return game;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public Vector2 getScreenCenter() {
        return screenCenter;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public int getActiveBullets() {
        return activeBullets;
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    @Override
    public void create() {
        game = this;
        inputHandler = new InputHandler();
        gameHandler = new GameHandler();
        //Gdx.input.setInputProcessor(inputHandler);
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        entityList = new ArrayList<>();
        screenCenter = new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.setScreen(new MainMenuScreen());
    }

    @Override
    public void render() {
        deltaTIme = Gdx.graphics.getDeltaTime();
        inputHandler.getKeyPress();
        super.render();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();

    }
}
