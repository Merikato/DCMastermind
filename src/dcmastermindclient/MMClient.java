/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermindclient;

import dcmastermind.MMPacket;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author 1410926
 */
public class MMClient {
    //ask user to input IP address
    private String IPAddress="10.172.20.229";
    private int port = 50000;
   private  MMPacket mmPacket;
   private byte[] clues ;
   private byte[] serverAnswers;
   private Socket socket;
    //ask user to start game or quit
    public static void main(String[] args) throws IOException
    {
        MMClient c = new MMClient();
    }
    public MMClient() throws IOException{
        createSocket();
        boolean playAgain=playAgainGame();
        
        while (playAgain == true)   //while loop to keep playing until user enters quit
        {   
            startGame(); //sends server the start msg
            //check server msg
            
            if(mmPacket.readPacket()[0] == 0x0000000A)
            {
                System.out.println("ready to draw board");
                displayGameBoard();
                playGame();
            }
            
        playAgain = playAgainGame();
        }
       // else 
            //end game -- close the socket
        
             socket.close();
    
    }
    private void createSocket() throws IOException{
        // Create socket that is connected to server on specified port
    socket = new Socket(IPAddress, port);
    System.out.println("Connected to server...sending echo string");
    
    mmPacket = new MMPacket(socket);
    System.out.println("CREATED PACKET");
    
    
    }
    public boolean playAgainGame(){
        //String userResponse = JOptionPane.showInputDialog("Enter P for play and Q for quit:");
        //if(userResponse == "P")
            return true;
        //else 
            //return false;
    }
    
    private void displayGameBoard(){
    //diplay board
    }
    private void startGame() throws IOException{
        System.out.println("send message to server");
        byte[] startMessage = new byte[]{0x00000011, 0,0,0};
        System.out.println("sending packet: " + Arrays.toString(startMessage));
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
               System.out.println("sent guess " + Arrays.toString(userAnswers));
               clues = mmPacket.readPacket();
               
               
               //display clues on board
               //if user won, display a msg, and call play again
               // else continuein the loop and get the user array
               userAnswers = fillInUserAnswerArray();
            }
            
            serverAnswers = mmPacket.readPacket();
            //display a msg 
            
        }
        
    }
    public byte[] fillInUserAnswerArray(){
        //byte[] userAnswers = new byte[4];
        //gets the colors to insert here, get it from GUI
        byte[] userAnswers = new byte[]{0x00000001,0x00000002,0x00000003,0x000000005};
        return userAnswers;
    }
}
