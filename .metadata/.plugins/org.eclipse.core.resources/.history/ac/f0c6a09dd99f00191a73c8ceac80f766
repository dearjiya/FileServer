package Message;

import java.io.File;
import java.io.FileOutputStream;

import Util.AESencdec;

public class FileTransferMessage extends Message{
	
	private static final long serialVersionUID = 1L;
	
	long size;
	String fileName;
	byte[] buff;
	boolean encrypt;
	
	public FileTransferMessage(long _size, String _fileName, byte[] _buff, boolean _encrypt){
		if(_encrypt == true){
			try{
				buff = AESencdec.encrypt(_buff);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			buff = _buff;
		}
		size = _size;
		fileName = _fileName;
		encrypt = _encrypt;
	}
	
	public Message handle() {
		System.out.println("Receive " + fileName + "(size: " + size + ", encryptOpt: " + encrypt + ")" );
		String filePath = fileServer.configParameters.get("FileRepository") + "\\" + fileName;
		
		try{
			if(encrypt == true){
				buff = AESencdec.decrypt(buff);
			}
			File outFile = new File(filePath);
			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
			fileOutputStream.write(buff);
			fileOutputStream.close();
			ResponseToClientMessage msg = new ResponseToClientMessage("Receive " + fileName + " to ");
			return msg;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
