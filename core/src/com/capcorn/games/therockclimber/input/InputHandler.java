package com.capcorn.games.therockclimber.input;

import com.badlogic.gdx.InputProcessor;

/**
 * User: kprotasov
 * Date: 06.05.2016
 * Time: 23:38
 */
public class InputHandler implements InputProcessor {

    private OnTouchListener onTouchListener;

    public InputHandler(final OnTouchListener onTouchListener){
        this.onTouchListener = onTouchListener;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (onTouchListener != null) {
            onTouchListener.onTouchDown(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (onTouchListener != null) {
            onTouchListener.onTouchUp();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
