package ru.gamesun.platformer.Actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Solid extends Actor {

    private Rectangle boundary;

    public Solid(float x, float y, float width, float height) {
        setDebug(true);
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);

        boundary = new Rectangle(x,y,width,height);
    }

    public Rectangle getBoundary(){
        return boundary;
    }
}
