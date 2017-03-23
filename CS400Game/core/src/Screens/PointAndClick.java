/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Tools.WorldBuilder;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    
    private Music music;
    
    //private Inventory inventory; must create class
    //private Array<Item> items; must create class

    BitmapFont font;
    String currentMessage;
    
    ShapeRenderer messageBox;
    
    public PointAndClick(CS400Game game) {
        this.game = game;
        
        messageBox = new ShapeRenderer();

        
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.setColor(Color.WHITE);
        currentMessage = "";
        
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 800, cam);
        viewport.apply();
        cam.position.set(400, 400, 0);
        //viewport = new FitViewport(game.V_WIDTH, game.V_HEIGHT, cam);
        
        world = new World(new Vector2(0,0), true);
        
        maploader = new TmxMapLoader();
        map = maploader.load("PNC1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(cam);
        /*atlas = ;
        physicDebug = ;
        music = ;*/
    }
    
    //public Atlas getAtlas() {}
    //public Map getMap() {}
    //public World getWorld() {}
    
    public void nextBG(boolean leftKey) {
        
    }
    
    public void clickHandler() {
        if (Gdx.input.justTouched()) {
            
        }
    }
    
    public void handleInput(float deltaTime) {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            nextBG(true);
            currentMessage = "";
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            nextBG(false);
            currentMessage = "";
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            currentMessage = "Cryro Test Subject," + "\n"
                    + "Point blank, if your reading this, I'm already dead."
                    + "Perhaps you can tell from whatever state of decomposition I'm currently in. "
                    + "I wrote this because I have a bit of an off-beat sense of humor, but also to help you out. If I'm dead, chances are, you'll go next. "
                    + "As I write this, I'm trying to figure out where the key to the door is, its so elusive. "
                    + "Since I can't find it, I've prepared to melt the lock with thermite. That's why I have magnesium strips, I'm a bit of a doomsday prepper but not anymore for health reasons. Help yourself."
                    + "Right now, I'm having trouble finding the two ingredients needed, iron oxide and aluminum powder, and if you're reading this I never found them. "
                    + "If you're lucky, you'll manage to find some. The ratio of aluminum powder to iron oxide is 27:80. Good luck, I'm dying to see if you making it.";
        }
        
        clickHandler();
    }
    
    public void update(float deltaTime) {
        handleInput(deltaTime);
        cam.update();
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        update(f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);     
        
        game.batch.begin();
        //everything rendered to screen goes here
        renderer.render();
        //font.draw(game.batch, currentMessage, 0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), 10, true);
        font.draw(game.batch, currentMessage, 0, 75, 400, 10, true);
        game.batch.end();
        
        messageBox.begin(ShapeType.Line);
        messageBox.rect(0, 0, 400, 150);
        messageBox.setColor(Color.BLUE);
        messageBox.end();
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
