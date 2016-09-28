/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermind;

import java.util.Random;

/**
 *
 * @author 1430626
 */
public class MMServerSession {

    private boolean playAgain = true;
    private boolean gameOver = false;
    private int[] colours = new int[]{0x00000001, 0x00000002, 0x00000003, 
        0x00000004, 0x00000005, 0x00000006, 0x00000007, 0x00000008};
    
    public MMServerSession(boolean playAgain, boolean gameOver, int[] colours) {
        this.playAgain = playAgain;
        this.gameOver = gameOver;
        this.colours = colours;
    }
    
    public void action(){

        while(playAgain){
            int[] answerSet = createAnswerSet(); //Generate answer set
            while(!gameOver){
                
            }
        }
        
    }
        
    
    private int[] createAnswerSet(){
        int[] randomSet = new int[4];
        Random random = new Random();
        for(int i = 0; i < randomSet.length; i++){
            int randomInt = random.nextInt(colours.length + 1);
            randomSet[i] = colours[randomInt];
        }
        
        return randomSet;
    }
    
    
    
    /*private void checkAnswers(int[] clientAnswers, int[] answerSet){
        for(int i = 0; i < clientAnswers.length; i++){
            
        }
        
    } Will do after clue generation if needed*/
    
}
