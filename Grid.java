package MinesweeperGame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Prachee Nanda
 * Date: 25th August 2020
 * Purpose: Serves as an interface to create grid
 */

public interface Grid{
    /**
     * 
     * @param intRow sets the number of rows in board
     * @param intColumn sets the number of columns in board
     * @param intScreenWidth sets the width of the screen
     * @param intScreenHeight sets the height of the screen
     * 
     * Creates the GUI for the board, scalable for different modes
     */
    public void createBoard(int intRow, int intColumn, int intScreenWidth, int intScreenHeight);
    
    /**
     * 
     * @param intMines sets the number of mines, based on level
     * Randomly places mines around the board
     */
    public void setMines(int intMines);
    
    /**
     * Calculates the value of tiles near a mine
     */
    public void calculateTileValue();
    
    /**
     * 
     * @param intTileX stores the x-coordinates for given tile
     * @param intTileY stores the y-coordinates for given tile
     * 
     * Recursive function that uncovers surrounding tiles if they are not mines
     */
    public void uncoverTiles(int intTileX, int intTileY);
    
    /**
     * Toggles whether cell should be flagged or not
     */
    public void flagCell();
    
    /**
     * 
     * @param intTileX is the x coordinate of the tile
     * @param intTileY is the y coordinate of the tile
     * This observes whether the game is over or not
     */
    public void gameStatus(int intTileX, int intTileY);
    
    /**
     * 
     * @param e looks at what event occurs
     * Add functionality to buttons
     */
    public void actionPerformed (ActionEvent e);
    
   
    /**
     * 
     * @param intScore displays the current score 
     * @return the high score by sorting the previous results and returning last number
     */
    public int displayHighScore(int intScore);   
}
