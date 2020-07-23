package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.controller.MainClass;

public class PreferenceScreen implements Screen {

    private MainClass parent;
    private Stage stage;

    private Skin skin;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label musicOnOffLabel;
    private Label volumeSoundLabel;
    private Label soundOnOffLabel;

    public PreferenceScreen(MainClass mainClass) {
        parent = mainClass;

        stage = new Stage(new ScreenViewport());

        parent.asstMng.queueAddSkin();
        parent.asstMng.manager.finishLoading();
        skin = parent.asstMng.manager.get("skins/uiskin.json");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        titleLabel = new Label("Preferences", skin);
        volumeMusicLabel = new Label("Music Volume:", skin);
        musicOnOffLabel = new Label("Music:", skin);
        volumeSoundLabel = new Label("Sound Volume:", skin);
        soundOnOffLabel = new Label("Sound Effects:", skin);

        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });

        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        final TextButton backButton = new TextButton("Back", skin, "toggle");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MainClass.MENU);
                stage.clear();
            }
        });

        table.add(titleLabel).colspan(2);
        table.defaults().width(150);
        table.row().pad(30,0,10,0);
        table.add(volumeMusicLabel);
        table.add(volumeMusicSlider).width(100);
        table.row();
        table.add(musicOnOffLabel);
        table.add(musicCheckbox);
        table.row().pad(10,0,10,0);
        table.add(volumeSoundLabel);
        table.add(volumeSoundSlider).width(100);
        table.row();
        table.add(soundOnOffLabel);
        table.add(soundCheckbox);
        table.row().pad(20,0,0,0);
        table.add(backButton).colspan(2).width(100);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        this.skin.dispose();
    }
}
