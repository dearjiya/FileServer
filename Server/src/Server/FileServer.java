package Server;
import java.nio.channels.SocketChannel;
import java.util.List;

public class FileServer {
	int serverPort;
	
	private static final int numOfThreads = 4;
	public SocketChannel remoteServer = null;
	public String configPath = "./config/serverconfig.properties";
	
//	List<Client> connections new Vector<Client>();
	
	public FileServer(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public void start() {
		try{
			createWorkThreads(numOfThreads);
			
			Runnable eRun = new EventManager(this);
			Thread eventThread = new Thread(eRun);
			eventThread.start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void createWorkThreads(int numOfThreads) {
		for(int i = 0; i < numOfThreads; i++){
			Runnable run = new FileThread(i);
			Thread thread = new Thread(run);
			thread.start();
		}
	}
}  