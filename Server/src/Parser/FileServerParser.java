package Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Properties;

import Message.FileRequestMessage;
import Message.FileTransferMessage;
import Message.CommandCode;
import Message.ServerSocketForObject;
import Server.FileServer;


public class FileServerParser {
	FileServer fileServer;
	String fileNameList;
	
	public FileServerParser(FileServer server){
		this.fileServer = server;
	}

	public String parse(String cmd) throws Exception {
		FunctionCall func = new FunctionCall();
		String sp[] = cmd.split(" ");
		String response = "";
		
		switch (sp[0].toUpperCase()) {
		case CommandCode.CONNECT_TO:
			int len = sp.length;
			String tmpPath = "";
			
			if(len == 1) {
				// ServerConfig remoteipport 로 접속
				tmpPath = this.fileServer.configParameters.get("RemoteIpPort");
			}
			else if((int) len == 2) {
				tmpPath = sp[1];
			}
			else {
				
			}
			System.out.println("Connect to " + tmpPath);
			String ipPort[] = tmpPath.split(":");
			if(ipPort.length != 2){
				// 잘못된 메시지 클라이언트에게 알려주는 부분 나중에 구현 필요
				response = "Wrong Message";
			}
			String ip = ipPort[0];
			String port = ipPort[1];
			SocketChannel remoteChannel = func.RequireConnect(ip,port);
			if(remoteChannel == null){
				// 연결실패
				response = "Failed Connection";
			}
			else{
				this.fileServer.remoteServer = remoteChannel;
				response = "Success Connection";
			}
			
			remoteChannel.configureBlocking(false);
			remoteChannel.register(fileServer.eventManager.selector, SelectionKey.OP_READ);
			
			break;
		case CommandCode.PUSH_FILE:
			// SEND만 하고 파일 이름 보내지 않았을 경우 처리 필요
			
			FileInputStream fin;
			
			System.out.println("Try to send file (filename: " + sp[1] + ")");
			
			if(this.fileServer.remoteServer == null){
				// Connect 요청 필요
				response = "Require to CONNECT First";
			}
			else{
				long fileSize;
				String filePath = this.fileServer.configParameters.get("FileRepository") + "\\" + sp[1];
				System.out.println(filePath);
				File sendFile = new File(filePath);
				
				if(!sendFile.exists()){
					response = "No Such File exists. PATH: " + filePath;
					break;
				}
				
				fileSize = sendFile.length();
				fin = new FileInputStream(sendFile);
				byte[] buffer = new byte[(int) fileSize];
				// 파일 4기가 넘으면 문제가 됨
				int data = fin.read(buffer);
				
				if (fileSize != data) {
					response = "Critical Error!!";
					break;
				}
				
				fin.close();
				
				FileTransferMessage ftMsg = new FileTransferMessage(fileSize, sp[1], buffer,Boolean.parseBoolean(this.fileServer.configParameters.get("FileDataEncrypt")));
				
				ServerSocketForObject sock = new ServerSocketForObject(fileServer.remoteServer);
				sock.send(ftMsg);
				
				response = "Sent File[" + filePath + "] to remote server.";
			}
			
			break;
		case CommandCode.PULL_FILE:
			if(this.fileServer.remoteServer == null){
				// Connect 요청 필요
				response = "Require to CONNECT First";
			}
			else{
				if(sp.length != 2){
					response = "Enter the filename";
				}
				else{
					String fileName=sp[1];
					
					FileRequestMessage fReqMsg = new FileRequestMessage(fileName);
					ServerSocketForObject sock = new ServerSocketForObject(this.fileServer.remoteServer);
					sock.send(fReqMsg);
					response = "File request success";
				}
			}
			
			break;
		case CommandCode.GET_FILELIST:
			if(this.fileServer.remoteServer == null){
				// Connect 요청 필요
				response = "Require to CONNECT First";
			}
			else {
				if(sp.length != 1) {
					response = "Wrong LS Command";
				}
				else {
					String filePath = this.fileServer.configParameters.get("FileRepository");
					File dirFile = new File(filePath);
					File []fileList = dirFile.listFiles();
					fileNameList = "";
					System.out.println("filePath: "+filePath);
					
					for(File tempFile : fileList) {
						if(tempFile.isFile()) {
							String tempFileName = tempFile.getName();
							fileNameList += tempFileName +" ";
							System.out.println("fileName: "+tempFileName);
							System.out.println(fileNameList);
						}
					}
					
					response = "This is FileList";
				}
			}
			
			break;
		case CommandCode.STOP_SERVER:
			response = "Terminate process is start";
//			System.exit(0);
			this.fileServer.stopServer();
		default:
			response = "Unknown Protocol";
			System.out.println("Unknown Protocol");
		}
		
		return response;
	}
}
