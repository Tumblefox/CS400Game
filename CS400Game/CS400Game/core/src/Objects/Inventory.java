 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CS400Game;

/**
 *
 * @author asieka01
 */
public class Inventory {
    
    private final int capacity = 8; //max num of items that can be carried
    private int numOfItems;
    private ShapeRenderer inventory;
    private SpriteBatch batch;
    private Texture levelItems;
    private Array<TextureRegion> regions;
    private Array<TextureRegion> currentRegions;
    private Array<Rectangle> rect;
    private boolean selected;
    private Vector2 select;
    private int selectedIndex;
    private Array<Vector2> itemCombos;
    private boolean[] i; //boolean to render each item
    
    public Inventory(Texture levelItems, Array<Vector2> itemCombos) {
        
        this.levelItems = levelItems; 
        numOfItems = 0;
        inventory = new ShapeRenderer();
        batch = new SpriteBatch();
    
        i = new boolean[capacity];
        for (int j = 0; j < i.length; j++)
            i[j] = false;
        
        regions = new Array();
        regions.add(new TextureRegion(levelItems, 0, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 100, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 200, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 300, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 400, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 500, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 600, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 700, 0, 100, 75));
        regions.add(new TextureRegion(levelItems, 800, 0, 100, 75));//combined item 1
        regions.add(new TextureRegion(levelItems, 900, 0, 100, 75));//combined item 2
        regions.add(new TextureRegion(levelItems, 1000, 0, 100, 75));
        
        currentRegions = new Array();
        for(int j = 0; j < regions.size; j++)
            currentRegions.add(regions.get(j));
        
        rect = new Array();
        rect.add(new Rectangle(400, 0, 100, 75));
        rect.add(new Rectangle(500, 0, 100, 75));
        rect.add(new Rectangle(600, 0, 100, 75));
        rect.add(new Rectangle(700, 0, 100, 75));
        rect.add(new Rectangle(400, 75, 100, 75));
        rect.add(new Rectangle(500, 75, 100, 75));
        rect.add(new Rectangle(600, 75, 100, 75));
        rect.add(new Rectangle(700, 75, 100, 75));
        
        select = new Vector2();
        selected = false;
        selectedIndex = 0;
        
        this.itemCombos = itemCombos;
    }
    
    public void add(String name, Array<Item> temp) {
        for (int j = 0; j < temp.size; j++) {
            if (temp.get(j).name().equals(name)) {
                i[j] = true;
            }
        } 
    }
    
    public void discard(String name, Array<Item> temp) {
        for (int j = 0; j < temp.size; j++) {
            if (temp.get(j).name().equals(name)) {
                i[j] = false;
            }
        }
        selected = false;
    }
    
    public void combine(int index) { //select objects
        for (int i = 0; i < itemCombos.size; i++) {
            if (selectedIndex == itemCombos.get(i).x) {
                if (index == itemCombos.get(i).y) 
                    currentRegions.set(index, regions.get(capacity + i)); //7
            }
            if (selectedIndex == itemCombos.get(i).y) {
                if (index == itemCombos.get(i).x) 
                    currentRegions.set((int)itemCombos.get(i).y, regions.get(capacity + i)); //7
            }
        }
    }
    
    public boolean isFull() {
        if (numOfItems == capacity)
            return true;
        else
            return false;
    }
    
    public int selected() { //returns index of selected item
        return selectedIndex;
    }
    
    public boolean hasItem(int index) {
        return i[index];
    }
    
    public boolean hasSelection() {
        return selected;
    }
    
    public boolean use(Rectangle rect, int item, int combo) {
        if (selected) {
            if (item == selectedIndex && regions.get(combo).equals(regions.get(item))) {
                if (rect.contains(Gdx.input.getX(), Gdx.input.getY()))
                    return true;
                else return false;
            } else
                return false;
        } else
            return false;
    }
    
    public boolean equalRegions(int item, int combo) {
        return regions.get(combo).equals(currentRegions.get(item));
    }
    
    public void input() {
        if (Gdx.input.justTouched()) {
            for (int j = 0; j < rect.size; j++) {
                if (rect.get(j).contains(Gdx.input.getX(), 950 - Gdx.input.getY())) {
                    if (!selected && i[j]) {
                        select.set(rect.get(j).x, rect.get(j).y);
                        selected = true;
                        selectedIndex = j;
                        CS400Game.manager.get("sfx/Donk.wav", Sound.class).play();
                    } else if (i[j]){
                        combine(j);
                        selected = false;
                    } else
                        selected = false;
                }
            }
        }
    }
    
    public void render(float deltaTime) {
        
        inventory.begin(ShapeRenderer.ShapeType.Line);
        inventory.setColor(Color.RED);
        inventory.rect(400, 0, 400, 150);
        if (selected) {
            inventory.rect(select.x, select.y, 100, 75);
        }
        inventory.end();
        
        
        batch.begin();
        if (i[0]) {//Bottom row
            batch.draw(currentRegions.get(0), 400, 0);
        }
        if (i[1]) {
            batch.draw(currentRegions.get(1), 500, 0);
        }
        if (i[2]) {
            batch.draw(currentRegions.get(2), 600, 0);
        }
        if (i[3]) {
            batch.draw(currentRegions.get(3), 700, 0);
        }
        if (i[4]) {//Top row
            batch.draw(currentRegions.get(4), 400, 75);
            batch.draw(currentRegions.get(10), 400, 75);
        }
        if (i[5]) {
            batch.draw(currentRegions.get(5), 500, 75);
        }
        if (i[6]) {
            batch.draw(currentRegions.get(6), 600, 75);
        }
        if (i[7]) {
            batch.draw(currentRegions.get(7), 700, 75);
        }
        batch.end();
    }
    
}
