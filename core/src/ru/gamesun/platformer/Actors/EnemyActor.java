package ru.gamesun.platformer.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import ru.gamesun.platformer.States.KoalaStatesEnum;

public class EnemyActor extends Actor {
    private OrthographicCamera camera;
    private Sprite standTexture;
    private KoalaStatesEnum koalaState;
    int mapWidth;
    int mapHeight;
    private Vector2 velocity;
    private static int HORISONTAL_SPEED;
    private static int DECELERATION;
    private static int GRAVITY;
    private Rectangle boundary;
    private Rectangle rightSensor;
    private Rectangle leftSensor;
    private Rectangle topSensor;
    private Rectangle bottomSensor;
    private float jumpStartY;
    private int direction = 1; // -1 || 1

    Texture bulletTexture;

    public EnemyActor() {
        Texture texture = new Texture("koala/stand.png");
        standTexture = new Sprite(texture);
        koalaState = KoalaStatesEnum.STAND;
        velocity = new Vector2();
        HORISONTAL_SPEED = 5;
        DECELERATION = 3;
        GRAVITY = 5;
        setY(200);
        setX(150);
        setHeight(texture.getHeight());
        setWidth(texture.getWidth());
        setOriginX(texture.getWidth()/2);

        boundary = new Rectangle(getX(), getY(), getWidth(), getHeight());
        rightSensor = new Rectangle(getX() + getWidth(), getY(), 1, getHeight());
        leftSensor = new Rectangle(getX(), getY(), 1, getHeight());
        topSensor = new Rectangle(getX(), getY() + getHeight(), getWidth(), 1);
        bottomSensor = new Rectangle(getX(), getY(), getWidth(), 1);

        bulletTexture = new Texture("bullet.png");

    }
    @Override
    public void draw(Batch batch, float parentAlpha) { ;
        batch.draw(standTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), direction * getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        velocity.y = -GRAVITY;
        velocity.x = HORISONTAL_SPEED * direction;

        if(Math.abs(velocity.x) > 1){
            if(velocity.x > 0){
                velocity.add(-DECELERATION, 0);
            }
            else{
                velocity.add(DECELERATION, 0);
            }
        }

        else{
            velocity.x = 0;
        }

        boundary.x = getX();
        boundary.y = getY();

        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if(actor instanceof Solid){
                Solid solid = (Solid) actor;
                if(bottomSensor.overlaps(solid.getBoundary())){
                    velocity.y = velocity.y + GRAVITY;
                }
                if(rightSensor.overlaps(solid.getBoundary())){
                   direction = -1;
                }
                if(leftSensor.overlaps(solid.getBoundary())){
                    direction = 1;
                }
                if(topSensor.overlaps(solid.getBoundary())){
                    koalaState = KoalaStatesEnum.STAND;
                }
            }
            if(actor instanceof KoalaActor){
                KoalaActor koalaActor = (KoalaActor) actor;
                if (boundary.overlaps(koalaActor.getBoundary())){
                    koalaActor.remove();
                    Label.LabelStyle labelStyle = new Label.LabelStyle();
                    labelStyle.font =  new BitmapFont();
                    Label label = new Label("Game Over!", labelStyle);
                    stage.addActor(label);
                }
            }
        }

        moveBy(velocity.x, velocity.y);
        bottomSensor.x = getX();
        bottomSensor.y = getY();
        rightSensor.x = getX() + getWidth() - 5;
        rightSensor.y = getY() + 5;
        leftSensor.x = getX();
        leftSensor.y = getY() + 5;
        topSensor.x = getX();
        topSensor.y = getY() + getHeight();
    }

    public Rectangle getBoundary() {
        return boundary;
    }
}
