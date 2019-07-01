package Server;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

import Parser.ConfigLoader;

public class FileServer {
	int serverPort;
	
	public String serverName;
	private static final int numOfThreads = 4;
	public SocketChannel remoteServer = null;
	public HashMap<String, String> configParameters;
	
	public EventManager eventManager;
	
	//	List<Client> connections new Vector<Client>();
	
	public FileServer(String serverName, int serverPort) {
		this.serverPort = serverPort;
		this.serverName = serverName;
		configParameters = new HashMap<String, String>();
	}
	
	public void start() {
		try{
			ConfigLoader loader = new ConfigLoader("./config/" + serverName + "_config.properties");
			configParameters.put("RemoteIpPort", loader.getValue("RemoteIpPort"));
			configParameters.put("FileRepository", loader.getValue("FileRepository"));
			configParameters.put("FileDataEncrypt", loader.getValue("FileDataEncrypt"));
			
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