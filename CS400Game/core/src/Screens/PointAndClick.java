/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Tools.WorldBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CS400Game;

/**
 *
 * @author asieka01
 */
public class PointAndClick implements Screen {    
    private CS400Game game;
    private TextureAtlas atlas;
    private OrthographicCamera cam;
    private Viewport viewport;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private WorldBuilder worldBuilder;
    private Box2DDebugRenderer physicDebug;
    //private Inventory inventory; must create class
    private Music music;
    //private Array<Item> items; must create class

    private Texture texture;//texture is the current background on screen
    private int textureIndex;//indicates index of current texture
    private Array<Texture> textureList; //holds all background textures
    
    public PointAndClick(CS400Game game) {
        this.game = game;
        textureList = new Array();
        textureList.add(new Texture("BG1.png"));
        textureList.add(new Texture("BG2.png"));
        textureList.add(new Texture("BG3.png"));
        textureList.add(new Texture("BG4.png"));
        texture = textureList.get(0);
        textureIndex = 0;
        /*atlas = ;
        cam = ;
        viewport = ;
        maploader = ;
        map = ;
        renderer = ;
        world = ;
        physicDebug = ;
        music = ;*/
    }
    
    //public Atlas getAtlas() {}
    //public Map getMap() {}
    //public World getWorld() {}
    
    public void nextBG(boolean leftKey) {
        if (leftKey == true) {
            if (textureIndex != textureList.size - 1) {
                texture = textureList.get(textureIndex + 1);
                textureIndex += 1;
            }
            else {
                texture = textureList.get(0);
                textureIndex = 0;
            }
        }
        else {
            if (textureIndex != 0) {
                texture = textureList.get(textureIndex - 1);
                textureIndex -= 1;
            }
            else {
                texture = textureList.get(3);
                textureIndex = 3;
            }
        }
    }
    
    public void handleInput(float deltaTime) {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            nextBG(true);
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            nextBG(false);
        }
    }
    
    public void update(float deltaTime) {
        handleInput(deltaTime);
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        update(f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        //everything rendered to screen goes here
        game.batch.draw(texture, 0, 0);
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {
        //viewport.update(i, i1);
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
