package Message;

import Parser.FileServerParser;

public class CmdMessage extends Message {
	private static final long serialVersionUID = 1L;
	String message;
	
	public CmdMessage(String msg){
		message = msg;
	}

	@Override
	public Message handle() {
		System.out.println("CmdMessage read: " + message);
		FileServerParser parser = new FileServerParser(fileServer);
		String resMessage = "";
		
		try {
			resMessage = parser.parse(message);
			
		}catch(NullPointerException e) {
			resMessage = "Wrong filePath";
		}
		catch (Exception e) {
			System.out.println("ExceptionMessage: " + e);
			e.printStackTrace();
			resMessage = "Unknown error!!";
		}
		
		ResponseToClientMessage resMsg = new ResponseToClientMessage(resMessage);
		
		return resMsg;
	}
}
