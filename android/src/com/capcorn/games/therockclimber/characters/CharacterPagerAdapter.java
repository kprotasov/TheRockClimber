package com.capcorn.games.therockclimber.characters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.capcorn.games.therockclimber.R;

/**
 * Created by DCOR on 27.11.2017.
 */

public class CharacterPagerAdapter extends PagerAdapter {

    private int layoutId;
    private Context context;
    private Characters[] characters;

    public CharacterPagerAdapter(final Context context, final int layoutId, final Characters[] characters) {
        this.context = context;
        this.layoutId = layoutId;
        this.characters = characters;
    }

    @Override
    public int getCount() {
        return characters.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(layoutId, container, false);

        final ImageView imageView = (ImageView) viewGroup.findViewById(R.id.imageView);
        final Characters character = characters[position];
        imageView.setImageResource(character.getImageResourceId());

        container.addView(viewGroup);
        return viewGroup;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
