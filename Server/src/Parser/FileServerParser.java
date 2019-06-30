package Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.channels.SocketChannel;

import Message.FileTransferMessage;
import Message.Protocol;
import Message.ServerSocketForObject;
import Server.FileServer;


public class FileServerParser {
	FileServer fileServer;
	public FileServerParser(FileServer server){
		this.fileServer = server;
	}

	public String parse(String cmd) throws Exception {
		FunctionCall func = new FunctionCall();
		String sp[] = cmd.split(" ");
		String response = "";
		
		switch (sp[0]) {
		case Protocol.CONNECT_TO:
			System.out.println("Connect to " + sp[1]);
			String ipPort[] = sp[1].split(":");
			if(ipPort.length != 2){
				// �߸��� �޽��� Ŭ���̾�Ʈ���� �˷��ִ� �κ� ���߿� ���� �ʿ�
				response = "Wrong Message";
			}
			String ip = ipPort[0];
			String port = ipPort[1];
			SocketChannel remoteChannel = func.RequireConnect(ip,port);
			if(remoteChannel == null){
				// �������
				response = "Failed Connection";
			}
			else{
				this.fileServer.remoteServer = remoteChannel;
				response = "Success Connection";
			}
			break;
		case Protocol.SEND_FILE:
			// SEND�� �ϰ� ���� �̸� ������ �ʾ��� ��� ó�� �ʿ�
			
			FileInputStream fin;
			
			System.out.println("Try to send file (filename: " + sp[1] + ")");
			
			if(this.fileServer.remoteServer == null){
				// Connect ��û �ʿ�
				response = "Require to CONNECT First";
			}
			else{
				long fileSize;
				String filePath = sp[1];
				File sendFile = new File(filePath);
				if(sendFile.exists()){
					fileSize = sendFile.length();
				}
				else{
					response = "No Such File exists";
					break;
				}
				
				fin = new FileInputStream(sendFile);
				byte[] buffer = new byte[(int) fileSize];
				// ���� 4�Ⱑ ������ ������ ��
				int data = fin.read(buffer);
				
				if (fileSize != data) {
					response = "Critical Error!!";
					break;
				}
				
				fin.close();
				
				FileTransferMessage ftMsg = new FileTransferMessage(fileSize, filePath, buffer);
				
				ServerSocketForObject sock = new ServerSocketForObject(fileServer.remoteServer);
				sock.send(ftMsg);
				
				response = "Sent File[" + filePath + "] to remote server.";
			}
			
			break;
		default:
			response = "Unknown Protocol";
			System.out.println("Unknown Protocol");
		}
		
		return response;
	}

	
}