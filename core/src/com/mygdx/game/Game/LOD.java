package com.mygdx.game.Game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.Scenes.CutScene;
import com.mygdx.game.Scenes.HUD;
import com.mygdx.game.Screens.CastleScreen;
import com.mygdx.game.Screens.ChurchScreen;
import com.mygdx.game.Screens.CityScreen;
import com.mygdx.game.Screens.OpenScreen;
import com.mygdx.game.Screens.TomatoMiniGame;
import com.mygdx.game.Tools.ResourceManager;

public class LOD extends Game {
	public SpriteBatch batch;
	private ResourceManager resourceManager;
	private CityScreen cityScreen;
	private CastleScreen castleScreen;
	private ChurchScreen churchScreen;
	private OpenScreen openScreen;
	private TomatoMiniGame tomatoMiniGame;
	private MyTimer timer;
	private HUD HUD;
	public CutScene cutScene;
	private Music music1;
	private  Music music2;
	private Music music3;

	@Override
	public void create () {

		cutScene = null;

		batch = new SpriteBatch();
		resourceManager = new ResourceManager();
		timer = new MyTimer();
		HUD = new HUD(timer, batch);

		openScreen = new OpenScreen(this, resourceManager, HUD, timer);
		cityScreen = new CityScreen(this, resourceManager, HUD, timer);
		castleScreen = new CastleScreen(this, resourceManager, HUD, timer);
		churchScreen = new ChurchScreen(this, resourceManager, HUD, timer);
		tomatoMiniGame = new TomatoMiniGame(this, resourceManager, HUD, timer);

		loadMusic();
		music1.play();
		setScreen(openScreen);
	}


	public void loadMusic() {
		music1 = Gdx.audio.newMusic(Gdx.files.internal("Music/RPG_Medieval_Fantasy_-_Care_to_Dance.mp3"));
		music2 = Gdx.audio.newMusic(Gdx.files.internal("Music/RPG_Medieval_Fantasy_-_Tavern_Row.mp3"));
		music3 = Gdx.audio.newMusic(Gdx.files.internal("Music/RPG_Medieval_Fantasy_-_Welcome_to_The_Boars_Inn_Loopable.mp3"));
		music1.setVolume(10);
		music1.setLooping(true);
		music2.setVolume(10);
		music2.setLooping(true);
		music3.setVolume(10);
		music3.setLooping(true);
	}

	public void changeScreen(String tag) {

		if (tag.equals("castle")) {
			music1.stop();
			music2.stop();
			music3.play();
			setScreen(castleScreen);
		}
		if (tag.equals("city")) {
			music1.play();
			music2.stop();
			music3.stop();
			setScreen(cityScreen);
		}
		if (tag.equals("church")) {
			music1.stop();
			music2.stop();
			music3.play();
			setScreen(churchScreen);
		}
		if (tag.equals("tomato")) {
			music1.stop();
			music2.play();
			music3.stop();
			setScreen(churchScreen);
			setScreen(tomatoMiniGame);
		}
	}
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
