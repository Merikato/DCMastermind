/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermind;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author 1432581
 */
public class MMPacket {
    private final int BUFSIZE = 4;
    private Socket clientSocket;
    public MMPacket(Socket socket){
        this.clientSocket = socket;
    }

    public Socket getSocket() {
        return clientSocket;
    }

    public void setSocket(Socket socket) {
        this.clientSocket = socket;
    }
    
    public byte[] readPackets()throws IOException{
        int msg_size;
        byte[] byteBuffer = new byte[BUFSIZE];
        InputStream in = this.clientSocket.getInputStream();
        OutputStream out = this.clientSocket.getOutputStream();
        while((msg_size = in.read(byteBuffer)) != 1){
            //out.write(byteBuffer, 0, BUFSIZE);
        }
        return byteBuffer;
    }
    public OutputStream getOutputStream()throws IOException{
        return this.clientSocket.getOutputStream();
    }
    
    
}

