package com.capcorn.games.therockclimber;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.capcorn.games.therockclimber.characters.Characters;

/**
 * User: kprotasov
 * Date: 19.11.2017
 * Time: 23:15
 */

public class CharacterPagerAdapter extends PagerAdapter {

    private final Context context;
    private final Characters[] characters;

    public CharacterPagerAdapter(Context context, Characters[] characters) {
        this.context = context;
        this.characters = characters;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
