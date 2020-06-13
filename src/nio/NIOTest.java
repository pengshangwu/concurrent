package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTest {

    public static void main(String[] args) throws Exception {
        // 将对应的通道关联对应资源的来源和目的地
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileInChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutChannel = fileOutputStream.getChannel();

        // 创建一个固定大小的buffer用于装载数据
        ByteBuffer buffer = ByteBuffer.allocate(10);

        while (true) {
            // 每次读取前，清空buffer中的指标，否则position不为0就存不了数据
            buffer.clear();
            // 然后将数据读入到buffer中
            int read = fileInChannel.read(buffer);
            if(read == -1) {
                break;
            }
            // 上述读完之后，开始反转将position置为0
            buffer.flip();
            // 开始将数据写入到通道
            fileOutChannel.write(buffer);

        }

        fileInChannel.close();
        fileOutChannel.close();
    }
}
