package com.mygdx.game.model;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoadingBarPart extends Actor {

    private AtlasRegion image;
    private Animation<TextureRegion> flameAnimation;
    private TextureRegion currentFrame;
    private float stateTime = 0f;

    public LoadingBarPart(AtlasRegion atlasRegion, Animation<TextureRegion> animation) {
        super();
        image = atlasRegion;
        flameAnimation = animation;
        this.setWidth(30);
        this.setHeight(25);
        this.setVisible(false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(image, getX(), getY(), 30, 30);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.draw(currentFrame, getX() - 5, getY(), 40, 40);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        currentFrame = flameAnimation.getKeyFrame(stateTime, true);
    }
}
