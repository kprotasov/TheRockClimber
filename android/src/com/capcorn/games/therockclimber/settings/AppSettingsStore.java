package com.capcorn.games.therockclimber.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static com.capcorn.games.therockclimber.settings.store.SettingsStore.SETTINGS_PREFERENCES_NAME;
import static com.capcorn.games.therockclimber.settings.store.SettingsStore.MUSIC_ENABLED__KEY;
import static com.capcorn.games.therockclimber.settings.store.SettingsStore.SOUND_ENABLED_KEY;
import static com.capcorn.games.therockclimber.settings.store.SettingsStore.VIBRATION_ENABLED_KEY;


/**
 * User: kprotasov
 * Date: 28.08.2018
 * Time: 22:07
 */

public class AppSettingsStore {

    private final Context context;

    public AppSettingsStore(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(SETTINGS_PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    public boolean isMusicEnabled() {
        return getSharedPreferences().getBoolean(MUSIC_ENABLED__KEY, true);
    }

    public void setMusicEnabled(boolean isEnabled) {
        getSharedPreferences().edit().putBoolean(MUSIC_ENABLED__KEY, isEnabled).apply();
    }

    public boolean isSoundEnabled() {
        return getSharedPreferences().getBoolean(SOUND_ENABLED_KEY, true);
    }

    public void setSoundEnabled(boolean isEnabled) {
        getSharedPreferences().edit().putBoolean(SOUND_ENABLED_KEY, isEnabled).apply();
    }

    public boolean isVibrationEnabled() {
        return getSharedPreferences().getBoolean(VIBRATION_ENABLED_KEY, true);
    }

    public void setVibrationEnabled(boolean isEnabled) {
        getSharedPreferences().edit().putBoolean(VIBRATION_ENABLED_KEY, isEnabled).apply();
    }

}
