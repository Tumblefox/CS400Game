package com.mygdx.game;

import Screens.Menu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CS400Game extends Game {
	public SpriteBatch batch;
	public static AssetManager manager;
        public static final int height = 800;
        public static final int width = 800;
        public static final float PPM = 100;//PPM = Pixels Per Meter
	
	@Override
	public void create () {
		batch = new SpriteBatch();
                setScreen(new Menu(this));
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
