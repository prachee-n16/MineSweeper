package MinesweeperGame;
import java.applet.Applet;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Cell {
    //Stores if individual cell has a mine, has been pressed, or flagged
    boolean isMine = false;
    boolean isUncovered = false;
    boolean isFlagged = false;
    
    //Stores the number of surrounding mines
    int intValue;
    
    public Cell (int intX, int intY){
        
    }
    
    /**
     * toggles if there is a mine or not
     */
    public void hasMine(){
        isMine = true;
    }
    
    //Adds value to cell
    public void addCellValue(int cellVal){
        if (isMine != true){
            intValue += cellVal;
        }
    }
    
    
}