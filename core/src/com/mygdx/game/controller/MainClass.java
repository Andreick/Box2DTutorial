package com.mygdx.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.view.EndScreen;
import com.mygdx.game.view.LoadingScreen;
import com.mygdx.game.view.MainScreen;
import com.mygdx.game.view.MenuScreen;
import com.mygdx.game.view.PreferenceScreen;

public class MainClass extends Game {

	private LoadingScreen loadingScreen;
	private PreferenceScreen preferenceScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

	private AppPreferences preferences;
	public B2dAssetManager asstMng = new B2dAssetManager();
	private Music playingSong;

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		preferences = new AppPreferences();

		setScreen(loadingScreen);

		asstMng.queueAddMusic();
		asstMng.manager.finishLoading();
		playingSong = asstMng.manager.get("musics/Rolemusic_-_pl4y1ng.mp3");
		//playingSong.play();
	}

	public AppPreferences getPreferences() {
		return this.preferences;
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if (preferenceScreen == null) preferenceScreen = new PreferenceScreen(this);
				this.setScreen(preferenceScreen);
				break;
			case APPLICATION:
				if (mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if (endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	@Override
	public void dispose() {
		playingSong.dispose();
		asstMng.manager.dispose();
		this.loadingScreen.dispose();
	}
}