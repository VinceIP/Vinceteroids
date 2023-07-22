package com.vinceteroids.game.entity;

import com.badlogic.gdx.math.MathUtils;

public class AsteroidGenerator {
    private static final int NUM_VERTICES = 10;
    private static final float MIN_RADIUS = 20f;
    private static final float MAX_RADIUS = 40f;

    /**
     * Generate a float array of vertices representing a randomly-shaped
     * polygon.
     * @return
     */
    public static float[] generateVertices(){
        float[] vertices = new float[NUM_VERTICES*2];

        for (int i = 0; i < NUM_VERTICES; i++) {
            //Place vertices around the polygon's center
            float angle = i * 360f / NUM_VERTICES;
            float radius = MathUtils.random(MIN_RADIUS, MAX_RADIUS);
            float x = radius * MathUtils.cosDeg(angle);
            float y = radius * MathUtils.sinDeg(angle);

            vertices[i*2] = x;
            vertices[i*2+1] = y;
        }
        return vertices;
    }
}
