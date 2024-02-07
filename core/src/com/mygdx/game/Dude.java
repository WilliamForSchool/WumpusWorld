package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Dude {
    private Texture texture;
    private Location loc;
    private boolean hasGold = false;
    private WumpusWorld worldd;
    private boolean isDead = false;


    public Dude(Location myLoc, WumpusWorld world) {
        texture = new Texture("guy.png");
        worldd = world;
        loc = myLoc;
        worldd.setVisibile(loc);
    }

    public boolean isHasGold() {
        return hasGold;
    }

    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Location getLoc() {
        return loc;
    }

    public void moveRight() {
        if(worldd.isValid(loc.getRow(), loc.getCol() + 1)) {
            loc.setCol(loc.getCol() + 1);
            worldd.setVisibile(loc);
        }

    }
    public void moveLeft() {
        if(worldd.isValid(loc.getRow(), loc.getCol() - 1)) {
            loc.setCol(loc.getCol() - 1);
            worldd.setVisibile(loc);
        }
    }
    public void moveUp() {
        if(worldd.isValid(loc.getRow() - 1, loc.getCol())) {
            loc.setRow(loc.getRow() - 1);
            worldd.setVisibile(loc);
        }
    }
    public void moveDown() {
        if(worldd.isValid(loc.getRow() + 1, loc.getCol())) {
            loc.setRow(loc.getRow() + 1);
            worldd.setVisibile(loc);
        }
    }

    public ArrayList<Location> getValidLocsAroundMe(Location loc) {
        ArrayList<Location> arr = new ArrayList<>();
        Location x = new Location(loc.getRow() + 1, loc.getCol());
        Location y = new Location(loc.getRow() - 1, loc.getCol());
        Location z = new Location(loc.getRow(), loc.getCol() + 1);
        Location i = new Location(loc.getRow(), loc.getCol() - 1);
        if(worldd.isValid(x)) {
            arr.add(x);
        }
        if(worldd.isValid(y)) {
            arr.add(y);
        }
        if(worldd.isValid(z)) {
            arr.add(z);
        }
        if(worldd.isValid(i)) {
            arr.add(i);
        }
        return arr;
    }


    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void draw(SpriteBatch batch) {
        if (!isDead) {
            batch.draw(texture, WumpusWorld.convertLocToX(loc), WumpusWorld.convertLocToY(loc));
        }
    }
}
