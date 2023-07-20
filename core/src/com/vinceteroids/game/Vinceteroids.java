package com.vinceteroids.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.vinceteroids.game.entity.Ship;
import com.vinceteroids.game.screen.MainMenuScreen;
import jdk.xml.internal.JdkConstants;

import javax.tools.JavaCompiler;
import java.awt.*;

public class Vinceteroids extends Game {
	public BitmapFont font;
	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;
	public OrthographicCamera camera;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		this.setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		font.dispose();

	}
}
