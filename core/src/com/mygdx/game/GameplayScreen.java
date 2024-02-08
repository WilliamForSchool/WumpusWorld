package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Stack;

public class GameplayScreen implements Screen {

    private static final float WORLD_WIDTH = 1024;
    private static final float WORLD_HEIGHT = 768;

    //Object that allows us to draw all our graphics
    private SpriteBatch spriteBatch;

    // Fonts
    BitmapFont defaultFont;

    //Object that allows us to draw shapes
    private ShapeRenderer shapeRenderer;

    //Camera to view our virtual world
    private Camera camera;
    private WumpusWorld wumpusWorld;

    //control how the camera views the world
    //zoom in/out? Keep everything scaled?
    private Viewport viewport;

    Texture selected;
    Texture trophy;

    boolean aiRunning = false;
    boolean gottenGold = false;

    int actualScore;
    Dude dude;
    int score = 1000;
    private Stack<Location> moves = new Stack<>();

    //timer var?
    private long delay = 50; // ms
    private long nextMove = System.currentTimeMillis() + delay;

    //runs one time, at the very beginning
    //all setup should happen here
    @Override
    public void show() {
        camera = new OrthographicCamera(); //2D camera
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);//move the camera
        camera.update();


        //freeze my view to 800x600, no matter the window size
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true); //???, I just know that this was the solution to an annoying problem

        defaultFont = new BitmapFont();
        wumpusWorld = new WumpusWorld();
        dude = new Dude(new Location(9,0), wumpusWorld);
        trophy = new Texture("trophy.png");
    }

    public void ai1() { // move right only AI
        if(nextMove < System.currentTimeMillis()) {
            if(wumpusWorld.isValid(dude.getLoc().getRow(), dude.getLoc().getCol() + 1)) {
                moves.push(dude.getLoc());
                System.out.println(dude.getValidLocsAroundMe(dude.getLoc()));
                dude.setLoc(new Location(dude.getLoc().getRow(), dude.getLoc().getCol() + 1));
                wumpusWorld.setVisibile(dude.getLoc());
                System.out.println("moved");
            } else {
                dude.setLoc(moves.peek());
                moves.pop();

            }
            score--;
            nextMove = System.currentTimeMillis() + delay;
        }
    }

    private void drawPTables(int[][] tables) {
        for (int row = 0; row < tables.length; row++) {
            for (int col = 0; col < tables[row].length; col++) {
                defaultFont.draw(spriteBatch, "" + tables[row][col], 24 + col * 50, 740 - row * 50);
            }
        }
    }



    // JANKY BUT KEEP IT OUT OF METHOD
    int x = 2;
    public int[][] pTables = {
            {20, 16, 1, 1, -1, 1, 4, 2, 2, 2},
            {16, 6, 2, 1, -1, 1, 1, 1, 4, 4},
            {14, 2, 2, 2, -1, 1, 1, 6, 1, 4},
            {12, 1, 2, 5, 1, 10, 8, 1, 1, 4},
            {8, 9, 10, 2, 20, 20, 6, 1, 1, 4},
            {2, 8, 1, 1, 8, 20, 8, 1, 1, 4},
            {6, 2, 8, 8, 7, 4, 20, 15, 1, 4},
            {3, 6, 6, 1, 8, 1, 6, 20, 6, 4},
            {2, 4, 6, 1, -1, 1, 1, 8, 20, 4},
            {2, 2, 2, 1, -1, 1, 1, 1, 10, 4}
    };


    public int[][] pTablesBase = {
            {20, 16, 1, 1, -1, 1, 4, 2, 2, 2},
            {16, 6, 2, 1, -1, 1, 1, 1, 4, 4},
            {14, 2, 2, 2, -1, 1, 1, 6, 1, 4},
            {12, 1, 2, 5, 1, 10, 8, 1, 1, 4},
            {8, 9, 10, 2, 20, 20, 6, 1, 1, 4},
            {2, 8, 1, 1, 8, 20, 8, 1, 1, 4},
            {6, 2, 8, 8, 7, 4, 20, 15, 1, 4},
            {3, 6, 6, 1, 8, 1, 6, 20, 6, 4},
            {2, 4, 6, 1, -1, 1, 1, 8, 20, 4},
            {2, 2, 2, 1, -1, 1, 1, 1, 10, 4}
    };


    public int[][] getOutMode = {
            {60, 60, 60, 60, 60, 60, 60, 60, 60, 60},
            {86, 86, 86, 86, 86, 86, 86, 86, 86, 60},
            {90, 90, 90, 90, 90, 90, 90, 90, 86, 60},
            {100, 100, 100, 100, 100, 100, 100, 90, 86, 60},
            {120, 120, 120, 120, 120, 120, 100, 90, 86, 60},
            {200, 200, 200, 200, 200, 120, 100, 90, 86, 60},
            {300, 300, 300, 300, 200, 120, 100, 90, 86, 60},
            {400, 400, 400, 300, 200, 120, 100, 90, 86, 60},
            {599, 599, 400, 300, 200, 120, 100, 90, 86, 60},
            {1000, 599, 300, 200, 86, 60, 90, 86, 86, 60}
    };
    public boolean getOut = false;

    private int[][] checkBoardForBaddies(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int c = 0;
                if (wumpusWorld.isValid(row + 1, col)) {
                    if (board[row + 1][col] < -8000) {
                        c++;
                    }
                }
                if (wumpusWorld.isValid(row - 1, col)) {
                    if (board[row - 1][col] < -8000) {
                        c++;
                    }
                }
                if (wumpusWorld.isValid(row, col + 1)) {
                    if (board[row][col + 1] < -8000) {
                        c++;
                    }
                }
                if (wumpusWorld.isValid(row, col - 1)) {
                    if (board[row][col - 1] < -8000) {
                        c++;
                    }
                }
                if (c == 3) {
                    board[row][col] = -10000;
                }
            }
        }
        return board;
    }

    private void IncentivizeUnknown(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if(board[row][col]== pTablesBase[row][col]) {
                    board[row][col] += 1;
                    pTablesBase[row][col] += 1;
                }
            }
        }
    }

    private Location getMax(Location loc, int[][] board) {
        ArrayList<Location> arr = dude.getValidLocsAroundMe(loc);
        Location max = arr.get(0);
        for (Location temp : arr) {
            if(!(temp.equals(dude.getLoc())) && board[max.getRow()][max.getCol()] < board[temp.getRow()][temp.getCol()]) {
                System.out.println("new max");
                max = temp;
            }
        }
        return max;
    }



    private Location actAccordingly(Location loc) {

        Location max = getMax(loc, pTables);
        System.out.println(max);
        ArrayList<Location> arr = dude.getValidLocsAroundMe(dude.getLoc());
        if(wumpusWorld.getValue(loc) >= 10 && wumpusWorld.getValue(loc) < 14) { // indicators

            for(Location locs: arr) {
                if(!locs.equals(dude.getLoc())) {
                    pTables[locs.getRow()][locs.getCol()] += 10;
                }
            }


            moves.pop();
            pTables[loc.getRow()][loc.getCol()] -= 10000;
            pTables[moves.peek().getRow()][moves.peek().getCol()] -= 15;
            return moves.peek();
        } else if(wumpusWorld.getValue(dude.getLoc()) == 14) { // glitter
            if(!getOut) {
                pTables[dude.getLoc().getRow()][dude.getLoc().getCol()] += 30;
                for (Location temp2 : arr) {
                    pTables[temp2.getRow()][temp2.getCol()] += 15;
                }
            }
            return max;
        } else if(wumpusWorld.getValue(dude.getLoc()) == 4) { // gold
            getOut = true;
            dude.setHasGold(true);
        }
        else {
            return max;
        }
        return max;
    }


    public void ai2() {
        if (nextMove < System.currentTimeMillis()) {
            if (!getOut) {
                moves.push(dude.getLoc()); // push the dudes current loc
                checkBoardForBaddies(pTables);
                //IncentivizeUnknown(pTables);
                pTables[dude.getLoc().getRow()][dude.getLoc().getCol()] -= 8; // Don't want him back tracking
                    /*
                    If we have been through the possible moves before, subtrack and don't go there again
                     */
                ArrayList<Location> arr = dude.getValidLocsAroundMe(dude.getLoc());
                for(Location temp: arr) {
                    for(Location locs: moves) {
                        if(locs.equals(temp)) {
                            pTables[temp.getRow()][temp.getCol()] -= 10;
                        }
                    }
                }
                // What to do if we are on a given tile, such as warning/gold
                dude.setLoc(actAccordingly(dude.getLoc()));
            } else {
                checkBoardForBaddies(pTables);
                moves.push(dude.getLoc()); // push the dudes current loc
                if (x == 2) {
                    for (int row = 0; row < pTables.length; row++) {
                        for (int col = 0; col < pTables[row].length; col++) {
                            pTables[row][col] += getOutMode[row][col]; // how the FUCK does this work? this adds to the original pTables, not even the getOutMode
                        }
                    }
                    x = 3;
                }
                pTables[dude.getLoc().getRow()][dude.getLoc().getCol()] -= 100;


                if(wumpusWorld.getValue(dude.getLoc()) >= 10 && wumpusWorld.getValue(dude.getLoc()) < 14) { // indicators
                    dude.setLoc(actAccordingly(dude.getLoc()));
                } else {
                    if (wumpusWorld.isValid(dude.getLoc().getRow() + 1, dude.getLoc().getCol()) && pTables[dude.getLoc().getRow() + 1][dude.getLoc().getCol()] > -100) {
                        dude.setLoc(new Location(dude.getLoc().getRow() + 1, dude.getLoc().getCol()));
                    } else if (wumpusWorld.isValid(dude.getLoc().getRow(), dude.getLoc().getCol() - 1) && pTables[dude.getLoc().getRow()][dude.getLoc().getCol() - 1] > -100) {
                        dude.setLoc(new Location(dude.getLoc().getRow(), dude.getLoc().getCol() - 1));
                    } else {
                        dude.setLoc(actAccordingly(dude.getLoc()));
                    }
                }


            }
            wumpusWorld.setVisibile(dude.getLoc());
            score--;
            nextMove = System.currentTimeMillis() + delay;
        }
    }



    public void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void handleKeyPresses() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            dude.moveUp();
            score--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            dude.moveLeft();
            score--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            dude.moveDown();
            score--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) ) {
            dude.moveRight();
            score--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            wumpusWorld.setShowFog(!wumpusWorld.isShowFog());
        }
    }

    public void handleMouseClick() {
        int startY = 120;
        int startX = 650;

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouse_x = Gdx.input.getX();
            int mouse_y = Gdx.input.getY();

            wumpusWorld.convertCoordsToLoc(mouse_x, mouse_y);
            if(selected == null) {
                if(mouse_x >= startX && mouse_x <= startX + 50 && mouse_y >= startY && mouse_y <= startY + 50) {
                    selected = WumpusWorld.spiderTile;
                } else if(mouse_x >= startX && mouse_x <= startX + 50 && mouse_y + 55 >= startY && mouse_y <= startY + 110) {
                    selected = WumpusWorld.pitTile;
                } else if(mouse_x >= startX && mouse_x <= startX + 50 && mouse_y + 110 >= startY && mouse_y <= startY + 165) {
                    selected = WumpusWorld.wumpusTile;
                } else if(mouse_x >= startX && mouse_x <= startX + 50 && mouse_y + 165 >= startY && mouse_y <= startY + 220) {
                    selected = WumpusWorld.goldTile;
                } else if(mouse_x >= startX && mouse_x <= startX + 50 && mouse_y + 220 >= startY && mouse_y <= startY + 275) {
                    selected = WumpusWorld.groundTile;
                } else if(mouse_x >= startX && mouse_x <= startX + 50 && mouse_y + 275 >= startY && mouse_y <= startY + 330) {
                    aiRunning = !aiRunning;
                }
            }
            else {
                wumpusWorld.placeTexture(selected, mouse_x, mouse_y);
                selected = null;
            }
        }
    }


    public void drawToolbar() {



        spriteBatch.draw(WumpusWorld.spiderTile, 650, 600);
        spriteBatch.draw(WumpusWorld.pitTile, 650, 545);
        spriteBatch.draw(WumpusWorld.wumpusTile, 650, 490);
        spriteBatch.draw(WumpusWorld.goldTile, 650, 435);
        spriteBatch.draw(WumpusWorld.groundTile, 650, 380);
        spriteBatch.draw(trophy, 650, 325);
        if(aiRunning) {
            defaultFont.draw(spriteBatch, "Untoggle Ai", 720, 355);
        } else {
            defaultFont.draw(spriteBatch, "Toggle Ai", 720, 355);
        }
        if(!dude.isDead()) {
            defaultFont.draw(spriteBatch, "Score: " + score, 200, 100);
        } else {
            defaultFont.draw(spriteBatch, "Final Score: " + actualScore, 200, 100);
        }
        if(selected != null) {
            spriteBatch.draw(selected, Gdx.input.getX() - 30, 740 - Gdx.input.getY());

        }
    }

    //this method runs as fast as it can, repeatedly, constantly looped
    @Override
    public void render(float delta) {
        clearScreen();


        //UI
        handleMouseClick();
        handleKeyPresses();

        // overarching things that need to run before the AI to check and make sure he is not dead
        if(wumpusWorld.getValue(dude.getLoc()) >= 1 && wumpusWorld.getValue(dude.getLoc()) <= 3) {
            dude.setDead(true);
            aiRunning = false;
            actualScore = score;
            wumpusWorld.setShowFog(false);
        }
        if(wumpusWorld.getValue(dude.getLoc()) == 4) {
            if(!gottenGold) {
                score += 50;
                gottenGold = true;
            }
        }
        if(dude.getLoc().getRow() == 9 && dude.getLoc().getCol() == 0 && dude.isHasGold()) {
            aiRunning = false;
        }


        //AI
        if(aiRunning) {
            ai2();
        }



        //all drawing of shapes MUST be in between begin/end
        shapeRenderer.begin();

        shapeRenderer.end();

        //all drawing of graphics MUST be in between begin/end
        spriteBatch.begin();



        wumpusWorld.draw(spriteBatch);
        dude.draw(spriteBatch);
        drawToolbar();
        spriteBatch.end();
        // add delay is the alternate method instead of using isValid on dude
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
