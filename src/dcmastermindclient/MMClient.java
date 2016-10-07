/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermindclient;

import dcmastermind.MMPacket;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 1410926
 */
public class MMClient {
    //ask user to input IP address
    private String IPAddress;
    private int port = 50000;
   private  MMPacket mmPacket;
   private byte[] clues ;
   private byte[] serverAnswers;
    //ask user to start game or quit
    
    public MMClient() throws IOException{
        createSocket();
        if (playAgain() == true)
        {   
            startGame(); //sends server the start msg
            //check server msg
            if(mmPacket.readPacket()[0] == 0x0000000A)
            {
                displayGameBoard();
                playGame();
            }
            
        }
       // else 
            //end game
        
    
    }
    private void createSocket() throws IOException{
        // Create socket that is connected to server on specified port
    Socket socket = new Socket(IPAddress, port);
    System.out.println("Connected to server...sending echo string");
    
    mmPacket = new MMPacket(socket);
    
    }
    public boolean playAgain(){
        String userResponse = JOptionPane.showInputDialog("Enter P for play and Q for quit:");
        if(userResponse == "P")
            return true;
        else 
            return false;
    }
    
    private void displayGameBoard(){
    //diplay board
    }
    private void startGame() throws IOException{
        //send meesage to server
        byte[] startMessage = new byte[]{0x00000010};
        mmPacket.writePacket(startMessage);
    }
    //it is for one game
    public void playGame() throws IOException{
        byte[] userAnswers = fillInUserAnswerArray();
        if(userAnswers.length == 4)
        {
            for(int i=0; i < 10;i++)
            {
               mmPacket.writePacket(userAnswers); //sends the user ans to server
               clues = mmPacket.readPacket();
               userAnswers = fillInUserAnswerArray();
            }
            
            serverAnswers = mmPacket.readPacket();
            //display a msg 
            
        }
        
    }
    public byte[] fillInUserAnswerArray(){
        byte[] userAnswers = new byte[4];
        //gets the colors to insert here
        return userAnswers;
    }
}
