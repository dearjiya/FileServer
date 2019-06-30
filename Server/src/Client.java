import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

import Message.CmdMessage;
import Message.Message;
import Message.ResponseToClientMessage;
import Message.ServerSocketForObject;

public class Client {

    public static void main(String[] args) {
    	
    	try{
    		SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);

            System.out.println("Require Connection");
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 10001));
            System.out.println("Connection Success");
            
            Scanner sc = new Scanner(System.in);
            
            while(true){
            	System.out.print("Cmd > ");
            	
            	// Input �޾� ���ɾ� ������
            	String msg = sc.nextLine();
            	if (msg.equals("q"))
            		break;
            	CmdMessage cmd = new CmdMessage(msg);
            	ServerSocketForObject sock = new ServerSocketForObject(socketChannel);
            	sock.send((Message)cmd);
            	
            	System.out.println("Sending Success");
            	
            	// Server�κ��� ������ �ޱ�
            	ResponseToClientMessage recvMsg = (ResponseToClientMessage)sock.recv();
            	
            	System.out.println("Received Data : " + recvMsg.resMessage);
            }
            
            sc.close();
//            if (socketChannel.isOpen())
//                socketChannel.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        

    }
}