/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 1430626
 */
public class MMServerSession {

    private boolean playAgain = true;
    private boolean gameOver = false;
    private int[] colours;
    MMPacket mmPacket;
    
    public MMServerSession(MMPacket mmp) {
        this.playAgain = playAgain;
        this.gameOver = gameOver;
        colours = new int[]{0,1,2,3,4,5,6,7};
        mmPacket = mmp;
    }
    
    private boolean setPlayAgainValue() throws IOException{
       System.out.println("server reading");
       byte[] packet= mmPacket.readPacket();
       System.out.println("Packet: \n" + Arrays.toString(packet));
       mmPacket.writePacket(new byte[]{0x0000000A,0,0,0});
       byte allPacket;      
       boolean play = false;
       
       for(int i=0;i<packet.length;i++)
       {
           allPacket = packet[i];
           if(allPacket == 0x00000011){
                play = true;
                return play;
           }
           else {
                play = false;
            //allPacket += packet[i]; //??
           }
       }     
       return play;
    }
    public void action() throws IOException{
        int counter=0;
        setPlayAgainValue();
        while(playAgain && !mmPacket.getSocket().isClosed()){
            int[] answerSet = createAnswerSet(); //Generate answer set
            while(!gameOver & !mmPacket.getSocket().isClosed()){
                
                // read packet from user.
                byte[] colorMessage = mmPacket.readPacket();
                System.out.println("received packet: "+ Arrays.toString(colorMessage));
                //check if msg is color     

                int colorRange = setColour(colorMessage[0]);
                if(colorRange != -1)
                {                                System.out.println("getting here");

                    //get user answer
                    int[] clientGuesses=new int[4];
                    for(int i=0;i<4;i++)
                    {
                        clientGuesses[i]=setColour(colorMessage[i]);
                    }
                    //compare the answers
                    //reply with clues
                   int[] clues= clueGenerator(clientGuesses,answerSet);
                  
                   System.out.println("user guesses: "+Arrays.toString(clientGuesses));
                   System.out.println("answer set: "+Arrays.toString(answerSet));
                   //send it to the client
                   //byte[] replyClientClues=colourBytes(clues);
                   byte[] replyClientClues=convertIntCluesArrayToBytes(clues);
                   mmPacket.writePacket(replyClientClues);
                   System.out.println("clues: "+Arrays.toString(clues));
                   System.out.println("clues in bytes: " + Arrays.toString(replyClientClues));
                   
                   counter++;
                   
                    //if 10th submission then 0xFFFFFFFF
                   if(counter == 10)
                       mmPacket.writePacket(new byte[]{0xFFFFFFFF});

                   
                }
                
            }
        }
        
    }
    private byte[] convertIntCluesArrayToBytes(int[] clues){
        byte[] byteClues = new byte[clues.length];
        for (int i=0;i < clues.length;i++)
        {
           byteClues[i]= convertIntCluesToBytes(clues[i]);
        }
        return byteClues;
    }
     private byte convertIntCluesToBytes(int clue){
         switch(clue){
            case 0: 
                return 0x00000000; // inplace   --change
            case 1: 
                return 0x00000001; // outplace -- change
            
            default: 
                return -1;
        }
     }

  private int[] clueGenerator(int[] clientGuesses,int[] answerSet){
        
    List<Integer> clueList = new ArrayList<Integer>();
    int[] cloneAnswerSet = new int[4];
    
    for(int i=0;i <4;i++)
    {
        cloneAnswerSet[i]=clientGuesses[i];
    }
        //check for in-place clues
        for(int i=0;i < 4;i++)
        {
            if(answerSet[i] == cloneAnswerSet[i])
            {
                cloneAnswerSet[i]=9; //so it will not be matched twice
                clueList.add(1);
            }
        }
        for(int guess=0;guess < 4;guess++)
        {
            for(int ans=0;ans<4;ans++)
            {    
                    if(answerSet[guess] == cloneAnswerSet[ans])
                    {
                        cloneAnswerSet[ans]=9;
                        clueList.add(0);
                    }
                }
            }
        int[] clues=new int[clueList.size()];
        for(int i=0;i<clueList.size();i++)
        {
            clues[i]=clueList.get(i);
        }
        return clues;
        }
  
    private int[] createAnswerSet(){
        int[] randomSet = new int[4];
        Random random = new Random();
        for(int i = 0; i < randomSet.length; i++){
            int randomInt = random.nextInt(colours.length - 0);
            
            randomSet[i] = colours[randomInt];
        }
        //call convertIntToByte()
        //byte[] randomByteSet=colourBytes(randomSet);
        return randomSet;
    }
    
    private byte[] colourBytes(int[] array){
        byte[] colours = new byte[array.length];
        for(int i = 0; i < colours.length; i++){
           colours[i]= convertIntToByte(i);
        }
        return colours;
    }
    
    private byte convertIntToByte(int i){
        switch(i){
            case 0: 
                return 0x00000001; // red
            case 1: 
                return 0x00000002; // yellow
            case 2: 
                return 0x00000003; // green
            case 3:
                return 0x00000004; // blue
            case 4:
                return 0x00000005; //purple
            case 5:
                return 0x00000006; // pink
            case 6:
                return 0x00000007; // white
            case 7:
                return 0x00000008; // brown
            default: 
                return -1;
        }
    }
    
    
    private int setColour(byte value){
      //  colours = new int[]{0x01, 0x00000002, 0x00000003, 
      //  0x00000004, 0x00000005, 0x00000006, 0x00000007, 0x00000008};
       // for(int i = )
        switch(value){
            case 0x00000001: 
                return 0; // red
            case 0x00000002: 
                return 1; // yellow
            case 0x00000003: 
                return 2; // green
            case 0x00000004:
                return 3; // blue
            case 0x00000005:
                return 4; //purple
            case 0x00000006:
                return 5; // pink
            case 0x00000007:
                return 6; // white
            case 0x00000008:
                return 7; // brown
            default: 
                return -1;
        }
    }
    
    
    /*private void checkAnswers(int[] clientAnswers, int[] answerSet){
        for(int i = 0; i < clientAnswers.length; i++){
            
        }
        
    } Will do after clue generation if needed*/
    
}
