package com.capcorn.games.therockclimber.settings.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * User: kprotasov
 * Date: 03.05.2018
 * Time: 12:56
 */

public class ShowedRewardedVideoStore {

    public enum RewardedVideoType {

        NOTHING_SHOWED,
        CONTINUE_SHOWED,
        X2_SHOWED;

        static RewardedVideoType getByName(final String name) {
            for (RewardedVideoType type : RewardedVideoType.values()) {
                if (type.name().equals(name)) {
                    return type;
                }
            }
            return RewardedVideoType.NOTHING_SHOWED;
        }

    }

    private static final String SHOWED_REWARDED_VIDEO_PREFERENCES_NAME = "com.capcorn.games.therockclimber.settings.store.SHOWED_REWARDED_VIDEO_PREFERENCES_NAME";
    private static final String SHOWED_REWARDED_VIDEO_KEY = "com.capcorn.games.therockclimber.settings.store.SHOWED_REWARDED_VIDEO_KEY";

    public RewardedVideoType getShowedRewardedVideoType() {
        final Preferences preferences = Gdx.app.getPreferences(SHOWED_REWARDED_VIDEO_PREFERENCES_NAME);
        final String showedTypeName = preferences.getString(SHOWED_REWARDED_VIDEO_KEY, RewardedVideoType.NOTHING_SHOWED.name());
        return RewardedVideoType.getByName(showedTypeName);
    }

    public void setShowedRewardedVideoType(final RewardedVideoType type) {
        final Preferences preferences = Gdx.app.getPreferences(SHOWED_REWARDED_VIDEO_PREFERENCES_NAME);
        preferences.putString(SHOWED_REWARDED_VIDEO_KEY, type.name());
        preferences.flush();
    }

}
