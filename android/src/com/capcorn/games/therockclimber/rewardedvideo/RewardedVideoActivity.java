package com.capcorn.games.therockclimber.rewardedvideo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.capcorn.games.therockclimber.R;
import com.capcorn.settings.ApplicationConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/**
 * User: kprotasov
 * Date: 02.12.2017
 * Time: 23:16
 */

public class RewardedVideoActivity extends Activity implements RewardedVideoAdListener {

    private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rewarded_video_activity);
        MobileAds.initialize(this, ApplicationConstants.AD_MOB_APPLICATION_IDENTIFIER);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideo();
    }

    private void loadRewardedVideo() {
        rewardedVideoAd.loadAd(ApplicationConstants.AD_MOB_CHARACTER_SELECTOR_IDENTIFIER, new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        // reload
        Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
        }
    }

    @Override
    protected void onResume() {
        rewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        rewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        rewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        // reload
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        // reload
    }
}
