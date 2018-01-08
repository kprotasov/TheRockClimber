package com.capcorn.games.therockclimber.characters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.capcorn.games.therockclimber.R;
import com.capcorn.games.therockclimber.rewardedvideo.RewardedVideoActivity;

/**
 * User: kprotasov
 * Date: 04.11.2017
 * Time: 15:33
 */

public class CharacterSelectorActivity extends Activity {

    private CharacterSelectorAdapter adapter;

    private AppSelectedCharacterStore store;
    private AppMoneyStore appMoneyStore;

    //private ListView listView;
    private Button selectButton;
    private TextView moneyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.character_selector_activity);

        appMoneyStore = new AppMoneyStore(this);

        moneyTextView = (TextView) findViewById(R.id.money_text_view);
        //listView = (ListView) findViewById(R.id.characters_list_view);
        selectButton = (Button) findViewById(R.id.select_button);
        store = new AppSelectedCharacterStore(this);

        final long totalMoney = appMoneyStore.load();
        moneyTextView.setText(getString(R.string.you_money, String.valueOf(totalMoney)));

        final Characters[] characters = Characters.values();
        final String selectedCharacterName = store.load();
        for (Characters character : characters) {
            if (selectedCharacterName.equals(character.getName())) {
                character.setSelected(true);
            } else {
                character.setSelected(false);
            }
        }

        adapter = new CharacterSelectorAdapter(this, R.layout.character_selector_item, characters);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        //viewPager.setAdapter(adapter);
        /*listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Characters selectedCharacter = (Characters) parent.getItemAtPosition(position);
                for (Characters character : characters) {
                    if (selectedCharacter.getName().equals(character.getName())) {
                        character.setSelected(true);
                    } else {
                        character.setSelected(false);
                    }
                }
                adapter.notifyDataSetInvalidated();
                // TODO: 16.11.2017 for test
                store.save(selectedCharacter.getName());
            }
        });*/

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(CharacterSelectorActivity.this, RewardedVideoActivity.class));
            }
        });

        /*listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/
    }

}
