package com.capcorn.games.therockclimber;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.capcorn.settings.ApplicationConstants;
import com.capcorn.settings.StringsResources;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static com.capcorn.settings.ApplicationConstants.AD_MOB_RESTART_INTERSTITIAL_IDENTIFIER;

public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener, OnShowAdListener {

	private enum RewardedType {

		CONTINUE,
		X_2

	}

	private static final long LOAD_REWARDED_VIDEO_DELAY = 30 * 1000;
	private static final long LOAD_INTERSTITIAL_AD_DELAY = 10 * 1000;

	private RewardedVideoAd rewardedVideoAd;
	private InterstitialAd interstitialAd;
	private MainGame mainGame;

	private RewardedType currentRewardedType = RewardedType.CONTINUE;

	private StringsResources stringsResources;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initStringsResources();

		mainGame = new MainGame(this, stringsResources);
		initialize(mainGame, config);

		MobileAds.initialize(this, ApplicationConstants.AD_MOB_APPLICATION_IDENTIFIER);

		rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		rewardedVideoAd.setRewardedVideoAdListener(this);

		interstitialAd = new InterstitialAd(this);
		//interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");// for test
		interstitialAd.setAdUnitId(AD_MOB_RESTART_INTERSTITIAL_IDENTIFIER);
		interstitialAd.loadAd(new AdRequest.Builder().build());

		createInterstitialListener();

		loadRewardedVideo();
	}

	private void initStringsResources() {
		stringsResources = new StringsResources();
		stringsResources.setClickToStart(getString(R.string.game_click_to_stare));
		stringsResources.setScore(getString(R.string.game_dialog_score));
		stringsResources.setBestScore(getString(R.string.game_dialog_best_score));
		stringsResources.setMoney(getString(R.string.game_dialog_money));
		stringsResources.setRestartButton(getString(R.string.game_dialog_restart));
		stringsResources.setContinueButton(getString(R.string.game_dialog_continue));
	}

	private void createInterstitialListener() {
		interstitialAd.setAdListener(new AdListener(){
			@Override
			public void onAdClosed() {
				super.onAdClosed();
				interstitialAd.loadAd(new AdRequest.Builder().build());
			}

			@Override
			public void onAdFailedToLoad(int i) {
				super.onAdFailedToLoad(i);
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						interstitialAd.loadAd(new AdRequest.Builder().build());
					}
				}, LOAD_INTERSTITIAL_AD_DELAY);
			}
		});
	}

	private void loadRewardedVideo() {
		//rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
		rewardedVideoAd.loadAd(ApplicationConstants.AD_MOB_GAME_SCREEN_IDENTIFIER, new AdRequest.Builder().build());
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		if (mainGame != null) {
			mainGame.onRewardedVideoUnloaded();
		}
		if (currentRewardedType.equals(RewardedType.CONTINUE)) {
			mainGame.onContinueVideoRewarded();
		} else if (currentRewardedType.equals(RewardedType.X_2)) {
			mainGame.onX2VideoRewarded();
		}
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		if (mainGame != null && rewardedVideoAd.isLoaded()) {
			mainGame.onRewardedVideoLoaded();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		rewardedVideoAd.setRewardedVideoAdListener(this);
		rewardedVideoAd.resume(this);
		if (mainGame != null) {
			if (rewardedVideoAd.isLoaded()) {
				mainGame.onRewardedVideoLoaded();
			} else {
				mainGame.onRewardedVideoUnloaded();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		rewardedVideoAd.pause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		rewardedVideoAd.destroy(this);
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
		// do nothing
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

	@Override
	public void onShowInterstitialAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (interstitialAd.isLoaded()) {
					interstitialAd.show();
				}
			}
		});
	}
}
