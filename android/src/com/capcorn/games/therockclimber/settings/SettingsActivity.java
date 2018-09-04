package com.capcorn.games.therockclimber.settings;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.capcorn.games.therockclimber.AppSettings;
import com.capcorn.games.therockclimber.R;

/**
 * User: kprotasov
 * Date: 26.08.2018
 * Time: 1:59
 */

public class SettingsActivity extends Activity {

    private AppSettingsStore appSettingsStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        appSettingsStore = new AppSettingsStore(this);

        final Typeface typeFace = Typeface.createFromAsset(getAssets(), AppSettings.APPLICATION_TYPEFACE);

        TextView settingsTitle = findViewById(R.id.settings_title);
        settingsTitle.setTypeface(typeFace);

        TextView settingsSound = findViewById(R.id.settings_sound);
        settingsSound.setTypeface(typeFace);

        TextView settingsMusic = findViewById(R.id.settings_music);
        settingsMusic.setTypeface(typeFace);

        TextView settingsVibration = findViewById(R.id.settings_vibration);
        settingsVibration.setTypeface(typeFace);

        Switch soundSwitch = findViewById(R.id.soundSwitch);
        soundSwitch.setChecked(appSettingsStore.isSoundEnabled());
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appSettingsStore.setSoundEnabled(isChecked);
            }
        });

        Switch musicSwitch = findViewById(R.id.musicSwitch);
        musicSwitch.setChecked(appSettingsStore.isMusicEnabled());
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appSettingsStore.setMusicEnabled(isChecked);
            }
        });

        Switch vibrationSwitch = findViewById(R.id.vibrationSwitch);
        vibrationSwitch.setChecked(appSettingsStore.isVibrationEnabled());
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appSettingsStore.setVibrationEnabled(isChecked);
            }
        });
    }

}
