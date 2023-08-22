package MinesweeperGame;
import javax.swing.JFrame;

/**
 *
 * @author Prachee Nanda
 * Date: August 23rd, 2020
 * Serves as main class to function with all components
 */
public class Minesweeper {
    
    public static void main(String[] args) {
        //Open opening window
        WelcomeWindow myGame = new WelcomeWindow();
        myGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myGame.setVisible(true);
    }
    
}
