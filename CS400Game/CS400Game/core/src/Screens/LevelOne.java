/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Objects.ChemistryPuzzle;
import Objects.Inventory;
import Objects.Item;
import Objects.Level;
import Objects.Note;
import Tools.WorldBuilder;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.CS400Game;

/**
 *
 * @author asieka01
 */
public class LevelOne  extends Level implements Screen {    
    
    private BitmapFont font;
    private String currentMessage, currentNote;
    private Array<String> inventory;
    private boolean hasPass, hasKey, hasStrips, hasNote, hasFoil, hasPowder, hasRust, hasThermite, lockBurned, thermitePlaced;
    private boolean disarmed, inPuzzle, inNote;
    private double[] specialItemWeights;
    private final double puzzleRatio = 0.3375;
    private final String puzzleNumerator = "Aluminum Powder";
    private ChemistryPuzzle puzzle = new ChemistryPuzzle(puzzleRatio, puzzleNumerator);
    private Texture exit;
    
    private float z = 30f;
    private float x = 0;
    
    public LevelOne(CS400Game game) {
        this.game = game;
        
        exit = new Texture("Exit.png");
        background = new Texture("PNC1.png");
        walls = new Array();
        walls.add(new TextureRegion(background, 0, 0, 800, 800));
        walls.add(new TextureRegion(background, 800, 0, 800, 800));
        walls.add(new TextureRegion(background, 1600, 0, 800, 800));
        walls.add(new TextureRegion(background, 2400, 0, 800, 800));
        textureIndex = 0;
        currentWall = walls.get(0);
        
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
        messages.add("I remember being put in that cryo chamber. What happened?");//end wall 0
        
        messages.add("These warning lights are getting on my nerves.");
        messages.add("It's a coffee grinder.");
        messages.add("Two coffee mugs...and absolutely no coffee.");
        messages.add("A digital scale, obviously for the coffee.");
        messages.add("This wastebin has aluminum foil.");
        messages.add("An eyewashing station...right next to a trash bin. How sanitary.");
        messages.add("A rusty pipe. How long have I been down here?");//end wall 1
        
        messages.add("These warning lights are getting on my nerves.");
        messages.add("The way out is clamped shut by the emergency system.");
        messages.add("The lock has also been seized by the emergency system. I have to turn it off to get out.");//end wall 2
        
        messages.add("These warning lights are getting on my nerves.");
        messages.add("Dead scientist. There is a note and a few magnesium strips in his pocket. Press 'R' to read the note.");
        messages.add("There was a key and a spoon in that drawer, 1 teaspoon to be exact.");
        messages.add("This computer controls the emergency system, but it's locked. I'll need a password.");
        messages.add("Found the password under the desk. It's 'B%P~768rS!9T/`Qj'.");
        
        inventory = new Array();
        hasPass = false;
        hasKey = false;
        hasStrips = false;
        hasNote = false;
        hasFoil = false;
        hasPowder = false;
        hasRust = false;
        hasThermite = false;
        disarmed = false;
        inPuzzle = false;
        inNote = false;
        lockBurned = false;
        thermitePlaced = false;
        
        messageBox = new ShapeRenderer();
        
        note = new Note();

        
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.setColor(Color.WHITE);
        currentMessage = "";
        currentNote = "";
        
        cam = new OrthographicCamera();
        viewport = new FitViewport(game.V_WIDTH, game.V_HEIGHT, cam);
        
        items = new Array();
        //Item(String name, boolean isDiscardable, boolean isCombined, int numOfUses)
        items.add(new Item("Thermite", false, false, 1));
        items.add(new Item("Aluminum Powder", false, false, 1));
        items.add(new Item("Rust", false, false, 1));
        items.add(new Item("Password", false, false, 1));
        items.add(new Item("Magnesium Strips", false, false, 1));
        items.add(new Item("Aluminum Foil", false, false, 1));
        items.add(new Item("Key", false, false, 1));
        items.add(new Item("Spoon", false, false, 1));
        
        specialItemWeights = new double[10];
        for (int i = 0; i < specialItemWeights.length - 2; i++)
            specialItemWeights[i] = 0;
        specialItemWeights[8] = 0.035;
        specialItemWeights[9] = 0.47;
        
        /*Index in itemCombinations corresponds to the order of combined items in the inventory image*/
        itemCombinations = new Array(); //Each Vector2 represents item combinations paired by index in items
        itemCombinations.add(new Vector2(2, 7)); //second value becomes the combined item
        itemCombinations.add(new Vector2(1, 7));
        
        inventoryGui = new Inventory(new Texture("L1Inventory.png"), itemCombinations);
        
        //music = CS400Game.manager.get("sfx/song.wav", Music.class);
        //music.setLooping(true);
        //music.play();
    }
    
