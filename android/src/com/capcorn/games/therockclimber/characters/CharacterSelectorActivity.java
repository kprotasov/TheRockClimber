package com.capcorn.games.therockclimber.characters;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capcorn.games.therockclimber.AppSettings;
import com.capcorn.games.therockclimber.R;
import com.capcorn.settings.ApplicationConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;

/**
 * User: kprotasov
 * Date: 04.11.2017
 * Time: 15:33
 */

public class CharacterSelectorActivity extends Activity implements RewardedVideoAdListener{

    private static final String STATE_SELECT = "com.capcorn.games.therockclimber.characters.STATE_SELECT";
    private static final String STATE_SELECTED = "com.capcorn.games.therockclimber.characters.STATE_SELECTED";
    private static final String STATE_BUY = "com.capcorn.games.therockclimber.characters.STATE_BUY";
    private static final String STATE_ADD_MONEY = "com.capcorn.games.therockclimber.characters.STATE_ADD_MONEY";
    private static final long REWARDED_VALUE = 100;

    private static final long LOAD_REWARDED_VIDEO_DELAY = 30 * 1000;

    private int currentBackgroundPosition = 0;
    private ArrayList<Integer> backgroundsList;

    private AppSelectedCharacterStore selectedCharacterStore;
    private AppMoneyStore appMoneyStore;
    private CharacterBuyStore characterBuyStore;
    private CharacterPagerAdapter adapter;

    private LinearLayout rootView;
    private Button selectButton;
    private TextView moneyTextView;
    private ViewPager viewPager;

    private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, ApplicationConstants.AD_MOB_APPLICATION_IDENTIFIER);
        final Typeface typeFace = Typeface.createFromAsset(getAssets(), AppSettings.APPLICATION_TYPEFACE);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideo();

        setContentView(R.layout.character_selector_activity);

        appMoneyStore = new AppMoneyStore(this);
        characterBuyStore = new CharacterBuyStore(this);

        rootView = findViewById(R.id.root_view);
        currentBackgroundPosition = 0;
        createBackgrounds();
        rootView.setBackgroundResource(backgroundsList.get(currentBackgroundPosition));

        moneyTextView = findViewById(R.id.money_text_view);
        moneyTextView.setTypeface(typeFace);
        selectButton = findViewById(R.id.select_button);
        selectButton.setTypeface(typeFace);
        selectedCharacterStore = new AppSelectedCharacterStore(this);

        setupMoneyText();

        final Characters[] characters = characterBuyStore.load();

        int selectedItemPosition = 0;

        for (Characters character : characters) {
            if (character.isSelected()) {
                break;
            }
            selectedItemPosition++;
        }

        adapter = new CharacterPagerAdapter(this, R.layout.character_selector_item, characters);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setCurrentItem(selectedItemPosition);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Characters selectedCharacter = characters[position];
                setupSelectButton(selectedCharacter);
                if (currentBackgroundPosition >= 8) {
                    currentBackgroundPosition = 0;
                } else {
                    currentBackgroundPosition++;
                }
                rootView.setBackgroundResource(backgroundsList.get(currentBackgroundPosition));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int position = viewPager.getCurrentItem();
                final Characters selectedCharacter = characters[position];
                final long totalMoney = appMoneyStore.load();

                if (selectButton.getTag().equals(STATE_SELECT)) {
                    selectAndSaveCharacter(selectedCharacter, characters);
                }
                if (selectButton.getTag().equals(STATE_BUY)) {
                    if (totalMoney >= selectedCharacter.getPrice()) {
                        selectedCharacter.setBayed(true);
                        characters[position].setBayed(true);
                        final long availableMoney = totalMoney - selectedCharacter.getPrice();
                        appMoneyStore.save(availableMoney);
                        selectAndSaveCharacter(selectedCharacter, characters);
                        setupMoneyText();
                    }
                }
                if (selectButton.getTag().equals(STATE_ADD_MONEY)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (rewardedVideoAd.isLoaded()) {
                                rewardedVideoAd.show();
                            }
                        }
                    });
                }
                setupSelectButton(selectedCharacter);
            }
        });
    }

    private void selectAndSaveCharacter(final Characters character, final Characters[] charactersArray){
        for (Characters currentCharacter : charactersArray) {
            currentCharacter.setSelected(false);
        }
        character.setSelected(true);
        selectedCharacterStore.save(character.getName());
        characterBuyStore.save(charactersArray);
        adapter.notifyDataSetChanged();
    }

    private void setupMoneyText() {
        final long totalMoney = appMoneyStore.load();
        moneyTextView.setText(getString(R.string.you_money, String.valueOf(totalMoney)));
    }

    private void setupSelectButton(Characters selectedCharacter) {
        final long userMoney = appMoneyStore.load();

        if (selectedCharacter.isSelected()) {
            selectButton.setText(getString(R.string.character_selector_button_selected));
            selectButton.setTag(STATE_SELECTED);
            selectButton.setVisibility(View.VISIBLE);
        }else if (selectedCharacter.isBayed()) {
            selectButton.setText(getString(R.string.character_selector_button_select));
            selectButton.setTag(STATE_SELECT);
            selectButton.setVisibility(View.VISIBLE);
        } else if (userMoney >= selectedCharacter.getPrice()) {
            selectButton.setText(getString(R.string.character_selector_button_buy));
            selectButton.setTag(STATE_BUY);
            selectButton.setVisibility(View.VISIBLE);
        } else if (userMoney >= selectedCharacter.getPrice() - REWARDED_VALUE
                && userMoney < selectedCharacter.getPrice() && rewardedVideoAd.isLoaded()){
            selectButton.setText(getString(R.string.character_selector_button_add_money));
            selectButton.setTag(STATE_ADD_MONEY);
            selectButton.setVisibility(View.VISIBLE);
        } else {
            selectButton.setVisibility(View.GONE);
        }
    }

    private void loadRewardedVideo() {
        //Toast.makeText(this, "start to load", Toast.LENGTH_LONG).show();
        //rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
        rewardedVideoAd.loadAd(ApplicationConstants.AD_MOB_CHARACTER_SELECTOR_IDENTIFIER, new AdRequest.Builder().build());
    }

    @Override
    public void onResume() {
        super.onResume();
        rewardedVideoAd.resume(this);
        if (viewPager != null) {
            final Characters[] characters = characterBuyStore.load();
            final Characters character = characters[viewPager.getCurrentItem()];
            setupSelectButton(character);
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //Log.v("CharacterSelectorActivity", "rewarded video loaded");
        //Toast.makeText(this, "loaded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideo();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        long storedUserMoney = appMoneyStore.load();
        appMoneyStore.save(storedUserMoney + REWARDED_VALUE);
        setupMoneyText();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        //Toast.makeText(this, "failed to load", Toast.LENGTH_LONG).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadRewardedVideo();
            }
        }, LOAD_REWARDED_VIDEO_DELAY);
    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    private void createBackgrounds() {
        backgroundsList = new ArrayList<>(9);
        backgroundsList.add(R.drawable.character_selector_bg0001);
        backgroundsList.add(R.drawable.character_selector_bg0002);
        backgroundsList.add(R.drawable.character_selector_bg0003);
        backgroundsList.add(R.drawable.character_selector_bg0004);
        backgroundsList.add(R.drawable.character_selector_bg0005);
        backgroundsList.add(R.drawable.character_selector_bg0006);
        backgroundsList.add(R.drawable.character_selector_bg0007);
        backgroundsList.add(R.drawable.character_selector_bg0008);
        backgroundsList.add(R.drawable.character_selector_bg0009);
    }

}
