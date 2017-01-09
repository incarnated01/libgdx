package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    float x, y, xv, yv;
    static final float MAX_VELOCITY = 100;
    TextureRegion up, down, left, right, stand, background;

    static final int WIDTH = 16;
    static final int HEIGHT = 16;

    static final int DRAW_WIDTH = WIDTH*3;
    static final int DRAW_HEIGHT = HEIGHT*3;


    float acceleration = 2f;

    @Override
    public void create () {
        batch = new SpriteBatch();

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        stand = grid [6][2];
        left.flip(true, false);
        background = grid[5] [1];
    }

    @Override
    public void render () {
        move();



        TextureRegion img;
        if (yv < 0) {
            img = down;
        } else if (yv > 0){
            img = up;
        } else if (xv < 0) {
            img = left;
        } else if (xv > 0) {
            img = right;
        } else {
            img = stand;
        }


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, x, y, DRAW_HEIGHT, DRAW_WIDTH);
        batch.end();
    }

    float decelerate(float velocity) {
        float deceleration = 0.95f;
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * acceleration;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY * acceleration;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY * acceleration;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.LEFT))  {
            xv = MAX_VELOCITY * acceleration;

        }

        y += yv * Gdx.graphics.getDeltaTime();
        x += xv * Gdx.graphics.getDeltaTime();

        yv = decelerate(yv);
        xv = decelerate(xv);

        if (y < 0 ){
            y = Gdx.graphics.getHeight();
        }
        if (y > Gdx.graphics.getHeight()) {
            y = 0;
        }
        if (x < 0) {
            x = Gdx.graphics.getWidth();
        }
        if (x > Gdx.graphics.getWidth()) {
            x = 0;
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}


