package com.capcorn.games.therockclimber.sound;

import com.badlogic.gdx.audio.Music;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;
import com.capcorn.games.therockclimber.settings.store.SettingsStore;

/**
 * User: kprotasov
 * Date: 21.07.2018
 * Time: 18:13
 */

public class MusicManager {

    enum State {
        PLAY,
        PAUSE,
        STOP
    }

    private AssetsLoader assetsLoader;
    private State backSound1State = State.STOP;
    private State musicWindState = State.STOP;
    private State musicStoneState = State.STOP;
    private SettingsStore settingsStore;

    public MusicManager(final AssetsLoader assetsLoader) {
        this.assetsLoader = assetsLoader;
        this.settingsStore = new SettingsStore();
        assetsLoader.getBackMusic1().setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                backSound1State = State.STOP;
            }
        });
        assetsLoader.getMusicWind().setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                musicWindState = State.STOP;
            }
        });
        assetsLoader.getMusicStone().setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                musicStoneState = State.STOP;
            }
        });
    }

    public void playSoundGold() {
        if (assetsLoader.getSoundGold() != null && settingsStore.isSoundEnabled()) {
            assetsLoader.getSoundGold().play();
        }
    }

    public void pauseSoundGold() {
        if (assetsLoader.getSoundGold() != null) {
            assetsLoader.getSoundGold().pause();
        }
    }

    public void resumeSoundGold() {
        if (assetsLoader.getSoundGold() != null && settingsStore.isSoundEnabled()) {
            assetsLoader.getSoundGold().resume();
        }
    }

    public void playSoundBrilliance() {
        if (assetsLoader.getSoundBrilliance() != null && settingsStore.isSoundEnabled()) {
            assetsLoader.getSoundBrilliance().play();
        }
    }

    public void pauseSoundBrilliance() {
        if (assetsLoader.getSoundBrilliance() != null) {
            assetsLoader.getSoundBrilliance().pause();
        }
    }

    public void resumeSoundBrilliance() {
        if (assetsLoader.getSoundBrilliance() != null && settingsStore.isSoundEnabled()) {
            assetsLoader.getSoundBrilliance().resume();
        }
    }

    public void playSoundFall() {
        if (assetsLoader.getSoundFall() != null && settingsStore.isSoundEnabled()) {
            assetsLoader.getSoundFall().play();
        }
    }

    public void pauseSoundFall() {
        if (assetsLoader.getSoundFall() != null) {
            assetsLoader.getSoundFall().pause();
        }
    }

    public void resumeSoundFall() {
        if (assetsLoader.getSoundFall() != null && settingsStore.isSoundEnabled()) {
            assetsLoader.getSoundFall().resume();
        }
    }

    public void playMusicBack1() {
        if (assetsLoader.getBackMusic1() != null && settingsStore.isMusicEnabled()) {
            assetsLoader.getBackMusic1().play();
            backSound1State = State.PLAY;
        }
    }

    public void pauseMusicBack1() {
        if (assetsLoader.getBackMusic1() != null) {
            assetsLoader.getBackMusic1().pause();
            backSound1State = State.PAUSE;
        }
    }

    public void resumeMusicBack1() {
        if (assetsLoader.getBackMusic1() != null && settingsStore.isMusicEnabled()) {
            if (backSound1State == State.PAUSE) {
                playMusicBack1();
            }
        }
    }

    public void stopMusicBack1() {
        if (assetsLoader.getBackMusic1() != null) {
            backSound1State = State.STOP;
            assetsLoader.getBackMusic1().stop();
        }
    }

    public void playMusicWind() {
        if (assetsLoader.getMusicWind() != null && settingsStore.isMusicEnabled()) {
            assetsLoader.getMusicWind().play();
            musicWindState = State.PLAY;
        }
    }

    public void pauseMusicWind() {
        if (assetsLoader.getMusicWind() != null) {
            assetsLoader.getMusicWind().pause();
            musicWindState = State.PAUSE;
        }
    }

    public void resumeMusicWind() {
        if (assetsLoader.getMusicWind() != null && settingsStore.isMusicEnabled()) {
            if (musicWindState == State.PAUSE) {
                playMusicWind();
            }
        }
    }

    public void stopMusicWind() {
        if (assetsLoader.getMusicWind() != null) {
            musicWindState = State.STOP;
            assetsLoader.getMusicWind().stop();
        }
    }

    public void playMusicStone() {
        if (assetsLoader.getMusicStone() != null && settingsStore.isMusicEnabled()) {
            assetsLoader.getMusicStone().play();
            musicStoneState = State.PLAY;
        }
    }

    public void pauseMusicStone() {
        if (assetsLoader.getMusicStone() != null) {
            assetsLoader.getMusicStone().pause();
            musicStoneState = State.PAUSE;
        }
    }

    public void resumeMusicStone() {
        if (assetsLoader.getMusicStone() != null && settingsStore.isMusicEnabled()) {
            if (musicStoneState == State.PAUSE) {
                playMusicStone();
            }
        }
    }

    public void stopMusicStone() {
        if (assetsLoader.getMusicStone() != null) {
            musicStoneState = State.STOP;
            assetsLoader.getMusicStone().stop();
        }
    }

    public void pauseAllSounds() {
        pauseSoundGold();
        pauseSoundBrilliance();
        pauseSoundFall();
        if (backSound1State == State.PLAY) {
            pauseMusicBack1();
        }
        if (musicWindState == State.PLAY) {
            pauseMusicWind();
        }
        if (musicStoneState == State.PLAY) {
            pauseMusicStone();
        }
    }

    public void resumeAllSounds() {
        if (settingsStore.isSoundEnabled()) {
            resumeSoundGold();
            resumeSoundBrilliance();
            resumeSoundFall();
            resumeMusicBack1();
            resumeMusicWind();
            resumeMusicStone();
        }
    }

}
