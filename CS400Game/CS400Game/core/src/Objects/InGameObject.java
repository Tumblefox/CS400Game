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
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
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
    private float x, y;
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

        
        if (object instanceof RectangleMapObject) {
            rectBounds = ((RectangleMapObject) object).getRectangle();
            x = rectBounds.x;
            rectBounds.y = 800 - rectBounds.y;
            y = rectBounds.y;
        }
        else if (object instanceof EllipseMapObject) {
            ellipseBounds = ((EllipseMapObject) object).getEllipse();
            x = ellipseBounds.x;
            ellipseBounds.y = 800 - ellipseBounds.y;
            y = ellipseBounds.y;
        }
        else {
            polyBounds = ((PolylineMapObject) object).getPolyline();
            x = polyBounds.getX();
            polyBounds.setPosition(x, 800 - polyBounds.getY());
            y = polyBounds.getY();
        }
        /*if (object.getProperties().get("type", String.class).equals("ellipse")) {
            ellipseBounds = object.getProperties().get("ellipse", Ellipse.class);
        }
        if (object.getProperties().get("type", String.class).equals("polyline")) {
            polyBounds = object.getProperties().get("polyline", Polyline.class);
        }*/
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
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public Shape2D getShape() {
        if (object instanceof RectangleMapObject) {
            return rectBounds;
        }
        else if (object instanceof EllipseMapObject) {
            return ellipseBounds;
        }
        else {
            return polyBounds;
        } 
    }
    
    public void showInteraction() {
        showInteraction = true;
    }
}
