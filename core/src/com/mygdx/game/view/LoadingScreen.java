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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.controller.MainClass;
import com.mygdx.game.model.LoadingBarPart;

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
    private Stage stage;

    private Table table, loadingTable;
    private Image titleImage, copyrightImage;

    private TextureAtlas atlas;
    private AtlasRegion title, dash, background, copyright;

    private Animation<TextureRegion> flameAnimation;

    public LoadingScreen(MainClass mainClass) {
        parent = mainClass;
        stage = new Stage(new ScreenViewport());

        loadAssets();

        // initiate queueing of images but don't start loading
        parent.asstMng.queueAddImages();
        System.out.println("Loading images...");
    }

    private void loadAssets() {
        parent.asstMng.queueAddLoadingImages();
        parent.asstMng.manager.finishLoading();

        // get images used to display loading progress
        atlas = parent.asstMng.manager.get("images/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");
        background = atlas.findRegion("flamebackground");
        copyright = atlas.findRegion("copyright");
        flameAnimation = new Animation<TextureRegion>(0.07f, atlas.findRegions("flames"), PlayMode.LOOP);
    }

    private void drawLoadingBar(int stage, TextureRegion currentFrame) {
        for (int i = 0; i < stage; i++) {
            batch.draw(currentFrame, 50 + (i * 50), 150,50,50);
            //batch.draw(dash, 35 + (i * 50), 140, 80, 80);
        }
    }

    @Override
    public void show() {
        titleImage = new Image(title);
        copyrightImage = new Image(copyright);

        table = new Table();
        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));
        //table.setDebug(true);

        loadingTable = new Table();
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        loadingTable.add(new LoadingBarPart(dash,flameAnimation));

        table.add(titleImage).align(Align.center).pad(10, 0, 0, 0).colspan(10);
        table.row();
        table.add(loadingTable).width(400);
        table.row();
        table.add(copyrightImage).align(Align.center).pad(200,0,0,0).colspan(10);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // check if the asset manager has finished loading
        if (parent.asstMng.manager.update()) { // Load some, will return true if done loading
            currentLoadingStage += 1;

            if (currentLoadingStage <= 5) {
                loadingTable.getCells().get((currentLoadingStage - 1) * 2).getActor().setVisible(true);
                loadingTable.getCells().get((currentLoadingStage - 1) * 2 + 1).getActor().setVisible(true);
            }

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

        stage.act();
        stage.draw();
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
