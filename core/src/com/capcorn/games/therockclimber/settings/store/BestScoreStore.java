package com.capcorn.games.therockclimber.settings.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Map;

/**
 * User: kprotasov
 * Date: 01.10.2017
 * Time: 11:58
 */

public class BestScoreStore {

    private static final String BEST_SCORE_PREFERENCES_NAME = "com.capcorn.games.therockclimber.settings.store.BEST_SCORE_PREFERENCES_NAME";
    private static final String BEST_SCORE_KEY = "com.capcorn.games.therockclimber.settings.store.BEST_SCORE_KEY";

    public long getBestScore() {
        final Preferences preferences = Gdx.app.getPreferences(BEST_SCORE_PREFERENCES_NAME);
        // не понятное поведение. при удалении приложения первый раз возвращается Integer вместо Long. Временно решено нижеследующим способом
        try {
            return preferences.getLong(BEST_SCORE_KEY, 0L);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void setBestScore(final long score) {
        Gdx.app.log("BestScoreStore", "set best score " + score);
        final Preferences preferences = Gdx.app.getPreferences(BEST_SCORE_PREFERENCES_NAME);
        preferences.putLong(BEST_SCORE_KEY, score);
        preferences.flush();
    }

    public void removeScore() {
        final Preferences preferences = Gdx.app.getPreferences(BEST_SCORE_PREFERENCES_NAME);
        preferences.remove(BEST_SCORE_KEY);
        preferences.flush();
    }

}
