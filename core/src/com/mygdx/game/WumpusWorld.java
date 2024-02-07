package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class WumpusWorld {
    public static int GROUND = 0, SPIDER = 1, PIT = 2, WUMPUS = 3, GOLD = 4; // <10 is black tile - fog of war
    public static int WEB = 11, WIND = 12, STINK = 13, GLITTER = 14;

    boolean visible[][] = new boolean[10][10];
    int[][] world = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    public static Texture groundTile, spiderTile, pitTile, wumpusTile, goldTile, webTile, windTile, glitterTile, stinkTile, blackTile,
            emptyGoldTile; // empty chest


    private boolean showFog = true;
    public boolean isWumpusDead = false;


    public WumpusWorld() {
        groundTile = new Texture("groundTile.png");
        spiderTile = new Texture("spiderTile.png");
        pitTile = new Texture("pitTile.png");
        wumpusTile = new Texture("wumpusTile.png");
        goldTile = new Texture("goldTile.png");
        webTile = new Texture("webTile.png");
        windTile = new Texture("windTile.png");
        glitterTile = new Texture("glitterTile.png");
        stinkTile = new Texture("stinkTile.png");
        blackTile = new Texture("blackTile.png");
        emptyGoldTile = new Texture("emptyChest.png");
    }

    public int getValue(Location loc) {
        return world[loc.getRow()][loc.getCol()];
    }

    public void setWorld(int[][] world) {
        this.world = world;
    }

    public void placeTexture(Texture t, int x, int y) {
        //given an x and y coordinate, place a tile in the correct [row][col]
        Location loc = convertCoordsToLoc(x, y);
        if(isValid(loc.getRow(), loc.getCol())) {
            if(t.toString().equals("spiderTile.png")) {
                world[loc.getRow()][loc.getCol()] = SPIDER;
            } else if(t.toString().equals("goldTile.png")) {
                world[loc.getRow()][loc.getCol()] = GOLD;
            } else if(t.toString().equals("wumpusTile.png")) {
                world[loc.getRow()][loc.getCol()] = WUMPUS;
            } else if(t.toString().equals("pitTile.png")) {
                world[loc.getRow()][loc.getCol()] = PIT;
            } else if(t.toString().equals("groundTile.png")) {
                world[loc.getRow()][loc.getCol()] = GROUND;
            }
        }
        addHints(loc);
    }

    public boolean isWumpusDead() {
        return isWumpusDead;
    }
    // TODO: Stretch goal im tool azy to implement
    public void setWumpusDead(boolean wumpusDead) {
        isWumpusDead = wumpusDead;
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < world.length && col >= 0 && col < world[row].length;
    }

    public boolean isValid(Location loc) {
        return loc.getRow() >= 0 && loc.getRow() < world.length && loc.getCol() >= 0 && loc.getCol() < world[loc.getRow()].length;
    }

    public static Location convertCoordsToLoc(int x, int y) {
        int row, col;
        col = (x - 20) / 50;
        row = (y - 18) / 50;

        return new Location(row, col);
    }

    // Location loc is the location of the object place hints around it

    public void addHints(Location loc) {
        ArrayList<Location> arr = getValidNeighbors(loc); // gets our neighbors
        for(int i = 0; i < arr.size(); i++) {
            if(!(world[loc.getRow()][loc.getCol()] == 0)) {
                world[arr.get(i).getRow()][arr.get(i).getCol()] = world[loc.getRow()][loc.getCol()] + 10;
            } else {
                world[arr.get(i).getRow()][arr.get(i).getCol()] = 0;
            }
        }
    }

    public static int convertLocToX(Location loc) {
        return loc.getCol() * 50 + 20;
    }

    public static int convertLocToY(Location loc) {
        return 718 - (loc.getRow() * 50 + 18);
    }

    public void setVisibile(Location loc) {
        if(isValid(loc.getRow(), loc.getCol())) {
            visible[loc.getRow()][loc.getCol()] = true;
        }
    }

    public boolean isShowFog() {
        return showFog;
    }

    public void setShowFog(boolean showFog) {
        this.showFog = showFog;
    }

    public ArrayList<Location> getValidNeighbors(Location loc) {
        ArrayList<Location> arr = new ArrayList<>();
        Location above = new Location(loc.getRow() + 1, loc.getCol());
        Location below = new Location(loc.getRow() - 1, loc.getCol());
        Location right = new Location(loc.getRow(), loc.getCol() + 1);
        Location left = new Location(loc.getRow(), loc.getCol() - 1);
        if(isValid(above.getRow(), above.getCol())) {
            arr.add(above);
        }
        if(isValid(below.getRow(), below.getCol())) {
            arr.add(below);
        }
        if(isValid(right.getRow(), right.getCol())) {
            arr.add(right);
        }
        if(isValid(left.getRow(), left.getCol())) {
            arr.add(left);
        }
        return arr;
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                // row = y, col = x
                if (visible[row][col] || !showFog) {
                    if (world[row][col] == GROUND) {
                        spriteBatch.draw(groundTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == SPIDER) {
                        spriteBatch.draw(spiderTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == WUMPUS) {
                        spriteBatch.draw(wumpusTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == GLITTER) {
                        spriteBatch.draw(glitterTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == PIT) {
                        spriteBatch.draw(pitTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == WIND) {
                        spriteBatch.draw(windTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == GOLD) {
                        spriteBatch.draw(goldTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == WEB) {
                        spriteBatch.draw(webTile, 20 + col * 50, 700 - row * 50);
                    } else if (world[row][col] == STINK) {
                        spriteBatch.draw(stinkTile, 20 + col * 50, 700 - row * 50);
                    }
                }
            }
        }
    }
}
