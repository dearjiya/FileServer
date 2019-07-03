import Server.FileServer;


public class Main {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: FileServer [server_name]");
			System.exit(1);
		}
		FileServer server = new FileServer(args[0]);
		server.startServer();
	}
}



