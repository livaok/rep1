package backend;

/**
 * @author liva
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	static final int PORT = 8880;
	List<RequestHandler> clients = new ArrayList<RequestHandler>();


	public Server() {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server is launched");

			while (true) {
				clientSocket = serverSocket.accept();
				RequestHandler client = new RequestHandler(clientSocket, this);
				clients.add(client);
				new Thread(client).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
				System.out.println("Server finished his work");
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMsgToAllClients(String msg) {
		for (RequestHandler client : clients) {
			client.sendMsg(msg);
		}
	}



	public void removeClientFromServer(RequestHandler client) {
		clients.remove(client);
	}
}
