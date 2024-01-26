package com.mygdx.game.Game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Tools.ResourceManager;

public class LOD extends Game {
	public SpriteBatch batch;
	private ResourceManager resourceManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		resourceManager = new ResourceManager();
		setScreen(new GameScreen(this, resourceManager));
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
