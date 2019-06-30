package Message;

public class ResponseToClientMessage extends Message{

	public String resMessage;
	
	public ResponseToClientMessage(String msg){
		resMessage = msg;
	}
	
	@Override
	public Message handle() {
		return null;
	}
	
}
