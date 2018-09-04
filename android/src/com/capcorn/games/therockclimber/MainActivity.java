package com.capcorn.games.therockclimber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.capcorn.games.therockclimber.characters.CharacterSelectorActivity;
import com.capcorn.games.therockclimber.settings.SettingsActivity;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by kprotasov on 03.06.2017.
 */

public class MainActivity extends Activity {

    private VideoView videoView;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        this.setContentView(R.layout.main_activity);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        videoView = findViewById(R.id.videoView);
        final String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.background_test;
        final Uri videoUri = Uri.parse(videoPath);
        final Typeface typeFace = Typeface.createFromAsset(getAssets(),AppSettings.APPLICATION_TYPEFACE);

        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        final TextView gameTitle = findViewById(R.id.game_title);
        gameTitle.setTypeface(typeFace);

        final TextView startButton = findViewById(R.id.start_button);
        startButton.setTypeface(typeFace);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                videoView.pause();
                final Intent gameIntent = new Intent(MainActivity.this, AndroidLauncher.class);
                startActivity(gameIntent);
            }
        });

        final TextView charactersButton = findViewById(R.id.character_button);
        charactersButton.setTypeface(typeFace);
        charactersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent characterIntent = new Intent(MainActivity.this, CharacterSelectorActivity.class);
                startActivity(characterIntent);
            }
        });

        final TextView settingsButton = findViewById(R.id.settings_button);
        settingsButton.setTypeface(typeFace);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }

}
