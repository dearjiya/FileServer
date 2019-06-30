import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class TestClient {

    public static void main(String[] args) throws IOException {
    	
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);

        System.out.println("Require Connection");
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 10001));
        System.out.println("Connection Success");

        ByteBuffer byteBuffer;
        Charset charset = Charset.forName("UTF-8");
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Cmd > ");
        
        // Input �޾� ���ɾ� ������
        byteBuffer = charset.encode(sc.nextLine());
        socketChannel.write(byteBuffer);
        System.out.println("Sending Success");

        // Server�κ��� ������ �ޱ�
        byteBuffer = ByteBuffer.allocate(100);
        socketChannel.read(byteBuffer);
        byteBuffer.flip();

        String data = charset.decode((byteBuffer)).toString();
        System.out.println("Received Data : " + data);

        if (socketChannel.isOpen())
            socketChannel.close();

    }
}