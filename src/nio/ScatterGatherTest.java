package nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scatter；分散
 * Gather：聚合
 */
public class ScatterGatherTest {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel open = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannel = open.bind(new InetSocketAddress(7000));

        SocketChannel socketChannel = serverSocketChannel.accept();

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        int messageLength = 8;

        while (true) {

            long read = 0;

            // 如果读取的数据还没有填满byteBuffers大小，则继续填
            while(read < messageLength) {

                read += socketChannel.read(byteBuffers);
                System.out.println("byteRead=" + read);
                // 每一次的读取都输出对应的buffer信息
                Arrays.stream(byteBuffers).map(buffer -> "position=" + buffer.position() + ", limit=" + buffer.limit()).forEach(System.out :: println);
            }

            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            // 当上述的byteBuffers空间放满了之后，开始从byteBuffers读并写出数据
            long write = 0;
            while (write < messageLength) {
               write += socketChannel.write(byteBuffers);
            }

            Arrays.asList(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead:=" + read + " byteWrite=" + write + ", messagelength" + messageLength);
        }

    }
}
