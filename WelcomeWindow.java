package MinesweeperGame;
import java.applet.Applet;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;

/**
 *
 * @author Prachee Nanda
 * Date: 24th August 2020
 * Serves as opening screen for player
 */


public class WelcomeWindow extends GameBoard implements ActionListener{
    //Label to show title, details and score
    JLabel lblBackground = new JLabel();
    JLabel lblTitle = new JLabel();
    JLabel lblDetails = new JLabel();
    
    //Declare buttons 
    JButton btnEasy = new JButton();
    JButton btnNormal = new JButton();
    JButton btnAdvanced = new JButton();
    
    //Declare images
    ImageIcon background = new ImageIcon("Background.png");
    ImageIcon title = new ImageIcon ("Title.png");    
    
    public WelcomeWindow(){
        //Set screen
        resize(500,800);
        getContentPane().setBackground(Color.GRAY);
        setLayout(null);
        
        //Set label with developer details
        lblDetails.setLocation(15,80);
        lblDetails.setSize(500,50);
        lblDetails.setText("Created by Prachee Nanda | All rights reserved | 2020");
        lblDetails.setFont(new Font("Calibri", Font.PLAIN, 20));
        lblDetails.setForeground(Color.WHITE);
        add(lblDetails);
        
        //Set label with the title
        lblTitle.setLocation(10,0);
        lblTitle.setSize(500,100);
        lblTitle.setIcon(title);
        add(lblTitle);
        
        //Set button for easy mode 
        btnEasy.setLocation(100,200);
        btnEasy.setSize(270,100);
        btnEasy.setText("EASY");
        btnEasy.setBackground(Color.PINK);
        btnEasy.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnEasy.setActionCommand("EASY");
        btnEasy.addActionListener(this);
        add(btnEasy);
        
        //Set button for normal mode
        btnNormal.setLocation(100,325);
        btnNormal.setSize(270,100);
        btnNormal.setText("NORMAL");
        btnNormal.setBackground(Color.PINK);
        btnNormal.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnNormal.setActionCommand("NORMAL");
        btnNormal.addActionListener(this);
        add(btnNormal);
        
        //Set button for advanced mode
        btnAdvanced.setLocation(100,450);
        btnAdvanced.setSize(270,100);
        btnAdvanced.setText("ADVANCED");
        btnAdvanced.setBackground(Color.PINK);
        btnAdvanced.setFont(new Font("Calibri", Font.PLAIN, 20));
        btnAdvanced.setActionCommand("ADVANCED");
        btnAdvanced.addActionListener(this);
        add(btnAdvanced);
        
        //Set label for background
        lblBackground.setLocation(0,0);
        lblBackground.setSize(500,800);
        lblBackground.setIcon(background);
        add(lblBackground);
    }
    
    public void actionPerformed (ActionEvent e){
        //If user chooses easy mode
        if(e.getActionCommand().equals("EASY")){
          //Create gridboard
          GameBoard gridBoard = new GameBoard();
          gridBoard.createBoard(10,10,950,500);
          gridBoard.setMines(10);
        
          gridBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          gridBoard.setVisible(true);
          this.dispose();
        }
        
        //If user chooses normal mode
        if(e.getActionCommand().equals("NORMAL")){
          GameBoard gridBoard = new GameBoard();
          //Create gridboard
          gridBoard.createBoard(16,16,1500,750);
          gridBoard.setMines(20);
        
          gridBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          gridBoard.setVisible(true);
          this.dispose();
        }
        
        //If user chooses advanced mode
        if(e.getActionCommand().equals("ADVANCED")){
          GameBoard gridBoard = new GameBoard();
          //Create gridboard
          gridBoard.createBoard(16,18,1500,750);
          gridBoard.setMines(30);
        
          gridBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          gridBoard.setVisible(true);
          this.dispose();
          
        }
         
    }
}
