/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CS400Game;

/**
 *
 * @author asieka01
 */
public class Menu implements Screen {
    private CS400Game game;
    private TextureAtlas atlas;
    Texture texture;
    private OrthographicCamera cam;
    private Viewport viewport;
    
    public Menu(CS400Game game) {
        this.game = game;
        atlas = new TextureAtlas();
        texture = new Texture("badlogic.jpg"); //This is the test image
        cam = new OrthographicCamera();
        //viewport = new ScreenViewport(cam); Resizes to fit display size
        viewport = new FitViewport(game.width, game.height, cam);
        
        
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        //everything rendered to screen goes here
        game.batch.draw(texture, 0, 0);
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {//i = width, i1 = height
        viewport.update(i, i1);
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
