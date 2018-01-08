package com.capcorn.games.therockclimber;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.capcorn.games.therockclimber.MainGame;
import com.capcorn.settings.ApplicationConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener, OnShowRewardedVideoListener {

	private RewardedVideoAd rewardedVideoAd;
	private MainGame mainGame;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		mainGame = new MainGame(this);
		initialize(mainGame, config);

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
		loadRewardedVideo();
		Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
				rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		if (mainGame != null && rewardedVideoAd.isLoaded()) {
			mainGame.onRewardedVideoLoaded();
		}
	}

	@Override
	protected void onResume() {
		rewardedVideoAd.resume(this);
		if (mainGame != null) {
			if (rewardedVideoAd.isLoaded()) {
				mainGame.onRewardedVideoLoaded();
			} else {
				mainGame.onRewardedVideoUnloaded();
			}
		}
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
		if (mainGame != null) {
			mainGame.onRewardedVideoUnloaded();
		}
		loadRewardedVideo();
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {

	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
		if (mainGame != null) {
			mainGame.onRewardedVideoUnloaded();
		}
		loadRewardedVideo();
	}

	@Override
	public void onShowRewardedVideo() {
		if (rewardedVideoAd.isLoaded()) {
			rewardedVideoAd.show();
		}
	}
}
