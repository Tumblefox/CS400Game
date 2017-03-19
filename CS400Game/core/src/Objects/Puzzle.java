/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import Screens.PointAndClick;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author asieka01
 */
public abstract class Puzzle {
    private World world;
    private String prompt;
    private float[] variables;
    private float formula;

    /*public Puzzle() {
    }*/

    public Puzzle(PointAndClick screen) {}
    
    public void checkAnswer() {}
    
    public void update(float deltaTime) {}
    
    public void render(float deltaTime) {}
    
    public void dispose() {}
    
}
