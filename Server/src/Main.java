import Server.FileServer;


public class Main {
	public static void main(String[] args) {
		System.out.println("thread: " + Thread.currentThread().getName());
		if (args.length != 1) {
			System.out.println("Usage: FileServer [server_name]");
			System.exit(1);
		}
		FileServer server = new FileServer(args[0]);
		server.startServer();
	}
}



