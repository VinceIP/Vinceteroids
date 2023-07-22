package com.vinceteroids.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;

import java.awt.*;

public interface Loopable {
    public static void loop(Polygon polygon) {
        float x = polygon.getX();
        float y = polygon.getY();
        int screenW = Gdx.graphics.getWidth() - 1;
        int screenH = Gdx.graphics.getHeight() - 1;
        if (x > screenW) x = 0;
        else if (x < 0) x = screenW;
        if (y > screenH) y = 0;
        else if (y < 0) y = screenH;
        polygon.setPosition(x, y);
    }
}
