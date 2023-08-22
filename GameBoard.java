package MinesweeperGame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Prachee Nanda
 * Date: 25th August 2020
 * Purpose: Creating Game Board for user to interact and play with
 */

public class GameBoard extends JFrame implements ActionListener, Grid {
    //i serves as an index for file searching
    int intI = 0;
        
    //Color to be used for game
    Color clrLightBlue = new Color(178, 234, 255);
    Color clrLightGreen = new Color (182, 234, 170);
    
    //Declare buttons 
    JButton btnRestart = new JButton();
    JButton btnFlag = new JButton();
    
    //Declare a button array and creates a virtual board
    JButton[][] btnGrid;
    
    //Create various panels that serve as layouts
    //Gridarea to display buttons
    JPanel pnlGridArea = new JPanel();
    //Titlearea has restart button, flag button etc.
    JPanel pnlTitleArea = new JPanel();
    //Lays out both grid area and title area
    JPanel pnlScreen = new JPanel();
    
    //Label to show title, details and score
    JLabel lblTitle = new JLabel();
    JLabel lblDetails = new JLabel();
    JLabel lblScore = new JLabel();
    
    //Create a text area for results to appear
    JTextArea txtGameResult = new JTextArea();
    
    //Load images
    ImageIcon title = new ImageIcon ("Title.png");
    ImageIcon mine = new ImageIcon ("mine.png");
    ImageIcon flag = new ImageIcon ("flag.png");
    //This image does not exist, used to remove images from cells
    ImageIcon none = new ImageIcon ("none.png");
    //Array list to store coordinates of all tiles with mines
    ArrayList<Integer> intMinesCoords = new ArrayList<>();
    ArrayList<Integer> intAllScores = new ArrayList<>();
    
    //Stores individual data for cells, how to access an individual cell and change data
    Cell[][] cells;
    
        
    //Stores whether the game should continue running or not
    boolean isGameRunning = true;
    
    //Stores whether user wants to flag cells
    boolean isFlaggingOn = false;
    
