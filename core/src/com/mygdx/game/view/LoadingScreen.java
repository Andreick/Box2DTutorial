package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.controller.MainClass;

public class LoadingScreen implements Screen {

    public final int IMAGE = 0;		// loading images
    public final int FONT = 1;		// loading fonts
    public final int PARTY = 2;		// loading particle effects
    public final int SOUND = 3;		// loading sounds
    public final int MUSIC = 4;		// loading music

    private int currentLoadingStage = 0;
    private float stateTime = 0f;

    // timer for exiting loading screen
    public float countDown = 5f; // 5 seconds of waiting before menu screen

    private MainClass parent;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private AtlasRegion title;
    private AtlasRegion dash;

    private Animation<TextureRegion> flameAnimation;

    public LoadingScreen(MainClass mainClass) {
        parent = mainClass;
        batch = new SpriteBatch();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    private void drawLoadingBar(int stage, TextureRegion currentFrame) {
        for (int i = 0; i < stage; i++) {
            batch.draw(currentFrame, 50 + (i * 50), 150,50,50);
            //batch.draw(dash, 35 + (i * 50), 140, 80, 80);
        }
    }

    @Override
    public void show() {
        parent.asstMng.queueAddLoadingImages();
        parent.asstMng.manager.finishLoading();

        atlas = parent.asstMng.manager.get("images/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");

        flameAnimation = new Animation<TextureRegion>(0.07f, atlas.findRegions("flames"), PlayMode.LOOP);

        parent.asstMng.queueAddImages();
        System.out.println("Loading images...");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta; // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = flameAnimation.getKeyFrame(stateTime, true);

        batch.begin();
        drawLoadingBar(currentLoadingStage * 2, currentFrame);
        batch.draw(title, 135, 250);
        batch.end();

        // check if the asset manager has finished loading
        if (parent.asstMng.manager.update()) { // Load some, will return true if done loading
            currentLoadingStage += 1;
            switch (currentLoadingStage) {
                case FONT:
                    System.out.println("Loading fonts....");
                    parent.asstMng.queueAddFonts();
                    break;
                case PARTY:
                    System.out.println("Loading particle effects....");
                    parent.asstMng.queueAddParticleEffects();
                    break;
                case SOUND:
                    System.out.println("Loading sounds....");
                    parent.asstMng.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading musics....");
                    parent.asstMng.queueAddMusic();
                    break;
                case 5:
                    System.out.println("Finished");
                    break;
            }
            if (currentLoadingStage > 5) {
                countDown -= delta;  // timer to stay on loading screen for short period once done loading
                currentLoadingStage = 5;  // cap loading stage to 5 as will use later to display progress bar and more than 5 would go off the screen
                if (countDown < 0) { // countdown is complete
                    parent.changeScreen(MainClass.MENU);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //parent.dispose();
    }
}
