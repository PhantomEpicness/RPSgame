/* gameLogic.java
 * Contains the actual game logic to RPS in deciding who wins by playing the player's choice against a random number (0-2)
 * Returns results to RPS frontend and then writes it to a file.
 * @author Donald Wyand
 * @date 10/15/2023
 */

import java.io.IOException;

public class gameLogic {
    // output to file
    static String outText = "UNDEFINED";
    // What the computer plays, retrieved by RPS to display the computer's hand
    public int computerPlays;
    static String[] choices = {"Rock","Paper","Scissors"};
    
    /** play()
     * 
     * Accepts player choice in a number (0 = rock , 1 = paper , 2 = scissors)
     * Then compares it with a random number for the computer to see who won. Returns TRUE if player wins\
     * @param choice (0 = rock , 1 = paper , 2 = scissors)
     */
    public static int play(int choice) throws IOException{
        String aiDecide = choices[(int)(Math.random()*3)];
        boolean playerWin = true;
        // 0 = tie, 2 = player win , 1 = computer win
        int victor = 0;
        // Debug output
        System.out.println("Player plays " + choices[choice]);
        System.out.println("Computer plays " + aiDecide);
        
        // Now the actual game logic here
        
        if(choices[choice] == aiDecide){ // if tie then return immediately
            outText = "Draw! Both chose " + aiDecide;
            RPS.resultText = outText;
            System.out.println("\n [TIE]"+ outText);
	    FileWrite.createOrAppendFile("\n[DRAW] "+ outText,"scores.txt", true);
            return 0;
        }
        // Compare player choice to computer choice
        if((choice == 0) && (aiDecide == "Scissors")){
            playerWin = true;
        }
        else if((choice == 1) && (aiDecide == "Rock")){
            playerWin = true;
        }
        else if((choice == 2) && (aiDecide == "Paper")){
            playerWin = true;
        }
        else {
            playerWin = false;
        }

        // These pass the win text to the debug output, file and RPS
        if(playerWin) {
            outText = choices[choice] + " beats " + aiDecide + " PLAYER WINS!";
            RPS.resultText = outText;
            // add tags for easy reading
            outText = "[WIN] " + outText;
            victor = 1;
        }
        else{
            outText = aiDecide + " beats " + choices[choice] + " AI WINS!";
            RPS.resultText = outText;
            // add tags for easy reading
            outText = "[LOSE] " + outText;
            victor = 2;
        }
        System.out.println("\n"+ outText);
        FileWrite.createOrAppendFile("\n"+ outText,"scores.txt", true);
        return victor; // Ties should be handled before this so we only expect 1 or 2
    }
}
