package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;

import java.awt.*;

public interface Loopable {
    public static void loop(Entity entity) {
        int offset = 0;
        Polygon polygon = entity.polygon;
        if(entity instanceof Asteroid) offset = 50;
        float x = polygon.getX();
        float y = polygon.getY();
        int screenW = Gdx.graphics.getWidth() - 1;
        int screenH = Gdx.graphics.getHeight() - 1;
        if (x > screenW + offset) x = -offset;
        else if (x < -offset) x = screenW;
        if (y > screenH + offset) y = -offset;
        else if (y < -offset) y = screenH + offset;
        polygon.setPosition(x, y);
    }
}
