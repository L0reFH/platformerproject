package ru.gamesun.platformer.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.Vector;

public class BulletActor extends Actor {

    private Texture texture;
    private Vector2 velocity;
    private Rectangle boundary;

    public BulletActor(Texture texture, float startX, float startY, int direction, Rectangle enemyBoundary) {
        this.texture = texture;
        velocity = new Vector2(3 * direction, 0);
        setX(startX);
        setY(startY);
        boundary = new Rectangle(getX(), getY() + 5, 25, 25);
        setHeight(25);
        setWidth(25);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        moveBy(velocity.x, velocity.y);
        boundary.x = getX();
        boundary.y = getY() + 5;

        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof Solid) {
                Solid solid = (Solid) actor;
                if (boundary.overlaps(solid.getBoundary())) {
                    remove();
                }
            }
            if (actor instanceof EnemyActor) {
                EnemyActor enemyActor = (EnemyActor) actor;
                if (boundary.overlaps(enemyActor.getBoundary())) {
                    remove();
                    enemyActor.remove();
                }
            }
        }
    }
}
