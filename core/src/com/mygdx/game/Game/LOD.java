package com.mygdx.game.Game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.Objects.Tomato;
import com.mygdx.game.Scenes.HUD;
import com.mygdx.game.Screens.CastleScreen;
import com.mygdx.game.Screens.ChurchScreen;
import com.mygdx.game.Screens.CityScreen;
import com.mygdx.game.Screens.TomatoMiniGame;
import com.mygdx.game.Tools.ResourceManager;

public class LOD extends Game {
	public SpriteBatch batch;
	private ResourceManager resourceManager;
	private CityScreen cityScreen;
	private CastleScreen castleScreen;
	private ChurchScreen churchScreen;
	private TomatoMiniGame tomatoMiniGame;
	private MyTimer timer;
	private HUD HUD;
	@Override
	public void create () {
		batch = new SpriteBatch();
		resourceManager = new ResourceManager();
		timer = new MyTimer();
		HUD = new HUD(timer, batch);

		cityScreen = new CityScreen(this, resourceManager, HUD, timer);
		castleScreen = new CastleScreen(this, resourceManager, HUD, timer);
		churchScreen = new ChurchScreen(this, resourceManager, HUD, timer);
		tomatoMiniGame = new TomatoMiniGame(this, resourceManager);


		setScreen(cityScreen);
	}

	public void changeScreen(String tag) {
		if (tag.equals("castle")) setScreen(castleScreen);
		if (tag.equals("city")) setScreen(cityScreen);
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