    public Array<Item> getItems() {
        return items;
    }
    
    public Array<Vector2> getItemCombos() {
        return itemCombinations;
    }
    
    public Inventory getInventory() {
        return inventoryGui;
    }
    
    //public World getWorld() {}
    
    public void nextBG(boolean leftKey) {

        if (leftKey == true) {
            if (textureIndex != walls.size - 1) {
                currentWall = walls.get(textureIndex + 1);
                textureIndex += 1;
            }
            else {
                currentWall = walls.get(0);
                textureIndex = 0;
            }
        }
        else {
            if (textureIndex != 0) {
                currentWall = walls.get(textureIndex - 1);
                textureIndex -= 1;
            }
            else {
                currentWall = walls.get(3);
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
            if (textureIndex == 3) {
                if (inventory.size != 0 && hasPass && inventoryGui.selected() == 3) {
                    if (objects.get(16).contains(Gdx.input.getX(), Gdx.input.getY())) {
                        currentMessage = "Finally, I shut the emergency system off.";
                        inventory.removeValue("Password", false);
                        inventoryGui.discard("Password", getItems());
                        walls.set(2, new TextureRegion(background, 3200, 0, 800, 800));
                        messages.set(16, "Finally, I shut the emergency system off.");
                        messages.set(11, "The door is sealed shut.");
                        messages.set(12, "This lock needs a key. What else is new?");
                        disarmed = true;
                    }
                }
                if (objects.get(17).contains(Gdx.input.getX(), Gdx.input.getY()) && !hasPass) {
                    inventory.add("Password");
                    inventoryGui.add("Password", getItems());
                    hasPass = true;
                }
                if (objects.get(14).contains(Gdx.input.getX(), Gdx.input.getY()) && !hasNote && !hasStrips) {
                    inventory.add("Note");
                    inventory.add("Magnesium Strips");
                    inventoryGui.add("Magnesium Strips", getItems());
                    hasNote = true;
                    hasStrips = true;
                }
                if (objects.get(15).contains(Gdx.input.getX(), Gdx.input.getY()) && hasKey != true) {
                    inventory.add("Key");
                    inventory.add("Spoon");
                    inventoryGui.add("Key", getItems());
                    inventoryGui.add("Spoon", getItems());
                    hasKey = true;
                }
            }
            
            if (textureIndex == 2) {
                if (objects.get(12).contains(Gdx.input.getX(), Gdx.input.getY()) && disarmed && hasKey && inventoryGui.selected() == 6) {
                    currentMessage = "The tip of the key broke inside the lock.";
                    inventory.removeValue("Key", false);
                    inventoryGui.discard("Key", getItems());
                }
            }
           if (textureIndex == 1) {
               if (objects.get(7).contains(Gdx.input.getX(), Gdx.input.getY()) && !hasFoil) {
                   inventory.add("Aluminum Foil");
                   inventoryGui.add("Aluminum Foil", getItems());
                   hasFoil = true;
               }
               if (objects.get(9).contains(Gdx.input.getX(), Gdx.input.getY()) && !hasRust) {
                   inventory.add("Rust");
                   inventoryGui.add("Rust", getItems());
                   hasRust = true;
               }
               if (objects.get(4).contains(Gdx.input.getX(), Gdx.input.getY()) && hasFoil && !hasPowder && inventoryGui.selected() == 5) {
                   inventory.add("Aluminum Powder");
                   inventoryGui.add("Aluminum Powder", getItems());
                   inventory.removeValue("Aluminum Foil", false);
                   inventoryGui.discard("Aluminum Foil", getItems());
                   currentMessage = "I used the grinder to grind the foil into powder.";
                   hasPowder = true;
               }
               
           }
           if (inventoryGui.hasSelection() && inventoryGui.selected() == 7 && inventoryGui.equalRegions(7, 8)) {//7 is item, 8 is combination item
               Item temp = items.get(2);
               temp.setWeight(specialItemWeights[8]);
               if (puzzle.usingbeaker1())
                   puzzle.addToB1(temp);
               else
                   puzzle.addToB2(temp);
           }
           if (inventoryGui.hasSelection() && inventoryGui.selected() == 7 && inventoryGui.equalRegions(7, 9)) {
               Item temp = items.get(1);
               temp.setWeight(specialItemWeights[9]);
               if (puzzle.usingbeaker1())
                   puzzle.addToB1(temp);
               else
                   puzzle.addToB2(temp);
           }
           if (objects.get(12).contains(Gdx.input.getX(), Gdx.input.getY()) && inventoryGui.hasSelection() && hasThermite && inventoryGui.selected() == 0) {
               currentMessage = "The lock is now loaded with thermite.";
               inventory.removeValue("Thermite", false);
               inventoryGui.discard("Thermite", getItems());
               hasThermite = false;
               thermitePlaced = true;
           }
           if (objects.get(12).contains(Gdx.input.getX(), Gdx.input.getY()) && inventoryGui.hasSelection() && hasStrips && thermitePlaced && inventoryGui.selected() == 4) {
               currentMessage = "I've ignited the thermite. The door should open now.";
               inventory.removeValue("Magnesium Strips", false);
               inventoryGui.discard("Magnesium Strips", getItems());
               hasThermite = false;
               lockBurned = true;
           }
           if (objects.get(6).contains(Gdx.input.getX(), Gdx.input.getY()) && hasRust && hasPowder) {
               inPuzzle = true;
           }
        }
        
        if (lockBurned) {
               walls.set(2, new TextureRegion(exit, 0, 0, 800, 800));
               currentWall = new TextureRegion(exit, 0, 0, 800, 800);
               CS400Game.manager.get("sfx/Wonder.wav", Sound.class).play();
               messages.set(11, "Time to go.");
               messages.set(12, "You couldn't pay me to touch that.");
               lockBurned = false;
           }
    }
    
    public void handleInput(float deltaTime) {
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            nextBG(true);
            currentMessage = "";
            inPuzzle = false;
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            nextBG(false);
            currentMessage = "";
            inPuzzle = false;
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            currentMessage = "Inventory: Empty";
            if (inventory.size != 0) {
                currentMessage = "Inventory:";
                for (int i = 0; i < inventory.size; i++)
                    currentMessage += "\n" + inventory.get(i);
                    }
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) && hasNote && !inNote) {
            inNote = true;
            currentMessage = "";
            currentNote = "Cryo Test Subject," + "\n"
                    + "Point blank, if your reading this, I'm already dead."
                    + "Perhaps you can tell from whatever state of decomposition I'm currently in. "
                    + "I wrote this because I have a bit of an off-beat sense of humor, but also to help you out. If I'm dead, chances are, you'll go next. "
                    + "As I write this, I'm trying to figure out where the key to the door is, its so elusive. "
                    + "Since I can't find it, I've prepared to melt the lock with thermite. That's why I have magnesium strips and a match, I'm a bit of a doomsday prepper but not anymore for health reasons. Help yourself."
                    + "Right now, I'm having trouble finding the two ingredients needed, iron oxide and aluminum powder, and if you're reading this I never found them. "
                    + "If you're lucky, you'll manage to find some. The ratio of aluminum powder to iron oxide is 27:80. Good luck, I'm dying to see if you making it.";
            note.setNote(currentNote);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R) && hasNote && inNote) {
            inNote = false;
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {// Prepared for debugging
            currentMessage = "result: " + inventoryGui.equalRegions(7,8);
        } 
                
        puzzle.input();
        
        /*if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            if (inPuzzle) {
                inPuzzle = false;
            } else {
                inPuzzle = true;
            }
        }*/
        
        clickHandler();
        
        inventoryGui.input();
        
        if (puzzle.isSolved() && !hasThermite && !thermitePlaced) {
            currentMessage = "I've successfully created thermite.";
            
            inventory.add("Thermite");
            inventoryGui.add("Thermite", getItems());
            
            inventory.removeValue("Rust", false);
            inventoryGui.discard("Rust", getItems());
            inventory.removeValue("Aluminum Powder", false);
            inventoryGui.discard("Aluminum Powder", getItems());
            hasThermite = true;
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

        game.batch.begin();
        //everything rendered to screen goes here
        
        game.batch.draw(currentWall, 0, 150, 800, 800);
        
        if (z != 0 && !disarmed) {
            game.batch.draw(new Texture("Overlay.png"), 0, 150, 800, 800);
            z--;
        } else if (x != 30f) {
            x++;
        } else {
            z = x;
            x = 0;
        }
        
        font.draw(game.batch, currentMessage, 0, 140, 400, 10, true);
        //font.draw(game.batch, "z: " + z, 0, 140, 400, 10, true);
        
        game.batch.end();
        
        messageBox.begin(ShapeType.Line);
        messageBox.rect(0, 0, 400, 150);
        messageBox.setColor(Color.BLUE);
        messageBox.end();
        
        inventoryGui.render(f);
        
        if (inNote) {
        note.render(f);
        }
        
        if (inPuzzle) {
            puzzle.render(f);
        }
    }

    @Override
    public void resize(int i, int i1) {
        //viewport.update(i, i1);
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
