package com.github.ybq.android.spinkit.style;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

public class MultiplePulse extends SpriteContainer {
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Pulse(), new Pulse(), new Pulse()};
    }

    public void onChildCreated(Sprite... sprites) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].setAnimationDelay((i + 1) * 200);
        }
    }
}
