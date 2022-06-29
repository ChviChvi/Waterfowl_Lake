package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;

public class Player extends Sprite implements InputProcessor {




    // the movement velocity ( it holds x and y)
    private Vector2 velocity = new Vector2();

    // how fast the player moves around and graivty
    private float speed = 60 * 2, gravity = 60 * 1.8f;


    private TiledMapTileLayer collisionlayer;

    public Player(Sprite sprite, TiledMapTileLayer collisionplayer){
        super(sprite);
        this.collisionlayer = collisionplayer;
        sprite.setRegionHeight(10);
        sprite.getWidth();
    }


// he has SpritBatch spireBatch here.
    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public TiledMapTileLayer getCollisionlayer() {
        return collisionlayer;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setCollisionlayer(TiledMapTileLayer collisionlayer) {
        this.collisionlayer = collisionlayer;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.W:
                velocity.y = speed;
                break;
            case Input.Keys.S:
                velocity.y = -speed;
                break;
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.W:
                velocity.y = 0;
                break;
            case Input.Keys.S:
                velocity.y = 0;
                break;
            case Input.Keys.A:
                velocity.x = 0;
                break;
            case Input.Keys.D:
                velocity.x = 0;
                break;
        }
        return true;

    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


//
    public void update(float delta) {

//        // save old position
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

//        if(isCellBlocked(getX(),getY())){
//            //setX(oldX);
//            setX(getX() - velocity.x * delta* 1.5f);
//            //setY(oldY);
//            setY(getY() - velocity.y * delta * 1.5f);
//            System.out.println("this happened?1");
//
//        }
        // move on x
        setX(getX() + velocity.x * delta* 1.5f);
        if(velocity.x < 0) {
            collisionX = collidesLeft();
        } else if (velocity.x > 0){
            collisionX =collidesRight();
        }
        if(collisionX){
            setX(oldX);
            velocity.x=0;
        }
        // move on y
        setY(getY() + velocity.y * delta * 1.5f);
        if(velocity.y < 0){
            collisionY = collidesBottom();
        } else if (velocity.y > 0){
            collisionY = collidesTop();
        }
        if(collisionY){
            setY(oldY);
            velocity.y=0;
        }
    }


    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = null;
        boolean blocked = false;

        try {
            cell = collisionlayer.getCell((int) (x / collisionlayer.getTileWidth()), (int) (y / collisionlayer.getTileHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cell != null && cell.getTile() != null) {

            if (cell.getTile().getProperties().containsKey("blocked")) {
                System.out.println("this happened?2");
                blocked = true;
            }
        }
        return blocked;
    }
    public boolean collidesRight(){
        boolean collides = false;

        for(float step = 0; step < getHeight(); step += (collisionlayer.getTileHeight()) /2){
            collides = isCellBlocked(getX() +getWidth() ,getY()+ step);
            if(collides)
                break;
        }

        return collides;
    }
    public boolean collidesLeft(){
        boolean collides = false;

        for(float step = 0; step < getHeight(); step += collisionlayer.getTileHeight() /2){
            collides = isCellBlocked(getX() + step,getY());
            if(collides)
                break;
        }

        return collides;
    }
    public boolean collidesBottom(){
        boolean collides = false;

        for(float step = 0; step < getWidth(); step += collisionlayer.getTileWidth() /2){
            collides = isCellBlocked(getX() + step,getY());
            if(collides)
                break;
        }

        return collides;
    }
    public boolean collidesTop(){
        boolean collides = false;

        for(float step = 0; step < getWidth(); step += collisionlayer.getTileWidth() /2){
            collides = isCellBlocked(getX() + step,getY() + getHeight());
            if(collides)
                break;
        }

        return collides;
    }
}
//
//
//    public boolean collidesRight() {
//        for(float step = 0; step <= getHeight(); step += increment)
//            if(isCellBlocked(getX() + getWidth(), getY() + step))
//                return true;
//        return false;
//    }
//
//    public boolean collidesLeft() {
//        for(float step = 0; step <= getHeight(); step += increment)
//            if(isCellBlocked(getX(), getY() + step))
//                return true;
//        return false;
//    }
//
//    public boolean collidesTop() {
//        for(float step = 0; step <= getWidth(); step += increment)
//            if(isCellBlocked(getX() + step, getY() + getHeight()))
//                return true;
//        return false;
//    }
//
//    public boolean collidesBottom() {
//        for(float step = 0; step <= getWidth(); step += increment)
//            if(isCellBlocked(getX() + step, getY()))
//                return true;
//        return false;
//    }