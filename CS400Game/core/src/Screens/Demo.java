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
public class Demo implements Screen {    
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
    private Array<Rectangle> objects;
    private Array<String> messages;
    BitmapFont font;
    String currentMessage;
    Array<String> inventory;
    boolean hasPass, hasKey, hasStrips, hasNote, hasPowder, hasRust, hasThermite;
    boolean disarmed; 
    
    ShapeRenderer messageBox;
    /*1-Light 
    2-Chamber
    3-Coffee Grinder
    4-Coffee Mugs
    5-Scale
    6-Waste Bin
    7-Sink
    8-Rusty pipe
    9-Door
    10-Lock
    11-Scientist
    12-Drawn
    13-Under Desk
    14-Computer*/
    
    public Demo(CS400Game game) {
        this.game = game;
        textureList = new Array();
        textureList.add(new Texture("Bunker1.png"));
        textureList.add(new Texture("Bunker2.png"));
        textureList.add(new Texture("Bunker3.png"));
        textureList.add(new Texture("Bunker4.png"));
        texture = textureList.get(0);
        textureIndex = 0;
        
        objects = new Array(20);// Rectangles begin in top right at 0,0
        // + on x goes right, + on y goes down
        objects.add(new Rectangle(570, 130, 46, 64));
        objects.add(new Rectangle(0, 130, 46, 64));
        objects.add(new Rectangle(110, 70, 285, 740));//end wall 0
        objects.add(new Rectangle(262, 112, 46, 64));
        objects.add(new Rectangle(20, 424, 64, 69));
        objects.add(new Rectangle(98, 468, 36, 40));
        objects.add(new Rectangle(175, 468, 82, 29));
        objects.add(new Rectangle(352, 720, 124, 95));
        objects.add(new Rectangle(460, 427, 278, 137));
        objects.add(new Rectangle(624, 591, 69, 14));//end wall 1
        objects.add(new Rectangle(684, 71, 63, 64));
        objects.add(new Rectangle(201, 24, 370, 794));
        objects.add(new Rectangle(37, 295, 121, 150));//end wall 2
        objects.add(new Rectangle(75, 120, 38, 64));
        objects.add(new Rectangle(50, 408, 260, 430));
        objects.add(new Rectangle(414, 525, 108, 63));
        objects.add(new Rectangle(583, 298, 258, 167));
        objects.add(new Rectangle(614, 597, 180, 220));
        
        messages = new Array(20);
        messages.add("These warning lights are getting on my nerves.");
        messages.add("These warning lights are getting on my nerves.");
        messages.add("I remember being put in that cryro chamber. What happened?");//end wall 0
        messages.add("These warning lights are getting on my nerves.");
        messages.add("It's a coffee grinder.");
        messages.add("Two coffee mugs...and absolutely no coffee.");
        messages.add("A digital scale, obviously for the coffee.");
        messages.add("A waste bin, probably nothing special.");
        messages.add("An eyewashing station...right next to a trash bin. How sanitary.");
        messages.add("A rusty pipe. How long have I been down here?");//end wall 1
        messages.add("These warning lights are getting on my nerves.");
        messages.add("The way out is clamped shut by the emergency system.");
        messages.add("The lock has also been seized by the emergency system. I have to turn it off to get out.");//end wall 2
        messages.add("These warning lights are getting on my nerves.");
        messages.add("Dead scientist. There is a note and two magnesium strips in his pocket. Press 'R' to read the note.");
        messages.add("There was a door key in that drawer.");
        messages.add("This conputer controls the emergency system, but it's locked. I'll need a password.");
        messages.add("Found the password under the desk. It's 'HOWDAREYOUASSUMEMYGENDER'.");
        
        inventory = new Array();
        hasPass = false;
        hasKey = false;
        hasStrips = false;
        hasNote = false;
        hasPowder = false;
        hasRust = false;
        hasThermite = false;
        disarmed = false;
        
        messageBox = new ShapeRenderer();

        
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.setColor(Color.WHITE);
        currentMessage = "";
        
        cam = new OrthographicCamera();
        viewport = new FitViewport(game.V_WIDTH, game.V_HEIGHT, cam);
        
        world = new World(new Vector2(0,0), true);
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
    
    public void clickHandler() {
        if (Gdx.input.justTouched()) {
            for (int i = 0; i < objects.size; i++) {
                if (objects.get(i).contains(Gdx.input.getX(), Gdx.input.getY())) {
                    if (textureIndex == 0 && i < 3) {// wall 0
                        currentMessage = messages.get(i);
                    }
                    if (textureIndex == 1 && i < 10 && i >= 3) { // wall 1
                        currentMessage = messages.get(i);
                    }
                    if (textureIndex == 2 && i < 13 && i >= 10) {// wall 2
                        currentMessage = messages.get(i);
                    }
                    if (textureIndex == 3 && i < 18 && i >= 13) {// wall 3
                        currentMessage = messages.get(i);
                    }
                }
            }
            if (inventory.size != 0 && hasPass) {
                if (textureIndex == 3) {
                if (objects.get(16).contains(Gdx.input.getX(), Gdx.input.getY())) {
                    currentMessage = "Finally, I shut the emergency system off.";
                    inventory.removeValue("Password", false);
                    textureList.set(2, (new Texture("Bunker3b.png")));
                    messages.set(11, "The door is sealed shut.");
                    messages.set(12, "This lock needs a key. What else is new?");
                    disarmed = true;
                }
                }
            }
            if (objects.get(17).contains(Gdx.input.getX(), Gdx.input.getY()) && !hasPass) {
                inventory.add("Password");
                hasPass = true;
            }
            if (objects.get(14).contains(Gdx.input.getX(), Gdx.input.getY()) && !hasNote && !hasStrips) {
                inventory.add("Note");
                inventory.add("Magnesium strips");
                hasNote = true;
                hasStrips = true;
            }
            if (objects.get(15).contains(Gdx.input.getX(), Gdx.input.getY()) && hasKey != true) {
                inventory.add("Key");
                hasKey = true;
            }
            
            if (textureIndex == 2) {
            if (objects.get(12).contains(Gdx.input.getX(), Gdx.input.getY()) && disarmed && hasKey) {
                currentMessage = "The tip of the key broke inside the lock.";
                inventory.removeValue("Key", false);
            }
            }
            if (objects.get(17).contains(Gdx.input.getX(), Gdx.input.getY()) && !hasPass) {
                inventory.add("Password");
                hasPass = true;
            }
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
            currentMessage = "Inventory: Empty";
            if (inventory.size != 0) {
                currentMessage = "Inventory:";
                for (int i = 0; i < inventory.size; i++)
                    currentMessage += "\n" + inventory.get(i);
                    }
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) && hasNote) {
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
        //game.batch.draw(texture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.draw(texture, 0, 150, 800, 800);
        
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
