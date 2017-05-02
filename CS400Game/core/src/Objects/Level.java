/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Objects;

import Tools.WorldBuilder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.CS400Game;

/**
 *
 * @author asieka01
 */
public class Level {
    
    protected CS400Game game;
    protected OrthographicCamera cam;
    protected Viewport viewport;
    /*protected TmxMapLoader maploader;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected World world;
    protected WorldBuilder worldBuilder;
    protected Box2DDebugRenderer physicDebug;*/
    protected Inventory inventoryGui;
    protected Music music;
    protected Array<Item> items;
    protected Array<Vector2> itemCombinations;

    protected Texture texture;//texture is the current background on screen
    protected Texture background;
    protected Array<TextureRegion> walls;
    protected TextureRegion currentWall;
    protected int textureIndex;//indicates index of current texture
    protected Array<Rectangle> objects;
    protected Array<String> messages;
    
    protected ShapeRenderer messageBox;
    
    protected Note note;
    
}
