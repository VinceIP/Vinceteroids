package com.vinceteroids.game.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.vinceteroids.game.entity.Entity;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class InputHandler{

    public final int UP = Input.Keys.W;
    public final int DOWN = Input.Keys.S;
    public final int LEFT = Input.Keys.A;
    public final int RIGHT = Input.Keys.D;
    public final int FIRE = Input.Keys.SPACE;
    public final int WARP = Input.Keys.F;

    public List<Entity> listeners;

    public InputHandler() {
        listeners = new ArrayList<>();
    }

    public void registerListener(Entity entity) {
        listeners.add(entity);
    }

    public void getKeyPress(){
        int key = 0;
        if(Gdx.input.isKeyPressed(LEFT)) key = LEFT;
        else if(Gdx.input.isKeyPressed(RIGHT)) key = RIGHT;
        else if(Gdx.input.isKeyPressed(UP)) key = UP;
        keyDown(key);
    }
    public void keyDown(int keycode) {
        for (Entity e : listeners) {
            e.keyEvent(keycode);
        }
    }
}