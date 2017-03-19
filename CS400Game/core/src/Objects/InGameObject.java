/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author asieka01
 */
public abstract class InGameObject {
    private World world;
    //private TiledMap map;
    private Rectangle bounds;
    //private Body body;
    private Screen screen;
    private MapObject object;
    private Fixture fixuture;
    
    //InGameObject()
}
