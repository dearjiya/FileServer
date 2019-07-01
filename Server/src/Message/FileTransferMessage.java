package Message;

public class FileTransferMessage extends Message{
	
	private static final long serialVersionUID = 1L;
	
	long size;
	String filePath;
	byte[] buff;
	
	public FileTransferMessage(long _size, String _filePath, byte[] _buff){
		size = _size;
		filePath = _filePath;
		buff = _buff;
	}
	
	@Override
	public Message handle() {
		System.out.println(size +","+ filePath +","+buff.length);
		return null;
	}

}
