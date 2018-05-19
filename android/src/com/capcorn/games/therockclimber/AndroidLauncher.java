package com.capcorn.games.therockclimber;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.capcorn.settings.ApplicationConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener, OnShowRewardedVideoListener {

	private enum RewardedType {

		CONTINUE,
		X_2

	}

	private RewardedVideoAd rewardedVideoAd;
	private MainGame mainGame;

	private RewardedType currentRewardedType = RewardedType.CONTINUE;

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
		Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();
	}

	private void loadRewardedVideo() {
		rewardedVideoAd.loadAd(/*ApplicationConstants.AD_MOB_GAME_SCREEN_IDENTIFIER*/
				"ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
		Toast.makeText(this, "start load rewarded video", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		loadRewardedVideo();
		Toast.makeText(this, "onRewarded", Toast.LENGTH_LONG).show();
		if (currentRewardedType.equals(RewardedType.CONTINUE)) {
			mainGame.onContinueVideoRewarded();
		} else if (currentRewardedType.equals(RewardedType.X_2)) {
			mainGame.onX2VideoRewarded();
		}
		//Toast.makeText(this, "onRewarded type " + currentRewardedType, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		if (mainGame != null && rewardedVideoAd.isLoaded()) {
			mainGame.onRewardedVideoLoaded();
		}
		Toast.makeText(this, "rewarded video loaded", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		rewardedVideoAd.setRewardedVideoAdListener(this);
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
		Toast.makeText(this, "onRewardedVideoClosed", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {

	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
		if (mainGame != null) {
			mainGame.onRewardedVideoUnloaded();
		}
		//loadRewardedVideo();
		Toast.makeText(this, "onRewardedVideoFailedToLoad", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onShowRewardedVideoForContinue() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (rewardedVideoAd.isLoaded()) {
					rewardedVideoAd.show();
					currentRewardedType = RewardedType.CONTINUE;
				}
			}
		});
	}

	@Override
	public void onShowRewardedVideoForX2() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (rewardedVideoAd.isLoaded()) {
					rewardedVideoAd.show();
					currentRewardedType = RewardedType.X_2;
				}
			}
		});
	}
}
