/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermind;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author 1432581
 */
public class MMServer {

    private int portNumber = 50000;
    private ServerSocket socket;
    
    public MMServer() {
        
    }
    
    public void createServerSocket(ServerSocket socket) throws IOException {
        for(;;){
            Socket client_socket = socket.accept();
            MMPacket mmp = new MMPacket(client_socket);
            //send an OK message to draw board 
            
            
            
        }
        
    }
    
}
