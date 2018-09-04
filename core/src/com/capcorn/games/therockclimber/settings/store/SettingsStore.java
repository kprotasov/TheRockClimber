package com.capcorn.games.therockclimber.settings.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * User: kprotasov
 * Date: 26.08.2018
 * Time: 19:54
 */

public class SettingsStore {

    public static final String SETTINGS_PREFERENCES_NAME = "com.capcorn.games.therockclimber.settings.store.SETTINGS_PREFERENCES_NAME";
    public static final String MUSIC_ENABLED__KEY = "com.capcorn.games.therockclimber.settings.store.MUSIC_ENABLED__KEY";
    public static final String SOUND_ENABLED_KEY = "com.capcorn.games.therockclimber.settings.store.SOUND_ENABLED_KEY";
    public static final String VIBRATION_ENABLED_KEY = "com.capcorn.games.therockclimber.settings.store.VIBRATION_ENABLED_KEY";

    public boolean isMusicEnabled() {
        final Preferences preferences = Gdx.app.getPreferences(SETTINGS_PREFERENCES_NAME);
        return preferences.getBoolean(MUSIC_ENABLED__KEY, true);
    }

    public void setMusicEnabled(boolean isEnabled) {
        final Preferences preferences = Gdx.app.getPreferences(SETTINGS_PREFERENCES_NAME);
        preferences.putBoolean(MUSIC_ENABLED__KEY, isEnabled);
        preferences.flush();
    }

    public boolean isSoundEnabled() {
        final Preferences preferences = Gdx.app.getPreferences(SETTINGS_PREFERENCES_NAME);
        return preferences.getBoolean(SOUND_ENABLED_KEY, true);
    }

    public void setSoundEnabled(boolean isEnabled) {
        final Preferences preferences = Gdx.app.getPreferences(SETTINGS_PREFERENCES_NAME);
        preferences.putBoolean(SOUND_ENABLED_KEY, isEnabled);
        preferences.flush();
    }

    public boolean isVibrationEnabled() {
        final Preferences preferences = Gdx.app.getPreferences(SETTINGS_PREFERENCES_NAME);
        return preferences.getBoolean(VIBRATION_ENABLED_KEY, true);
    }

    public void setVibrationEnabled(boolean isEnabled) {
        final Preferences preferences = Gdx.app.getPreferences(SETTINGS_PREFERENCES_NAME);
        preferences.putBoolean(VIBRATION_ENABLED_KEY, isEnabled);
        preferences.flush();
    }

}
