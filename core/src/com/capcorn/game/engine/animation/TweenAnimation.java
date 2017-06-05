package com.capcorn.game.engine.animation;

import com.badlogic.gdx.Gdx;

/**
 * Created by kprotasov on 07.02.2016.
 */
public class TweenAnimation {

    private static final String STATE_PLAYED = "statePlayed";
    private static final String STATE_PAUSED = "statePaused";
    private String state;
    private float x;
    private float y;
    private float speedX;
    private float speedY;
    private float destX;
    private float destY;
    private OnAnimationFinishListener listener;

    public TweenAnimation(){
        this.state = STATE_PAUSED;
    }

    public TweenAnimation(final float fromX, final float fromY, final float destX, final float destY, final float speed){
        restart(fromX, fromY, destX, destY, speed);
    }

    public void restart(final float fromX, final float fromY, final float destX, final float destY, final float speed){
        this.state = STATE_PAUSED;
        this.x = fromX;
        this.y = fromY;
        //Для того чтобы приращение по x и по у было одинаково пересчитываем скорость так что большее расстояние будет большей скоростью а меньшее меньшей
        final float destXScale = Math.abs(destX - fromX);
        final float destYScale = Math.abs(destY - fromY);
        if (destXScale >= destYScale){
            this.speedX = speed;
            this.speedY = speed * (destYScale / destXScale);
        }else{
            this.speedX = speed * (destXScale / destYScale);
            this.speedY = speed;
        }
        if (destX < fromX){
            this.speedX = -speedX;
        }
        if (destY < fromY){
            this.speedY = -speedY;
        }
        this.destX = destX;
        this.destY = destY;
    }

    public void setOnAnimationFinishListener(final OnAnimationFinishListener listener){
        this.listener = listener;
    }

    public void removeOnAnimationFinishListener() {
        this.listener = null;
    }

    public void start(){
        this.state = STATE_PLAYED;
    }

    public void stop(){
        this.state = STATE_PAUSED;
    }

    public void update(final float delta){
        if (state.equals(STATE_PLAYED)){
            float nextX = x + speedX * delta;
            float nextY = y + speedY * delta;

            if (speedX >= 0){
                if (nextX >= destX){
                    nextX = destX;
                }else{
                    x = nextX;
                }
            }else if (speedX < 0){
                if (nextX <= destX){
                    nextX = destX;
                }else{
                    x = nextX;
                }
            }
            if (speedY >= 0){
                if (nextY >= destY){
                    nextY = destY;
                }else{
                    y = nextY;
                }
            }else if (speedY < 0){
                if (nextY <= destY){
                    nextY = destY;
                }else{
                    y = nextY;
                }
            }
            if (nextX == destX && nextY == destY){
                state = STATE_PAUSED;
                if (listener != null){
                    listener.onAnimationFinish(destX, destY);
                }
                Gdx.app.log("TweenAnimation", "state paused");
            }
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getState() {
        return state;
    }

    public interface OnAnimationFinishListener{
        void onAnimationFinish(final float destX, final float destY);
    }
}