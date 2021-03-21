package Client;

import Server.AddingServer;
import com.sun.beans.editors.ByteEditor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class AddingClient {

    final static int ADDING_PORT = 1234;
    static ByteBuffer buffer = ByteBuffer.allocate(4096);
    static int response;

    public static void adding(String host,String message){
        try{

            InetSocketAddress socketAddress = new InetSocketAddress(host,ADDING_PORT);
            SocketChannel client = SocketChannel.open(socketAddress);

            buffer = ByteBuffer.wrap(message.getBytes());

            System.out.println("Client is sent "+ message);
            client.write(buffer);
            //The same process we applied for echo
            buffer.clear();
            buffer.limit(4);
            client.read(buffer);
            //set position to 0
            buffer.rewind();
            //convert answer to the int
            response = buffer.getInt();
            System.out.println("Server response is "+ response);
            //clean buffer
            buffer.clear();
            client.close();

        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
