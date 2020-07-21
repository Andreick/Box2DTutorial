package com.mygdx.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyboardController implements InputProcessor {
    public boolean up, left, down, right;
    public boolean buttonLeft, buttonRight, buttonMiddle;
    public boolean dragging;
    public Vector2 mouseLocation = new Vector2();

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.UP:
                up = true;
                keyProcessed = true;
                break;
            case Input.Keys.LEFT:
                left = true;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                down = true;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                right = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.UP:
                up = false;
                keyProcessed = true;
                break;
            case Input.Keys.LEFT:
                left = false;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                down = false;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                right = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            buttonLeft = true;
        } else if (button == Input.Buttons.RIGHT) {
            buttonRight = true;
        } else if (button == Input.Buttons.MIDDLE) {
            buttonMiddle = true;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        dragging = false;
        if (button == Input.Buttons.LEFT) {
            buttonLeft = false;
        } else if (button == Input.Buttons.RIGHT) {
            buttonRight = false;
        } else if (button == Input.Buttons.MIDDLE) {
            buttonMiddle = false;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragging = true;
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
