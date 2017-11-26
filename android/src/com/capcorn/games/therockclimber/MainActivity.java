package com.capcorn.games.therockclimber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.capcorn.games.therockclimber.characters.CharacterSelectorActivity;

/**
 * Created by kprotasov on 03.06.2017.
 */

public class MainActivity extends Activity{

    private VideoView videoView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main_activity);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        videoView = (VideoView)findViewById(R.id.videoView);
        final String videoPath =  "android.resource://" + getPackageName() + "/" + R.raw.background_test;
        final Uri videoUri = Uri.parse(videoPath);

        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        final TextView startButton = (TextView) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                videoView.pause();
                final Intent gameIntent = new Intent(MainActivity.this, AndroidLauncher.class);
                startActivity(gameIntent);
            }
        });

        final TextView charactersButton = (TextView) findViewById(R.id.character_button);
        charactersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent characterIntent = new Intent(MainActivity.this, CharacterSelectorActivity.class);
                startActivity(characterIntent);
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
