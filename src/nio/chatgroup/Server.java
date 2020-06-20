package nio.chatgroup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private int port = 6666;
    private String msg = "";

    public Server() {
        try {
            // 将server的channel注册到select中
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            // 监听对应的端口号
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 服务端接收数据
    public void getData() throws IOException {

        while (true) {
            // 获取当前的选择器是否有事件发生
            int select = selector.select();

            if (select > 0) { // 表示有事件发生
                // 获取有事件的所有key
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {// 循环遍历key
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {// 假设是连接的key
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println(socketChannel.getRemoteAddress() + " 已连接....");
                    }
                    if (key.isReadable()) {// 如果是可读的key

                        msgForward(key);
                    }
                    iterator.remove();
                }
            }
        }
    }

    // 服务端将接收的接收的数据进行转发，排除自己
    private void msgForward(SelectionKey selectionKey) {

        SocketChannel socketChannel = null;

        try {
            // 通过key获取关联的channel
            socketChannel = (SocketChannel) selectionKey.channel();

            int read = socketChannel.read(ByteBuffer.allocate(1024));
            if (read > 0) {
                SocketChannel finalSocketChannel = socketChannel;

                selector.keys().forEach(key -> {

                    Channel targetChannel = key.channel();

                    if (targetChannel != finalSocketChannel && targetChannel instanceof SocketChannel) {
                        try {
                            ((SocketChannel) targetChannel).write(ByteBuffer.wrap(msg.getBytes()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }catch (Exception e) {

            try {
                System.out.println(socketChannel.getRemoteAddress() + " 离线了..");
                // 取消注册
                selectionKey.cancel();

                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Server server = new Server();
        server.getData();
    }
}

