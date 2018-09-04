package com.capcorn.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * User: kprotasov
 * Date: 30.08.2018
 * Time: 22:12
 */

public class StringsResources {

    private String clickToStart;
    private String score;
    private String bestScore;
    private String money;
    private String restartButton;
    private String continueButton;

    public String getClickToStart() {
        return clickToStart;
    }

    public void setClickToStart(String clickToStart) {
        this.clickToStart = clickToStart;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBestScore() {
        return bestScore;
    }

    public void setBestScore(String bestScore) {
        this.bestScore = bestScore;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRestartButton() {
        return restartButton;
    }

    public void setRestartButton(String restartButton) {
        this.restartButton = restartButton;
    }

    public String getContinueButton() {
        return continueButton;
    }

    public void setContinueButton(String continueButton) {
        this.continueButton = continueButton;
    }
}
