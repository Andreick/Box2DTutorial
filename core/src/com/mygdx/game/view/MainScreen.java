package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.controller.MainClass;
import com.mygdx.game.controller.KeyboardController;
import com.mygdx.game.model.B2dModel;

public class MainScreen implements Screen {

    private MainClass parent;
    private KeyboardController controller;
    private B2dModel model;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Box2DDebugRenderer debugRenderer;

    private SpriteBatch batch;
    private Texture playerTexture;

    public MainScreen(MainClass mainClass) {
        parent = mainClass;

        camera = new OrthographicCamera();
        viewport = new FitViewport(32, 24, camera);
        controller = new KeyboardController();

        model = new B2dModel(controller, viewport, parent.asstMng);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        batch = new SpriteBatch();
        parent.asstMng.queueAddImages();
        parent.asstMng.manager.finishLoading();

        playerTexture = parent.asstMng.manager.get("images/player.png");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, camera.combined);

        batch.begin();
        batch.draw(playerTexture, model.player.getPosition().x -1, model.player.getPosition().y -1, 2, 2);
        batch.end();

        model.logicStep(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        batch.setProjectionMatrix(camera.combined);
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
        parent.dispose();
        debugRenderer.dispose();
        batch.dispose();
        playerTexture.dispose();
        model.world.dispose();
    }
}
