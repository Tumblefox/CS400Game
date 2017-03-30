/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import Screens.PointAndClick;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author asieka01
 */
public class InGameObject {
    private String name;
    private String message;
    private String interaction;
    private boolean showInteraction;
    //private World world;
    private Rectangle rectBounds;
    private Polyline polyBounds;
    private Ellipse ellipseBounds;
    //private Body body;
    private Screen screen;
    private MapObject object;
    private Fixture fixuture;
    
    public InGameObject(PointAndClick screen, String objectName, String layer) {
        this.screen = screen;
        
        name = objectName;
        MapLayer temp = screen.getMap().getLayers().get(layer);
        object = temp.getObjects().get(name);
        
        message = object.getProperties().get("message", String.class);
        interaction = object.getProperties().get("interaction", String.class);
        showInteraction = false;

        
        if (object.getProperties().get("type", String.class).equals("rectangle")) {
            rectBounds = object.getProperties().get("rectangle", Rectangle.class);
        }
        if (object.getProperties().get("type", String.class).equals("ellipse")) {
            ellipseBounds = object.getProperties().get("ellipse", Ellipse.class);
        }
        if (object.getProperties().get("type", String.class).equals("polyline")) {
            polyBounds = object.getProperties().get("ellipse", Polyline.class);
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getText() {
        if (showInteraction) {
            return interaction;
        }
        return message;
    }
    
    public Rectangle getRect() {
        return rectBounds;
    }
    
    public Ellipse getEllipse() {
        return ellipseBounds;
    }
    
    public Polyline getPolyline() {
        return polyBounds;
    }
    
    public void showInteraction() {
        showInteraction = true;
    }
}
