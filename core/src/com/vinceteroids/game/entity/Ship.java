package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.vinceteroids.game.Vinceteroids;

public class Ship extends Entity{
    float originX;
    float originY;
    float[] vertices = {
            0f, 20f,      // Top vertex
            12f, -12f,    // Bottom-right vertex
            0f, -4f,      // Inner vertex
            -12f, -12f    // Bottom-left vertex
    };

    PolygonSprite polygonSprite;
    PolygonSpriteBatch polygonSpriteBatch;
    Texture texture;

    private float rotate = 5f;
    public Polygon polygon;

    public Ship(Vinceteroids game) {
        super(game);
        originX = Gdx.graphics.getWidth() / 2f;
        originY = Gdx.graphics.getHeight() / 2f;
    }

    public void create(){
        polygonSpriteBatch = new PolygonSpriteBatch();
        texture = new Texture("tex.png");
        polygon = new Polygon(vertices);
        PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(texture), vertices, new short[]{0, 1, 2, 0, 2, 3});
        polygonSprite = new PolygonSprite(polygonRegion);
        float centerX = (vertices[0] + vertices[2] + vertices[4] + vertices[6]) / 4f;
        float centerY = (vertices[1] + vertices[3] + vertices[5] + vertices[7]) / 4f;
        polygonSprite.setOrigin(centerX, centerY);
        polygonSprite.setPosition(originX, originY);
        polygonSprite.setRotation(0f);



    }

    public void render(){
        polygonSpriteBatch.begin();
        polygonSprite.setScale(1.5f);
        polygonSprite.draw(polygonSpriteBatch);
        polygonSpriteBatch.end();

    }

    public void pause(){}

    public void resume(){}

    public void dispose(){}
}
