/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Screens.PointAndClick;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 *
 * @author asieka01
 */
public class ChemistryPuzzle extends Puzzle {
    
    Texture b1, b2, scale; //beaker b1 and b2
    BitmapFont font;
    String w1, w2, equation; //weight w1 and w2 of b1 and b2, repsectively
    ShapeRenderer bg;
    
    public ChemistryPuzzle() {
        //b1 = new Texture("");
        //b2 = new Texture("");
        //scale = new Texture("");
        
        w1 = "";
        w2 = "";
        equation = "";
        
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.setColor(Color.GREEN);
        
        bg = new ShapeRenderer();
    }
    
    public void render(float deltaTime) {
        bg.begin(ShapeType.Filled);
        bg.rect(100, 200, 600, 600);
        bg.setColor(Color.WHITE);
        bg.end();
    }
    
}
