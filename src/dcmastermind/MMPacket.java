/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermind;

import java.io.ByteArrayOutputStream;
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
    private InputStream in;
    private OutputStream out;
    private int msg_size;
    
    public MMPacket(Socket socket)throws IOException{
        this.clientSocket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public Socket getSocket() {
        return clientSocket;
    }

    public void setSocket(Socket socket) {
        this.clientSocket = socket;
    }
    
    
    /**
     * reads packet from the MMPacket's socket and returns a byte array of the data
     * @return
     * @throws IOException 
     */
    public byte[] readPackets()throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] byteBuffer = new byte[BUFSIZE];
       
        for(int i;(i=in.read(byteBuffer))!= 1;){
            baos.write(byteBuffer,0,i);
        }
        byte result[] = baos.toByteArray();
        return result;
    }
    /**
     * takes in data to be written to the out of the packet.
     * @param bytes
     * @throws IOException 
     */
    public void writePackets(byte[] bytes)throws IOException{
        out.write(bytes);
    }
    
    
}

