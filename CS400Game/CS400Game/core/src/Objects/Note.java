/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 *
 * @author Tumblefox
 */
public class Note {
    private ShapeRenderer note;
    private String currentNote;
    private BitmapFont font;
    private SpriteBatch batch;
    
    public Note() {
        note = new ShapeRenderer();
        currentNote = "";
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        batch = new SpriteBatch();
    }
    
    public void setNote(String note) {
        currentNote = note;
    } 
    
    public void render(float deltaTime) {
        note.begin(ShapeType.Filled);
        note.rect(100, 200, 600, 600);
        note.setColor(Color.WHITE);
        note.end();
        
        batch.begin();
        font.draw(batch, currentNote, 200, 600, 400, 10, true);
        batch.end();
    }
}
