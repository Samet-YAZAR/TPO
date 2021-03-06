package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public abstract class AddingServer implements Evaluate{

    private static final int PORT_NUMBER= 1234;
    private static final int BUFFER_SIZE = 4096;
    static String msg;
    static int result;

    public static void addingServer(){

        try{
            ServerSocketChannel server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(PORT_NUMBER));
            server.socket().setReuseAddress(true);
            server.configureBlocking(false); //Once more why ?

            Selector selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while(true){
                int channelCount = selector.select();
                if(channelCount> 0 ){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if(key.isAcceptable()){
                            SocketChannel client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ,client.socket().getPort());
                        }else if(key.isReadable()){
                            SocketChannel client = (SocketChannel) key.channel();
                            buffer.clear();
                            client.read(buffer);
                            buffer.flip();
                            String s = new String(buffer.array()).trim();
                            result = Evaluate.calculate(s);

                            client.register(selector,SelectionKey.OP_WRITE,client.socket().getPort());
                        }else if(key.isWritable()){
                            SocketChannel client = (SocketChannel) key.channel();
                            buffer.clear();
                            buffer.limit(4);
                            buffer.putInt(result);
                            buffer.rewind();
                            client.write(buffer);
                            buffer.clear();
                            client.close();
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
