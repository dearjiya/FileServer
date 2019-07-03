package Message;

import java.io.File;
import java.io.FileInputStream;

public class FileRequestMessage extends Message {

	String fileName;
	public FileRequestMessage(String reqFileName) {
		fileName = reqFileName;
	}

	public Message handle() {
		
		String filePath = this.fileServer.configParameters.get("FileRepository") + "\\" +fileName;
		System.out.println("Received File request for " + filePath);
		File reqFile = new File(filePath);
		long fileSize;
		if(reqFile.exists()){
			try{
				fileSize = reqFile.length();
				FileInputStream fin = new FileInputStream(reqFile);
				byte[] buffer = new byte[(int) fileSize];
				int data = fin.read(buffer);
				fin.close();
				
				if (fileSize != data) {
					System.out.println("Critical Error!!");
				}
				FileTransferMessage transferMsg = new FileTransferMessage(fileSize, fileName, buffer, Boolean.parseBoolean(this.fileServer.configParameters.get("FileDataEncrypt")));
				System.out.println("Sent requested file");
				return transferMsg;
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		else{
			System.out.println("No Such File exists");
		}
		
		return null;
	}

}
