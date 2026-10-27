package com.backrooms.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/** Temporary bootstrap; replaced by the real game implementation. */
public class BackroomsGame extends ApplicationAdapter {
    @Override
    public void create() {
        Gdx.gl.glClearColor(0.75f, 0.72f, 0.45f, 1f);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
}
