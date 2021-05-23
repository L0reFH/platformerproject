package ru.gamesun.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import ru.gamesun.platformer.Actors.BulletActor;
import ru.gamesun.platformer.Actors.EnemyActor;
import ru.gamesun.platformer.Actors.KoalaActor;
import ru.gamesun.platformer.Actors.Solid;

public class Platformer extends ApplicationAdapter {
    TmxMapLoader loader;
    TiledMap map1;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    public static final int SCREENHEIGHT = 640;
    public static final int SCREENWIDTH = 800;
    private Stage stage;
    KoalaActor koalaActor;
    MapProperties mapProperties;
    private int mapWidth;
    private int mapHeight;
    MapLayers layers;
    BulletActor bulletActor;
    EnemyActor enemyActor;
    ImageButton upButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ImageButton downButton;
    ImageButton fireButtonTexture;

    @Override
    public void create() {
        loader = new TmxMapLoader();
        map1 = loader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map1);
        mapProperties = map1.getProperties();
        Integer mapCountTilesByWidth = (Integer) mapProperties.get("width");
        Integer mapCountTilesByHeight = (Integer) mapProperties.get("height");
        Integer tileWidth = (Integer) mapProperties.get("tilewidth");
        Integer tileHeight = (Integer) mapProperties.get("tileheight");
        mapWidth = mapCountTilesByWidth * tileWidth;
        mapHeight = mapCountTilesByHeight * tileHeight;

        stage = new Stage();
        camera = (OrthographicCamera) stage.getCamera();
        camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
        renderer.setView(camera);

        layers = map1.getLayers();
        for (MapLayer layer : layers) {
            if (layer instanceof MapLayer) {
                MapObjects objects = layer.getObjects();
                for (MapObject object : objects) {
                    MapProperties objectProperties = object.getProperties();
                    float x = (Float) objectProperties.get("x");
                    float y = (Float) objectProperties.get("y");
                    float width = (Float) objectProperties.get("width");
                    float height = (Float) objectProperties.get("height");
                    if (objectProperties.containsKey("name")) {
                        String name = (String) objectProperties.get("name");
                        if (name.equals("Solid")) {
                            Solid solid = new Solid(x, y, width, height);
                            stage.addActor(solid);
                        }
                    }

                }
            }
        }
        stage.addActor(new Solid(-5, 0, 5, SCREENHEIGHT));
        stage.addActor(new Solid(mapWidth + 5, 0, 5, SCREENHEIGHT));
        koalaActor = new KoalaActor(camera, mapWidth, mapHeight);
        enemyActor = new EnemyActor();
        stage.addActor(koalaActor);
        stage.addActor(enemyActor);

        Gdx.input.setInputProcessor(stage);

        TextureRegionDrawable upButtonDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("up.jpg")));
        TextureRegionDrawable upButtonDrawableCh = new TextureRegionDrawable(new Texture(Gdx.files.internal("up_ch.jpg")));
        upButton = new ImageButton(upButtonDrawable, upButtonDrawableCh);
        upButton.setWidth(100);
        upButton.setHeight(100);
        upButton.setX(25);
        upButton.setY(25);

		TextureRegionDrawable leftButtonDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("left.jpg")));
		TextureRegionDrawable leftButtonDrawableCh = new TextureRegionDrawable(new Texture(Gdx.files.internal("left_ch.jpg")));
		leftButton = new ImageButton(leftButtonDrawable, leftButtonDrawableCh);
        leftButton.setWidth(100);
        leftButton.setHeight(100);
        leftButton.setX(25);
        leftButton.setY(25);

        stage.addActor(upButton);
        upButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                koalaActor.toUp();
            }
        });

        stage.addActor(leftButton);

        TextureRegionDrawable rightButtonDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("right.jpg")));
        TextureRegionDrawable rightButtonDrawableCh = new TextureRegionDrawable(new Texture(Gdx.files.internal("right_ch.jpg")));

        rightButton = new ImageButton(rightButtonDrawable, rightButtonDrawableCh);
        rightButton.setWidth(100);
        rightButton.setHeight(100);
        rightButton.setX(25);
        rightButton.setY(25);
        stage.addActor(rightButton);

        TextureRegionDrawable fireButtonDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("shoot.png")));

        fireButtonTexture = new ImageButton(fireButtonDrawable);
        fireButtonTexture.setWidth(150);
        fireButtonTexture.setHeight(150);
        fireButtonTexture.setX(575);
        fireButtonTexture.setY(25);
        stage.addActor(fireButtonTexture);

        fireButtonTexture.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                koalaActor.shoot();
            }
        });

    }

    @Override
    public void render() {
        renderer.setView(camera);
        renderer.render();
        stage.draw();
        stage.act();
		upButton.setX(camera.position.x - SCREENWIDTH / 2 + 125);
		upButton.setY(150);
		rightButton.setX(camera.position.x - SCREENWIDTH / 2 + 200);
		leftButton.setX(camera.position.x - SCREENWIDTH/2 + 50);
		fireButtonTexture.setX(camera.position.x - SCREENWIDTH/2 + 650);
		if(leftButton.isPressed()){
            koalaActor.toLeft();
        }

		if(rightButton.isPressed()){
		    koalaActor.toRight();
        }
    }

    @Override
    public void dispose() {
    }
}

