package Message;

public class ResponseToClientMessage extends Message{

	private static final long serialVersionUID = 1L;
	public String resMessage;
	
	public ResponseToClientMessage(String msg){
		resMessage = msg;
	}
	
	@Override
	public Message handle() {
		return null;
	}
	
}
