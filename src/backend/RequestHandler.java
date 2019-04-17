package backend;

/**
 * @author liva
 */
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler implements Runnable {

	private static int clientsCount = 0;
	private Server server;
	private Socket clientSocket;
	private PrintWriter outMessage;
	private Scanner inMessage;


	public RequestHandler(Socket clientSocket, Server server) {
		try {
			clientsCount++;
			this.server = server;
			this.clientSocket = clientSocket;
			this.outMessage = new PrintWriter(clientSocket.getOutputStream());
			this.inMessage = new Scanner(clientSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}


	}

	private void close() {
		server.removeClientFromServer(this);
		clientsCount--;
		server.sendMsgToAllClients("In our chat client count is " + clientsCount);
	}

	public void sendMsg(String msg) {
		outMessage.println(msg);
		outMessage.flush();

	}

	@Override
	public void run() {

		try {
			server.sendMsgToAllClients("We have a new clients");

			while (true) {
				if (inMessage.hasNext()) {
					String clientMsg = inMessage.nextLine();
					System.out.println(clientMsg);

					server.sendMsgToAllClients(clientMsg);
				}

				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}


	}
}
