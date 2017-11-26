package com.capcorn.games.therockclimber.characters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.capcorn.games.therockclimber.R;

/**
 * User: kprotasov
 * Date: 06.11.2017
 * Time: 13:54
 */

public class CharacterSelectorAdapter extends ArrayAdapter<Characters>{

    private final int resourceId;
    private final Characters[] objects;
    private final LayoutInflater inflater;

    private ViewHolder viewHolder;

    public CharacterSelectorAdapter(Context context, int resource, Characters[] objects) {
        super(context, resource, objects);

        this.inflater = LayoutInflater.from(context);
        this.resourceId = resource;
        this.objects = objects;
    }

    static class ViewHolder {
        private ImageView imageView;
        private ImageButton checkButton;
        private TextView priceTextView;
        private TextView characterName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            if (convertView == null) {
                convertView = inflater.inflate(resourceId, null);

                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                //viewHolder.checkButton = (ImageButton) convertView.findViewById(R.id.item_check);
                //viewHolder.priceTextView = (TextView) convertView.findViewById(R.id.price_test_view);
                //viewHolder.characterName = (TextView) convertView.findViewById(R.id.character_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final Characters character = getItem(position);
            viewHolder.imageView.setImageResource(character.getImageResourceId());
            //viewHolder.priceTextView.setText(String.valueOf(character.getPrice()));
            //viewHolder.characterName.setText(character.getCharacterName());
            /*if (character.isSelected()) {
                viewHolder.checkButton.setVisibility(View.VISIBLE);
            } else {
                viewHolder.checkButton.setVisibility(View.GONE);
            }*/

        }catch (final Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
