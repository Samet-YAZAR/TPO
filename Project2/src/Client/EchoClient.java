package Client;

import jdk.nashorn.internal.runtime.JSONFunctions;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public abstract class EchoClient {

    final static int ECHO_PORT = 7777;
    static ByteBuffer buffer = ByteBuffer.allocate(4096);
    static String response;

    public static void echo(String host, String message){
        try {
            //create socker
            InetSocketAddress socketAddress = new InetSocketAddress(host,ECHO_PORT);
            SocketChannel client = SocketChannel.open(socketAddress);
            //save message to buffer
            buffer = ByteBuffer.wrap(message.getBytes());

            System.out.println("Client is sent "+ message );
            //send message
            client.write(ByteBuffer.wrap(message.getBytes()));
            //clean buffer
            buffer.clear();
            //read servers message
            client.read(buffer);

            buffer.flip();
            response = new String(buffer.array());
            System.out.println("Server response is" + response);

            buffer.clear();
            client.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
