package com.capcorn.games.therockclimber.characters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public int getItemPosition(@NonNull final Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(layoutId, container, false);

        final ImageView imageView = viewGroup.findViewById(R.id.imageView);
        final TextView priceText = viewGroup.findViewById(R.id.priceText);
        final TextView nameText = viewGroup.findViewById(R.id.nameText);

        final Characters character = characters[position];

        if (character.isBayed()) {
            imageView.setImageResource(character.getImageResourceId());
            priceText.setVisibility(View.INVISIBLE);
        } else {
            imageView.setImageResource(character.getNotBuyedResourceId());
            priceText.setVisibility(View.VISIBLE);
        }
        priceText.setText(String.valueOf(character.getPrice()));
        nameText.setText(character.getCharacterName());

        container.addView(viewGroup);
        return viewGroup;
    }

    @Override
    public void destroyItem(@NonNull final ViewGroup container, int position, @NonNull final Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    private boolean isItemEven(final int position) {
        return position % 2 == 0;
    }

}
