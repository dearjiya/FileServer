package Server;


public class FileThread implements Runnable {
	int myId;
	public FileThread(int id){
		this.myId = id;
	}
	public void run() {
		while(true){
			try {
				Thread.sleep(6000);
//				System.out.println("["+myId+"]"+"I slept 2seconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
