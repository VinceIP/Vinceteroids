package com.vinceteroids.game.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.vinceteroids.game.Vinceteroids;
import com.vinceteroids.game.entity.KeyListener;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    public final int UP = Input.Keys.W;
    public final int LEFT = Input.Keys.A;
    public final int RIGHT = Input.Keys.D;
    public final int FIRE = Input.Keys.SPACE;
    public final int WARP = Input.Keys.F;
    public final int ANY_KEY = Input.Keys.ANY_KEY;

    private Vinceteroids game;

    public List<KeyListener> listeners;

    public InputHandler() {
        game = Vinceteroids.get();
        listeners = new ArrayList<>();
    }

    public void registerListener(KeyListener object) {
        listeners.add(object);
    }

    public void getKeyPress() {
        int key = 0;
        if (game.getGameHandler() != null) {
            if (!game.getGameHandler().isGameOver()) {
                if (Gdx.input.isKeyPressed(LEFT)) key = LEFT;
                else if (Gdx.input.isKeyPressed(RIGHT)) key = RIGHT;
                else if (Gdx.input.isKeyPressed(UP)) key = UP;
                if (Gdx.input.isKeyJustPressed(FIRE)) key = FIRE;
            } else {
                if (Gdx.input.isKeyJustPressed(ANY_KEY)) key = ANY_KEY;
            }

        }
        keyDown(key);

    }

    public void keyDown(int keycode) {
        for (KeyListener e : listeners) {
            e.keyEvent(keycode);
        }
    }
}