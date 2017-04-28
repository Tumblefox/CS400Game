/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.text.DecimalFormat;

/**
 *
 * @author asieka01
 */
public class ChemistryPuzzle extends Puzzle {
    private Array<TextureRegion> regions;
    private Texture texture;
    private BitmapFont font;
    private String w1, w2, equation; //weight w1 and w2 of b1 and b2, repsectively
    private Vector2 b1Pos, b2Pos, scalePos, currentB1Pos, currentB2Pos, currentScalePos;
    private Rectangle b1, b2, scale, window;
    private double b1Weight, b2Weight, ratio;
    private boolean beaker1, beaker2, b1Drag, b2Drag, solved;
    private String substance1, substance2, numerator;
    private DecimalFormat formatter;
    private ShapeRenderer bg, rect;
    private SpriteBatch batch;
    
    public ChemistryPuzzle(double ratio, String numerator) {
        texture = new Texture("Chemistry.png");
        
        regions = new Array();
        regions.add(new TextureRegion(texture, 0, 0, 103, 92));
        b1Pos = new Vector2();
        b1Pos.set(150, 400);
        currentB1Pos = b1Pos;
        b1 = new Rectangle(currentB1Pos.x, currentB1Pos.y, regions.get(0).getRegionWidth(), regions.get(0).getRegionHeight());
        
        regions.add(new TextureRegion(texture, 116, 0, 270, 92));
        scalePos = new Vector2();
        scalePos.set(150 + regions.get(0).getRegionWidth(), 400);
        currentScalePos = scalePos;
        scale = new Rectangle(currentScalePos.x, currentScalePos.y, regions.get(1).getRegionWidth(), regions.get(1).getRegionHeight());
        
        regions.add(new TextureRegion(texture, 396, 0, 103, 92));
        b2Pos = new Vector2();
        b2Pos.set(150 + regions.get(0).getRegionWidth() + regions.get(1).getRegionWidth(), 400);
        currentB2Pos = b2Pos;
        
        b2 = new Rectangle(currentB2Pos.x, currentB2Pos.y, regions.get(2).getRegionWidth(), regions.get(2).getRegionHeight());
        
        w1 = "Weight 1: ";
        b1Weight = 10; //could be set from outside the class
        w2 = "Weight 2: ";
        b2Weight = 10;
        equation = "";
        
        beaker1 = true;
        beaker2 = false;
        b1Drag = false;
        b2Drag = false;
        solved = false;
        
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.setColor(Color.RED);
        
        bg = new ShapeRenderer();
        rect = new ShapeRenderer();
        
        window = new Rectangle(100, 200, 600, 600);//used to check if pointer is inside the window
        
        substance1 = "nothing";
        substance2 = "nothing";
        
        formatter = new DecimalFormat("#0.00");
        
        this.ratio = ratio;
        this.numerator = numerator;
    }
    
    public void moveB1(int x, int y) {
        currentB1Pos.set(x, y);
        b1.setPosition(x, y);
        b1Drag = true;
    }
    
    public void moveB2(int x, int y) {
        currentB2Pos.set(x, y);
        b2.setPosition(x, y);
        b2Drag = true;
    }
    
    public void weigh() {
        if (scale.contains(Gdx.input.getX(), Gdx.input.getY())) {
            if (b1Drag) {
                 w1 = formatter.format(b1Weight) + " oz of " + substance1;
            }
            if (b2Drag) {
                w2 = formatter.format(b2Weight) + " oz of " + substance2;
            }
        }
        checkAnswer(ratio, numerator);
    }
    
    public void addToB1(Item substance) {
        if (substance1 == "nothing" && substance2 != substance.name()) {
            substance1 = substance.name();
            b1Weight += substance.getWeight();
        }
        else if (substance1 == substance.name())
            b1Weight += substance.getWeight();
    }
    
    public void addToB2(Item substance) {
        if (substance2 == "nothing" && substance1 != substance.name()) {
            substance2 = substance.name();
            b2Weight += substance.getWeight();
        }
        else if (substance2 == substance.name())
            b2Weight += substance.getWeight();
    }
    
    //public Rectangle getB1Rect() {
    //    return b1;
    //}
    
    public Rectangle getWindow() {
        return window;
    }
    
    public Rectangle activeBeakerRect() {
        if (beaker1)
            return b1;
        else
            return b2;
    }
    
    public boolean usingbeaker1() {
        return beaker1;
    }
    
    public void resetPlacement() {
        currentB1Pos.set(150, 400);
        b1.setPosition(currentB1Pos.x, currentB1Pos.y);
        b1Drag = false;
        currentB2Pos.set(150 + regions.get(0).getRegionWidth() + regions.get(1).getRegionWidth(), 400);
        b2.setPosition(currentB2Pos.x, currentB2Pos.y);
        b2Drag = false;
        //currentScalePos = new Vector2(150 + regions.get(0).getRegionWidth(), 400);
    }
    
    public void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            beaker1 = true;
            beaker2 = false;
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            beaker1 = false;
            beaker2 = true;
        }
        
        if (Gdx.input.isTouched() && window.contains(Gdx.input.getX(), 950 - Gdx.input.getY())) {
            if (beaker1) {
                moveB1(Gdx.input.getX() - 50, 900 - Gdx.input.getY());
            } else {
                moveB2(Gdx.input.getX() - 50, 900 - Gdx.input.getY());
            }
        } else {
            resetPlacement();
        }
        
        weigh();
    }
    
    public void checkAnswer(double substanceRatio, String num) {
        if (substance1 == num && substance2 != "nothing") {
            if (((b1Weight - 10d) / (b2Weight - 10d)) - substanceRatio < 0.01 && ((b1Weight - 10d) / (b2Weight - 10d)) - substanceRatio > -0.01) {
                font.setColor(Color.GREEN);
                solved = true;
            } else {
                font.setColor(Color.RED);
                solved = false;
            }
        }
        if (substance2 == num && substance1 != "nothing") {
            if (((b2Weight - 10d) / (b1Weight - 10d)) - substanceRatio < 0.01 && ((b2Weight - 10d) / (b1Weight - 10d)) - substanceRatio > -0.01) {
                font.setColor(Color.GREEN);
                solved = true;
            } else {
                font.setColor(Color.RED);
                solved = false;
            }
        }
    }
    
     public boolean isSolved() {
         return solved;
     }
    
    public void render(float deltaTime) {
        bg.begin(ShapeType.Filled);
        bg.rect(100, 200, 600, 600);
        bg.setColor(Color.WHITE);
        bg.end();
        
        rect.begin(ShapeType.Line);
        rect.setColor(Color.BLUE);
        rect.rect(b1.x, b1.y, b1.width, b1.height);
        rect.rect(b2.x, b2.y, b2.width, b2.height);
        rect.rect(scale.x, scale.y, scale.width, scale.height);
        rect.end();
        
        batch.begin();
        
        batch.draw(regions.get(1), currentScalePos.x, currentScalePos.y); //beaker 1
        batch.draw(regions.get(0), currentB1Pos.x, currentB1Pos.y);
        batch.draw(regions.get(2), currentB2Pos.x, currentB2Pos.y); //beaker 2
        
        font.draw(batch, w1, 150, 400, 100, 10, true);
        font.draw(batch, w2, 550, 400, 100, 10, true);
        //font.draw(batch, "" + (((b1Weight - 10) / (b2Weight - 10)) - 0.3375), 150, 600, 100, 10, true);
        
        batch.end();
        
        
    }
    
}
