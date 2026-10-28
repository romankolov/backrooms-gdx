package com.backrooms.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Small persisted settings blob. */
public final class GameSettings {

    private static final String NAME = "backrooms-settings";

    public float sensitivity = 1.0f;
    public int renderDistance = 3;
    public boolean audio = true;
    public boolean torch = true;
    public boolean showDebug = false;
    /** VHS camcorder pass: scanlines, chroma bleed, tracking drift, OSD and tape hiss. */
    public boolean vhs = true;
    /** The thing in the corridors. Off means a completely empty level. */
    public boolean entity = true;
    public int quality = 1;          // 0 = low, 1 = high

    private Preferences prefs;

    public void load() {
        prefs = Gdx.app.getPreferences(NAME);
        sensitivity = prefs.getFloat("sensitivity", 1.0f);
        renderDistance = prefs.getInteger("renderDistance", 3);
        audio = prefs.getBoolean("audio", true);
        torch = prefs.getBoolean("torch", true);
        showDebug = prefs.getBoolean("showDebug", false);
        vhs = prefs.getBoolean("vhs", true);
        entity = prefs.getBoolean("entity", true);
        quality = prefs.getInteger("quality", 1);
    }

    public void save() {
        if (prefs == null) {
            return;
        }
        prefs.putFloat("sensitivity", sensitivity);
        prefs.putInteger("renderDistance", renderDistance);
        prefs.putBoolean("audio", audio);
        prefs.putBoolean("torch", torch);
        prefs.putBoolean("showDebug", showDebug);
        prefs.putBoolean("vhs", vhs);
        prefs.putBoolean("entity", entity);
        prefs.putInteger("quality", quality);
        prefs.flush();
    }
}
