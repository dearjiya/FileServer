import Server.FileServer;


public class Main {
	private static final int SERVER_PORT = 10001;
	public static void main(String[] args) {
		FileServer server = new FileServer(SERVER_PORT);
		server.start();
	}
}



// 굳이 모든 명령어를 다 클라이언트에 보내야하는지? 보내기전에 프로토콜 확인하는 로직은?