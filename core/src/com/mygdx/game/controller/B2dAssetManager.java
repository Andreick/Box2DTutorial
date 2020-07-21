package com.mygdx.game.controller;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {
    public final AssetManager manager = new AssetManager();

    //Textures

    public final String playerImage = "images/player.png";
    public final String enemyImage = "images/enemy.png";

    public void queueAddImages() {
        manager.load(playerImage, Texture.class);
        manager.load(enemyImage, Texture.class);
    }

    //Sounds

    public final String pingSound = "sounds/ping.wav";
    public final String boingSound = "sounds/boing.wav";

    public void queueAddSounds() {
        manager.load(pingSound, Sound.class);
        manager.load(boingSound, Sound.class);
    }

    //Musics

    public final String playingSong = "musics/Rolemusic_-_pl4y1ng.mp3";

    public void queueAddMusic() {
        manager.load(playingSong, Music.class);
    }

    //Skins

    public final String skin = "skin/uiskin.json";

    public void queueAddSkin() {
        SkinParameter params = new SkinParameter("skin/uiskin.atlas");
        manager.load(skin, Skin.class, params);
    }
}
