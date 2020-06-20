package nio.chatgroup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    private SocketChannel socketChannel;
    private Selector selector;
    private final String host = "127.0.0.1";
    private int port = 6666;

    public Client() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println(socketChannel.getLocalAddress() + " is ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 接收服务器传来数据
    public void getData() {
       while (true) {
           try {
               int select = selector.select(1000);
               if(select > 0) {
                   Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                   while (iterator.hasNext()) {
                       SelectionKey key = iterator.next();
                       if(key.isReadable()) {
                           SocketChannel channel = (SocketChannel) key.channel();

                           ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                           if(channel.read(byteBuffer) > 0) {
                               System.out.println(new String(byteBuffer.array()).trim());
                           }
                       }
                   }
                   // 不太清除如果删除了该key的话，就又得重新accpet然后再更改register(selector, SelectionKey.OP_READ
                   iterator.remove();
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    // 发送数据
    public void sendMsg() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            try {
                String msg = socketChannel.getLocalAddress().toString() + "说: " + line;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        Client client = new Client();
        new Thread(client::getData).start();
        new Thread(client::sendMsg).start();
    }
}
