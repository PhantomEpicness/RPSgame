/* @author Donald Wyand
 * @date 10/8/23
 * ICS 211 RPS project
 * Copyright (C) 2023 Donald Wyand
 * Licensed under GPLv3 (included)
*/

/* ****************************************************************
 *                          Milestones
 * ****************************************************************
 * - GUI baseline functionality[]
 * - Buttons[]
 * - Documentation[]
 * - Game mechanics[]
 * - GUI game frame[]
 * - FileWrite.java[]
 * - Functionality testing[]
 * - Ensure MVP[]
 * - GUI design[]
 */

 /* * Design notes
  *  - Use as many images as possible
  *  Options will be displayed in a roundel if possible
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/* RPS.java
 * Contains mainly GUI specific functionality and passes inputs to their respective class files.
 */

public class RPS {
    // Static variables
    public static String resultText = "null";
    static int playerPoints = 0;
    static int computerPoints = 0;
    static int draws = 0;
    static String roundResult = "null";
    static int round = 0;
    static String topString = "CHOOSE YOUR WEAPON!";
    static String[] choices = {"rock","paper","Scissors"};
    // Defining static labels

    static JLabel computerScore = new JLabel("Computer Score:" + computerPoints + " points");
    static JLabel playerScore = new JLabel("Player Score:" + playerPoints + " points");
    static JLabel roundTicker = new JLabel("Round " + round);
    static JLabel ties = new JLabel("Ties:" + draws);
    static JLabel playerChose = new JLabel("");
    // String that appears at the top of the gamespace denoting AI moves and who won the round 
    static JLabel topMessage = new JLabel("<html><h1>" + topString + "</h1></html>", SwingConstants.CENTER);
    static int playerChoice = 0;

    // Frame where scores are displayed
    static JFrame scoreFrame = new JFrame("Rock Paper Scissors score sheet ");
    public static void main(String[] args) throws IOException{
        // Control variables
        int fontSize = 32;
        // Create/append to file denoting new session
        FileWrite.createOrAppendFile("\n[BEGIN RPS GAME SESSION]","scores.txt", true);
        /*
        * Frame generation
        */

        JFrame frame = new JFrame("rock paper scissers v1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(725,500);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        // Panels
        
        /*
        * playPanel, outer play area where you can put stuff in
        */
        JPanel playPanel = new JPanel(new GridLayout(5,5,10,5));
        JPanel outerPlayArea = new JPanel(new BorderLayout());
        /*
        * Asset creation
        */

        // images
        ImageIcon topImg = new ImageIcon("titlev2.png");
        ImageIcon bottomImg = new ImageIcon("footerboxy.png");
        ImageIcon sideImg = new ImageIcon("boxy2.png");

        // Labels & their variables

        JLabel topBanner = new JLabel(topImg);
        JLabel sideBanner = new JLabel(sideImg);
        JLabel sideBanner2 = new JLabel(sideImg);
        JLabel bottomBanner = new JLabel(bottomImg);  
        
        playerScore.setFont(new Font("SansSerif", Font.PLAIN, fontSize-10));
        
        computerScore.setFont(new Font("SansSerif", Font.PLAIN, fontSize-10));
        roundTicker.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        
        ties.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        //buttons

        JButton rock = new JButton ("Rock ✊");
        rock.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        JButton paper = new JButton ("Paper📄");
        paper.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        JButton scissers = new JButton ("Scissors✂️");
        scissers.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        JButton displayScore = new JButton ("Scores");
        
        /*
        * Layout management, from "inside" to "outside"
        */

        //playpanel
        playPanel.add(computerScore);
        playPanel.add(rock);
        playPanel.add(playerScore);
        playPanel.add(paper);
        playPanel.add(roundTicker);
        playPanel.add(scissers);
        playPanel.add(ties);
        playPanel.add(playerChose);
        // outerPlayArea
        outerPlayArea.add(playPanel,BorderLayout.CENTER);
        outerPlayArea.add(topMessage,BorderLayout.NORTH);
        outerPlayArea.add(displayScore,BorderLayout.SOUTH);

        // frame
        frame.add(topBanner,BorderLayout.NORTH);
        frame.add(outerPlayArea,BorderLayout.CENTER);
        frame.add(bottomBanner,BorderLayout.SOUTH);
        frame.add(sideBanner,BorderLayout.WEST);
        frame.add(sideBanner2,BorderLayout.EAST);
        frame.setVisible(true);
        

        /*
        * Event handlers - Choices
        * Each of these run the roundFinish function, passing the result of the gameLogic function, which is also passed the corresponding choice.
        * 0 = rock , 1 = paper , 2 = scissors
        */

        rock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.out.println("* player clicks rock!");
                playerChoice = 0;
                try{
                    roundFinish(gameLogic.play(0)); // Try/catch because this has to handle the exception too.
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
                frame.validate();
                playPanel.validate();
            }
        });
        paper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("* player clicks paper!");
                playerChoice = 1;
                try{
                    roundFinish(gameLogic.play(1));
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
                frame.validate();
                playPanel.validate();
            }
        });

        scissers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("* player clicks scissors!");
                playerChoice = 2;
                try{
                    roundFinish(gameLogic.play(2));
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
                frame.validate();
                playPanel.validate();
                roundTicker.setText("Round: " + round);
            }
        });
        /** Button to display the scores
         * Creates a new window which will display the scores. The scores are read line by line from scores.txt and are
         * then encapsulated in HTML for display on a JLabel object
         */
        displayScore.addActionListener(new ActionListener() {
            String scores = "";
            public void actionPerformed(ActionEvent e) {
                scoreFrame.setSize(200,200);
                scoreFrame.setLayout(new BorderLayout());
                scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // We don't want to close everything else too
                scoreFrame.setVisible(true);
                scoreFrame.setBackground(Color.orange);
                JLabel scoreTitle = new JLabel("All scores are saved to the scores.txt file.");
                scoreTitle.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
                JLabel scoreList = new JLabel("SCORES");
                scoreFrame.add(scoreTitle,BorderLayout.NORTH);
                scoreFrame.add(scoreList,BorderLayout.CENTER);
                File f = new File("scores.txt");
                Scanner infile = null; // init the scanner
                try {
                    infile = new Scanner(f);
                }
                catch (FileNotFoundException exe) { // If for some reason scores.txt doesn't exist
                    exe.printStackTrace();
                    System.exit(1);
                }
                while (infile.hasNextLine()) {
                    String line = infile.nextLine().trim();
                    scores += "<br>" + line + "</br>"; // Put in BR tags to break it up because HTML doesn't like newlines
                }
                scoreList.setText("<html>" + scores + "</html>"); //display with html
                scoreFrame.pack(); // create a nice list of sorts
                scoreFrame.validate();
                scores = ""; // Empty this out so it won't stack
            }
        });
    }
    /** roundFinsh()
    * Triggered by game logic to display victor, increment round number and changes scores accordingly
    * @param roundResult The int result of the round, 0 for tie, 1 for player win, 2 for player loss
    */
    static void roundFinish(int roundResult){
        switch(roundResult){
            case 0:
                draws ++;
                ties.setText("Ties: " + draws);
                break;
            case 1:
                playerPoints ++;
                break;
            case 2:
                computerPoints ++;
                break;
        }
        // Adding HTML tags and set top text to result text
        playerChose.setText("You chose " + choices[playerChoice]);
        topMessage.setText("<html><h1>" + resultText + "</h1></html>");
        computerScore.setText("Computer Score:" + computerPoints + " points");
        playerScore.setText("Player Score:" + playerPoints + " points");
        round++;
        roundTicker.setText("Round " + round);
        return;
    }
}
