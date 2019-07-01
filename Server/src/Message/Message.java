package Message;

import java.io.Serializable;
import java.nio.channels.SocketChannel;

import Server.FileServer;

public abstract class Message implements Serializable {

	public FileServer fileServer;

	public abstract Message handle();

}
