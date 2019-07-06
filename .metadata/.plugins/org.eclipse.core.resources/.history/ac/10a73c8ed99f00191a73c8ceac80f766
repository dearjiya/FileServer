package Message;

import Server.FileServer;



import java.io.Serializable;
import java.nio.channels.ClosedChannelException;
public abstract class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public FileServer fileServer;

	public abstract Message handle() throws ClosedChannelException;

}

