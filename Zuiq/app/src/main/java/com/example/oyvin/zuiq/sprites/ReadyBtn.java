package com.example.oyvin.zuiq.sprites;

import com.example.oyvin.zuiq.states.GameState;

import sheep.graphics.Image;

public class ReadyBtn extends Button {
    public ReadyBtn(Image image) {
        super(image, 0.33f, 0.33f);
    }


    protected void onTouchDownSprite() {
        GameState.getInstance().setState("q");
        GameState.getInstance().getController().play();
    }
}