    //Creates a global variable of the dimensions and number of mines
    int intGridRow, intGridColumn, intTotalMines;
    
    
    /**
     * 
     * @param intRow sets the number of rows in board
     * @param intColumn sets the number of columns in board
     * @param intScreenWidth sets the width of the screen
     * @param intScreenHeight sets the height of the screen
     * 
     * Creates the GUI for the board, scalable for different modes
     */
    public void createBoard(int intRow, int intColumn, int intScreenWidth, int intScreenHeight){
        //Using input data, creates virtual board and cells
        cells = new Cell[intRow][intColumn];
        btnGrid = new JButton[intRow][intColumn];
        
        //Set the local variable to global
        intGridRow = intRow;
        intGridColumn = intColumn;
        
        //Set size of screen and allow it to close
        setSize (intScreenWidth, intScreenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create a grid-like layout to set buttons
        GridLayout gridLayout = new GridLayout(intRow, intColumn);
        pnlGridArea.setLayout(gridLayout);
        
        //Create another grid-like layout to display reset buttons and game title properly
        GridLayout titleLayout = new GridLayout(6,1,10,5);
        pnlTitleArea.setLayout(titleLayout);
        
        //Create a grid-like layout to allow both panels
        GridLayout screenLayout = new GridLayout(1,2);
        pnlScreen.setLayout(screenLayout);
        
        
        for (int x = 0; x < intRow; x++){
            for (int y = 0; y < intColumn; y++){
                //Create button and set background color
                btnGrid[x][y] = new JButton();
                btnGrid[x][y].setBackground(clrLightGreen);
                
                //Set unique action listener
                btnGrid[x][y].setActionCommand("CELL"+x+y);
                btnGrid[x][y].addActionListener(this);
                
                //Create virtual button
                Cell cell = new Cell(x,y);
                cells[x][y] = cell;
                
                //Add to panel
                pnlGridArea.add(btnGrid[x][y]);
            }
        }
        
        pnlScreen.add(pnlGridArea);
        
        //Set title image
        lblTitle.setIcon(title);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        
        //Set information for details
        lblDetails.setText("Created by Prachee Nanda | All rights reserved | 2020");
        lblDetails.setHorizontalAlignment(JLabel.CENTER);
        lblDetails.setFont(new Font("Calibri", Font.PLAIN, 16));
        lblDetails.setForeground(Color.WHITE);
        
        //Set flag button
        btnFlag.setText("FLAG CELL");
        btnFlag.setFont(new Font("Calibri", Font.PLAIN, 16));
        btnFlag.setActionCommand("FLAG");
        btnFlag.addActionListener(this);
        btnFlag.setBackground(Color.WHITE);
        
        //Set restart button
        btnRestart.setText("RESTART");
        btnRestart.setFont(new Font("Calibri", Font.PLAIN, 16));
        btnRestart.setActionCommand("RESTART");
        btnRestart.addActionListener(this);
        btnRestart.setBackground(Color.WHITE);
        
        //Create area for results to appear
        txtGameResult.setFont(new Font("Calibri", Font.PLAIN, 16));
        
        
        //Set title area panel with widgets
        pnlTitleArea.add(lblTitle);
        pnlTitleArea.add(lblDetails);
        pnlTitleArea.add(btnFlag);
        pnlTitleArea.add(btnRestart);
        pnlTitleArea.add(txtGameResult);
        pnlTitleArea.setBackground(Color.BLACK);
        
        //Add it to panel
        pnlScreen.add(pnlTitleArea);
        //Make screen visible
        add (pnlScreen);
    }
    
    /**
     * 
     * @param intMine sets the number of mines, based on level
     * Randomly places mines around the board
     */ 
    public void setMines(int intMine){
        //this var changes to the numbers of mines that have to be created for the selected level
        int intMultiplier = 0;
        intTotalMines = intMine;
        
        //Sets a multiplier depending on the level of difficulty
        if (intMine == 10){
            intMultiplier = 10;
        }
        if (intMine == 20) {
            intMultiplier = 16;
        }
        if (intMine == 30) {
            intMultiplier = 16;
        }
        
        //While loop to place mines randomly across the board
        while (intMine != 0) {
            //Set two random numbers
            int intX = (int) (intMultiplier*Math.random());
            int intY = (int) (intMultiplier*Math.random());
            
            //Check if tile has a mine
            if (cells[intX][intY].isMine == false){
                //Add both coordinates into arraylist
                intMinesCoords.add(intX);
                intMinesCoords.add(intY);
                
                //Update value of cell
                cells[intX][intY].hasMine();
                //Decrease the number of mines to be set
                intMine -= 1;
            }
            
        }
        //Calculate the value of the tile
        calculateTileValue();
    }
    
    /**
     * Calculates the value of tiles near a mine
     */
    public void calculateTileValue (){
        //For all levels, this var stores the value of the end row
        int intLastRow = intGridRow - 1;
        int intLastColumn = intGridColumn - 1;
        //For loop to go through all cells with a mine
        for (int x =0; x < intMinesCoords.size(); x+=2){
            if (cells[intMinesCoords.get(x)][intMinesCoords.get(x + 1)].isMine == true){
                int intTileX = intMinesCoords.get(x);
                int intTileY = intMinesCoords.get(x + 1);
                //For cells that are not on edges
                if ((intTileX != 0) && (intTileX != intLastRow) && (intTileY!= 0) && (intTileY != intLastColumn)){
                    //Add 1 to value to hint at mines around it
                    cells[intTileX + 1][intTileY].addCellValue(1);
                    cells[intTileX - 1][intTileY].addCellValue(1);
                    cells[intTileX][intTileY + 1].addCellValue(1);
                    cells[intTileX][intTileY - 1].addCellValue(1);
                    cells[intTileX + 1][intTileY + 1].addCellValue(1);
                    cells[intTileX - 1][intTileY + 1].addCellValue(1);
                    cells[intTileX + 1][intTileY - 1].addCellValue(1);
                    cells[intTileX - 1][intTileY - 1].addCellValue(1);
                }

                //if at first row
                if (intTileX == 0){
                    //add 1 to value but don't look at cells behind
                    cells[intTileX + 1][intTileY].addCellValue(1);
                    //Considers cells that are not at left
                    if (intTileY != 0){
                        cells[intTileX][intTileY - 1].addCellValue(1);
                        cells[intTileX + 1][intTileY - 1].addCellValue(1);
                    }
                    //Considers cells that are not at right
                    if (intTileY != intLastColumn){
                        cells[intTileX][intTileY + 1].addCellValue(1);
                        cells[intTileX + 1][intTileY + 1].addCellValue(1);
                    }
                }
                
                //If at last row
                if (intTileX == intLastRow){
                    //add 1 to value but don't look at cells in front
                    cells[intTileX - 1][intTileY].addCellValue(1);
                    
                    //Considers cells that are not at left
                    if (intTileY != 0){
                        cells[intTileX][intTileY - 1].addCellValue(1);
                        cells[intTileX - 1][intTileY - 1].addCellValue(1);
                    }
                    //Considers cells that are not at right
                    if (intTileY != intLastColumn){
                        cells[intTileX][intTileY + 1].addCellValue(1);
                        cells[intTileX - 1][intTileY + 1].addCellValue(1);
                    }
                }
                //If at left
                if (intTileY == 0){
                    //add 1 to value but don't look at cells further left
                    cells[intTileX][intTileY + 1].addCellValue(1);
                    //Considers bottom cells
                    if (intTileX != 0){
                        cells[intTileX - 1][intTileY].addCellValue(1);
                        cells[intTileX - 1][intTileY + 1].addCellValue(1);
                    }
                    //Consider top cells
                    if (intTileX != intLastRow){
                        cells[intTileX + 1][intTileY].addCellValue(1);
                        cells[intTileX + 1][intTileY + 1].addCellValue(1);
                    }
                }
                //If at right
                if (intTileY == intLastColumn){
                    cells[intTileX][intTileY - 1].addCellValue(1);
                    //add 1 to value but don't look at cells further right
                    //Considers bottom cells
                    if (intTileX != 0){
                        cells[intTileX - 1][intTileY].addCellValue(1);
                        cells[intTileX - 1][intTileY - 1].addCellValue(1);
                    }
                    //Consider top cells
                    if (intTileX != intLastRow){
                        cells[intTileX + 1][intTileY].addCellValue(1);
                        cells[intTileX + 1][intTileY - 1].addCellValue(1);
                    }
                }

            }
        }
    }
    
    /**
     * 
     * @param e looks at what event occurs
     * Add functionality to buttons
     */
    public void actionPerformed (ActionEvent e){
        //Loop to generate unique commands for buttons and see if any of them are pressed
        for (int x = 0; x < intGridRow; x++){
            for (int y = 0; y < intGridColumn; y++){
                //If the button is pressed
                if(e.getActionCommand().equals("CELL" + x + y)){ 
                    //Ensure user wants to flag and game isnt over
                    if (isFlaggingOn == true && isGameRunning == true){
                        if (cells[x][y].isFlagged == false){
                            btnGrid[x][y].setIcon(flag);
                            cells[x][y].isFlagged = true;
                        } else {
                            btnGrid[x][y].setIcon(none);
                            cells[x][y].isFlagged = false;
                        }
                        
                    }
                    
                    //Check what should happen
                    gameStatus(x, y);
                    
                    
                }
            }
        }
        
        //Set flag button to stop game so flag can be placed
        if(e.getActionCommand().equals("FLAG")){
            flagCell();
        }
        //Set restart button to open welcome screen
        if(e.getActionCommand().equals("RESTART")){
            WelcomeWindow myGame = new WelcomeWindow();
            myGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myGame.setVisible(true);
            this.dispose();
        }
    }
    
    public void flagCell(){
        //Loop to set the icons of cells into mines
        if (isFlaggingOn == false){
            btnFlag.setBackground(clrLightBlue);
            isFlaggingOn = true;
            //Toggles the value as represented by color of button
        } else {
            isFlaggingOn = false;
            btnFlag.setBackground(Color.WHITE);
        }
    }
    
    /**
     * Controls game mechanics
     */
    
    public void gameStatus(int x, int y){
        //Set variable to count how many cells have been uncovered
        int intCellsUncovered = 0;
        
        //Linear searching algorithm to see whether all cells are uncovered
        for (int i = 0; i < intGridRow; i++){
            for (int j = 0; j < intGridColumn; j++){
                if ((cells[i][j].isUncovered == true && cells[i][j].isMine == false)){
                    intCellsUncovered += 1;
                }
            }
        }
        
        //Check if user has won game
        if (intCellsUncovered == ((intGridRow * intGridColumn) - intTotalMines)){
            txtGameResult.setText("You have won the game. Your score was " + intCellsUncovered + " and high score is: " + displayHighScore(intCellsUncovered));
            btnRestart.setBackground(clrLightBlue);
            isGameRunning = false;
                        
            }
        
        //Check if there is a mine
        if (cells[x][y].isMine == true && cells[x][y].isFlagged == false && isGameRunning == true && isFlaggingOn == false){
            //Hit mine
            btnGrid[x][y].setIcon(mine);
            btnGrid[x][y].setBackground(Color.RED);
            
            isGameRunning = false;
            //Set score and game over result
            txtGameResult.setText("Uh Oh! You blew it - Your score was: " + intCellsUncovered + " and high score is: " + displayHighScore(intCellsUncovered));
            btnRestart.setBackground(clrLightBlue);
        } else {
            //Uncover the tile
            uncoverTiles(x,y);
        }
        
    }
    
    /**
     * 
     * @param intTileX stores the intTileX-coordinates for given tile
     * @param intTileY stores the intTileY-coordinates for given tile
 
        Recursive function that uncovers surrounding tiles if they are not mines
     */
    public void uncoverTiles(int intTileX, int intTileY){
        //For all levels, this var stores the value of the end row
        int intLastRow = intGridRow - 1;
        int intLastColumn = intGridColumn - 1;
        
        //If cell hasn't been uncovered
        if (cells[intTileX][intTileY].isUncovered == false && isGameRunning == true && isFlaggingOn == false && cells[intTileX][intTileY].isFlagged == false){
            //Set it to uncovered
            cells[intTileX][intTileY].isUncovered = true;
            //Set a label with its value
            String strLabel = String.valueOf(cells[intTileX][intTileY].intValue);
            //Set the color to a different one, indicating it has been pressed
            btnGrid[intTileX][intTileY].setBackground(Color.LIGHT_GRAY);
            
            //If the cell is not a blank cell
            if (cells[intTileX][intTileY].intValue!= 0){
                //Set the label to its value
                btnGrid[intTileX][intTileY].setText(strLabel);
            }
            
            //if it is a blank cell
            if ("0".equals(strLabel)){
                //If it is not on the edge
                if ((intTileX > 0) && (intTileX < intLastRow) && (intTileY > 0) && (intTileY < intLastColumn)){
                    //Uncover the surrounding cells and user recursion to uncover tiles around the cell uncovered
                    //As long as they are not mines or not uncovered
                    if (cells[intTileX + 1][intTileY].isMine == false && cells[intTileX + 1][intTileY].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY);
                    }
                    if (cells[intTileX - 1][intTileY].isMine == false && cells[intTileX - 1][intTileY].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY);
                    }
                    if (cells[intTileX][intTileY+1].isMine == false && cells[intTileX][intTileY+1].isUncovered == false){
                        uncoverTiles(intTileX,intTileY+1);
                    }
                    if (cells[intTileX][intTileY - 1].isMine == false && cells[intTileX][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX,intTileY - 1);
                    } 
                    if (cells[intTileX - 1][intTileY - 1].isMine == false && cells[intTileX - 1][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY - 1);
                    }
                    if (cells[intTileX + 1][intTileY - 1].isMine == false && cells[intTileX + 1][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY - 1);
                    }
                    if (cells[intTileX + 1][intTileY + 1].isMine == false && cells[intTileX + 1][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY + 1);
                    }
                    if (cells[intTileX - 1][intTileY + 1].isMine == false && cells[intTileX - 1][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY + 1);
                    }
                }
                
                //If it is at the beginning row
                if (intTileX == 0){
                    //Uncover cells around it
                    if (cells[intTileX + 1][intTileY].isMine == false && cells[intTileX + 1][intTileY].isUncovered == false){
                    uncoverTiles(intTileX + 1,intTileY);
                    }
                    if (intTileY == 0){
                        if (cells[intTileX][intTileY + 1].isMine == false && cells[intTileX][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX,intTileY + 1);
                        }
                        if (cells[intTileX+1][intTileY + 1].isMine == false && cells[intTileX+1][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX+1,intTileY + 1);
                        } 
                    }
                    if (intTileY == intLastColumn){
                        if (cells[intTileX][intTileY - 1].isMine == false && cells[intTileX][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX,intTileY - 1);
                        } 
                        if (cells[intTileX + 1][intTileY - 1].isMine == false && cells[intTileX + 1][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY - 1);
                        } 
                    }
                }
                
                //If it is at the end row
                if (intTileX == intLastRow){
                    //Uncover the cells around it
                    if (cells[intTileX - 1][intTileY].isMine == false && cells[intTileX - 1][intTileY].isUncovered == false){
                    uncoverTiles(intTileX - 1,intTileY);
                    }
                    if (intTileY == 0){
                        if (cells[intTileX][intTileY + 1].isMine == false && cells[intTileX][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX,intTileY + 1);
                        } 
                        if (cells[intTileX - 1][intTileY + 1].isMine == false && cells[intTileX - 1][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY + 1);
                        } 
                    }
                    if (intTileY == intLastColumn){
                        if (cells[intTileX][intTileY - 1].isMine == false && cells[intTileX][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX,intTileY - 1);
                        } 
                        if (cells[intTileX - 1][intTileY - 1].isMine == false && cells[intTileX - 1][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY - 1);
                        } 
                    }
                }
                
                //If it is at the beginning column
                if (intTileY == 0){
                    //Uncover cells around it
                    if (cells[intTileX][intTileY + 1].isMine == false && cells[intTileX][intTileY + 1].isUncovered == false){
                    uncoverTiles(intTileX,intTileY + 1);
                    }
                    if (intTileX == 0){
                        if (cells[intTileX + 1][intTileY].isMine == false && cells[intTileX + 1][intTileY].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY);
                        }
                        if (cells[intTileX + 1][intTileY + 1].isMine == false && cells[intTileX + 1][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY + 1);
                        } 
                    }
                    if (intTileX == intLastRow){
                        if (cells[intTileX - 1][intTileY].isMine == false && cells[intTileX - 1][intTileY].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY);
                        } 
                        if (cells[intTileX - 1][intTileY + 1].isMine == false && cells[intTileX - 1][intTileY + 1].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY + 1);
                        } 
                    }
                }
                
                //If it is at the ending column
                if (intTileY == intLastColumn){
                    //Uncover cells around it
                    if (cells[intTileX][intTileY - 1].isMine == false && cells[intTileX][intTileY - 1].isUncovered == false){
                    uncoverTiles(intTileX,intTileY - 1);
                    }
                    if (intTileX == 0){
                        if (cells[intTileX + 1][intTileY].isMine == false && cells[intTileX + 1][intTileY ].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY);
                        }
                        if (cells[intTileX + 1][intTileY - 1].isMine == false && cells[intTileX + 1][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX + 1,intTileY - 1);
                        } 
                    }
                    if (intTileX == intLastRow){
                        if (cells[intTileX - 1][intTileY].isMine == false && cells[intTileX - 1][intTileY].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY);
                        } 
                        if (cells[intTileX - 1][intTileY - 1].isMine == false && cells[intTileX - 1][intTileY - 1].isUncovered == false){
                        uncoverTiles(intTileX - 1,intTileY - 1);
                        } 
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @param intScore is the current score
     * @return highest score of all time
     */
    public int displayHighScore(int intScore){
        //Try, catch block to read and write into this file
        try{
            //Variables to help read and write in file
            FileReader fr = new FileReader("Score.txt");
            BufferedReader br = new BufferedReader(fr);
            //True statement added to ensure it doesn't rewrite values
            FileWriter fw = new FileWriter("Score.txt", true);
            PrintWriter pr = new PrintWriter(fw);
            
            //Write current score into file
            pr.write(Integer.toString(intScore) + "\n");
            pr.close();
            
            //Set variable to read lines
            String strLine = br.readLine();
            
            //Read a file and get data to store in an arraylist
            while(strLine != null){
                intAllScores.add(Integer.valueOf(strLine));
                strLine = br.readLine();
            }
            
        } catch (IOException e){}
        
        //to find highest score, the array list will be sorted 
        //Sorting algorithm altered to fit with arraylists
        int intITemp;
        int intJTemp;
        int intObjects = intAllScores.size();
        
        //For all elements and next
        for (int i = 0; i < intObjects - 1; i++){
            for (int j = i + 1; j < intObjects; j++){
                //Check which is greater
                if (intAllScores.get(i) > intAllScores.get(j)){
                    //If yes, swap them
                    intITemp = intAllScores.get(i);
                    intJTemp = intAllScores.get(j);
                    
                    intAllScores.set(i,intJTemp);
                    intAllScores.set(j, intITemp);
                }
            }
        }
        //High score is the final element in list
        int intHighScore = intAllScores.get(intObjects - 1);
        //Return it
        return intHighScore;
    }
}
