package com.capcorn.games.therockclimber.settings.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.capcorn.games.therockclimber.graphics.AnimatedResourceNames;

/**
 * User: kprotasov
 * Date: 01.10.2017
 * Time: 11:58
 */


public class SelectedGameCharacterStore {

    public static final String FILE_SELECTED_CHARACTER = "com.capcorn.games.therockclimber.settings.store.FILE_SELECTED_CHARACTER";
    public static final String RECORD_SAVED_CHARACTER = "com.capcorn.games.therockclimber.settings.store.RECORD_SAVED_CHARACTER";

    public String getSelectedCharacter() {
        final Preferences preferences = Gdx.app.getPreferences(FILE_SELECTED_CHARACTER);
        return preferences.getString(RECORD_SAVED_CHARACTER, AnimatedResourceNames.BASE_CHARACTER.getName());
    }

    public void setSelectedCharacter(final String characterName) {
        final Preferences preferences = Gdx.app.getPreferences(FILE_SELECTED_CHARACTER);
        preferences.putString(RECORD_SAVED_CHARACTER, characterName);
        preferences.flush();
    }

}
