/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author asieka01
 */
public class Analysis {
    
    private BitmapFont font;
    private SpriteBatch batch;
    private String mousePos, screenPos;
    
    public Analysis() {
        
        batch = new SpriteBatch();
        font = new BitmapFont();
        mousePos = "";
        screenPos = "";
    }
    
    public void collect() {
        mousePos = "Mouse: " + Gdx.input.getX() + ", " + Gdx.input.getY();
    }
    
    public void render(float deltaTime) {
        batch.begin();
        font.draw(batch, mousePos, 200, 400, 100, 10, true);
        batch.end();
    }
    
}
