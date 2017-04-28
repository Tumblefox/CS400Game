/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Objects.InGameObject;
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
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CS400Game;
import java.util.Iterator;

/**
 *
 * @author asieka01
 */
public class PointAndClick implements Screen {    
    private CS400Game game;
    //private TextureAtlas atlas;
    private OrthographicCamera cam;
    private Viewport viewport;
    
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private float[] stages;
    private int restrictionPoint; //point of initially inaccessible stages; always at the end of array
    private int numOfStages;
    private int currentStage;
    private Array<InGameObject> gameObjects;
    
    private World world;
    private WorldBuilder worldBuilder;
    private Box2DDebugRenderer physicDebug;
    
    private Music music;
    
    //private Inventory inventory; must create class
    //private Array<Item> items; must create class

    BitmapFont font;
    String currentMessage;
    float textPos;
    
    ShapeRenderer messageBox;
    InGameObject test;
    
    public PointAndClick(CS400Game game) {
        this.game = game;
        
        messageBox = new ShapeRenderer();

        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.setColor(Color.WHITE);
        currentMessage = "";
        
        cam = new OrthographicCamera();

        viewport = new FitViewport(game.V_WIDTH, game.V_HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        //cam.position.set(2399, viewport.getWorldHeight() / 2, 0);
        
        //Map Debug
        /*currentMessage = "Position: " + cam.position.x;
        textPos = 0;*/
        
        world = new World(new Vector2(0,0), true);
        
        maploader = new TmxMapLoader();
        map = maploader.load("PNC1.tmx");
        //map = maploader.load("PNCtest.tmx"); //TEST
        renderer = new OrthogonalTiledMapRenderer(map);
        
        //numOfStages = ((map.getProperties().get("tilewidth", Integer.class)) * 2) / game.V_WIDTH;
        numOfStages = ((map.getProperties().get("tilewidth", Integer.class))) / game.V_WIDTH; //Test
        stages = new float[numOfStages];
        stages[0] = game.V_WIDTH / 2; //First stage's cam position is at game.V_WIDTH / 2 (400px)
        currentStage = 0;
        
        restrictionPoint = 4;
        //restrictionPoint = 9; //TEST
        
        for (int i =  1; i < numOfStages; i++) {
            stages[i] = stages[i-1] + game.V_WIDTH;
        }

       int objectCount = 0;
       for (int i = 0; i <  map.getLayers().getCount(); i++) {
           objectCount += map.getLayers().get(i).getObjects().getCount();
       }
       
       String objName = "";
       String layer = "";
       gameObjects = new Array(objectCount);
       for (int j = 0; j < map.getLayers().getCount(); j++) {
           for (int i = 0; i < map.getLayers().get(j).getObjects().getCount(); i++) {
               if (map.getLayers().get(j).getObjects().getCount() != 0) {
                   objName = map.getLayers().get(j).getObjects().get(i).getName();
                   layer = map.getLayers().get(j).getName();
                   gameObjects.add(new InGameObject(this, objName, layer));
                   //currentMessage += "Name: " + objName +"\n"; DEBUG
               }
           }
       }
       /*for (int i = 0; i < stages.length; i++) {
           currentMessage += "Stage " + i + ": " + stages[i] + "\n";
       }*/
       //currentMessage += renderer.getViewBounds().width;
       //currentMessage += map.getLayers().get(1).getObjects().get(1).getProperties().get("type", String.class); DEBUG
       //currentMessage += numOfStages; DEBUG
       /*String dis = ""; DEBUG
       for (Iterator<String> iter = map.getLayers().get(1).getObjects().get(0).getProperties().getKeys(); iter.hasNext();) {
           dis += iter.next() + "\n";        
       }
       currentMessage = dis;*/
       
        /*atlas = ;
        physicDebug = ;
        music = ;*/
       int x = 0;
       for (InGameObject o: gameObjects) {
           currentMessage += "Object " + x + ": " + o.getName() + " | Coor.: " + o.getX() + "," + o.getY() + " | "+ o.getShape().getClass().getTypeName() + "\n";
           x++;
       }
       
       test = gameObjects.get(1);
    }
    
    //public Atlas getAtlas() {}
    //public World getWorld() {}
    
    public TiledMap getMap() {
        return map;
    }
    
    public void nextBG(boolean leftKey) {
        
    }
    
    public void clickHandler() {
        if (Gdx.input.justTouched()) {
            for (int i = 0; i < gameObjects.size; i++) {
                if (gameObjects.get(i).getShape().contains(Gdx.input.getX(), Gdx.input.getY())) {
                    currentMessage = gameObjects.get(i).getText();
                }
            }
            /*if (gameObjects.peek().getShape().contains(Gdx.input.getX(), Gdx.input.getY())) {
                currentMessage = gameObjects.peek().getText();
            }*/
        }
    }
    
    public void handleInput(float deltaTime) {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if (currentStage != (restrictionPoint - 1)) {
                currentStage++;
                cam.position.x = stages[currentStage];
                textPos += game.V_WIDTH;
            }
            else {
                cam.position.x = stages[0];
                currentStage = 0;
                textPos = 0;
            }
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (currentStage != (0)) {
                currentStage --;
                cam.position.x = stages[currentStage];
                textPos -= game.V_WIDTH;
            }
            else {
                cam.position.x = stages[restrictionPoint - 1];
                currentStage = restrictionPoint - 1;
                textPos = restrictionPoint - 1;
            }
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            currentMessage = "";
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            currentMessage = "Cryo Test Subject," + "\n"
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
        renderer.setView(cam);
        cam.update();
        //currentMessage = "Position: " + cam.position.x;
        //renderer.setView(cam);
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClearColor(1, 0, 0, 1); //RED
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderer.render();
        //currentMessage = "" + renderer.getViewBounds().x + "\n" + currentStage + "\n" + cam.position.x; //DEBUG
        
        game.batch.setProjectionMatrix(cam.combined);     
        
        game.batch.begin();
        //everything rendered to screen goes here
        font.draw(game.batch, currentMessage, textPos, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), 10, true);
        font.draw(game.batch, "Mouse: " + Gdx.input.getX() + ", " + Gdx.input.getY(), textPos, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 10, true);
        game.batch.end();
        
        messageBox.begin(ShapeType.Line);
        //messageBox.rect(0, 0, 400, 150);
        //messageBox.polyline(gameObjects.get(0).getPolyline().getVertices());
        messageBox.ellipse(test.getX(), 800 - test.getY(), test.getEllipse().circumference() / 3.14f, test.getEllipse().circumference() / 3.14f);
        messageBox.setColor(Color.BLUE);
        messageBox.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        map.dispose();
    }
    
}
