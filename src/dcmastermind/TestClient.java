/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcmastermind;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author 1432581
 */
public class TestClient {
    
    public static void main(String[] args) throws IOException{
        int server_port = 50000;
        byte[] packet = "hello world".getBytes();
        Socket soc = new Socket("10.172.14.124",server_port);
        MMPacket mmp = new MMPacket(soc);
        mmp.writePackets(packet);
    }
}
