package com.vinceteroids.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.vinceteroids.game.entity.Ship;
import com.vinceteroids.game.handler.InputHandler;
import com.vinceteroids.game.screen.MainMenuScreen;
import jdk.xml.internal.JdkConstants;

import javax.tools.JavaCompiler;
import java.awt.*;

public class Vinceteroids extends Game {
	private static Vinceteroids game;
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	private InputHandler inputHandler;
	private Vector2 screenCenter;

	public static Vinceteroids get(){
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

	public Vector2 getScreenCenter(){
		return screenCenter;
	}

	@Override
	public void create () {
		game = this;
		inputHandler = new InputHandler();
		//Gdx.input.setInputProcessor(inputHandler);
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		screenCenter = new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setScreen(new MainMenuScreen());
	}

	@Override
	public void render () {
		inputHandler.getKeyPress();
		super.render();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		font.dispose();

	}
}
