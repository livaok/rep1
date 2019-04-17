package myServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author liva
 */
public class Server {

	private static final int          SERVER_PORT = 8888;


	private Scanner  in;
	private PrintWriter out;

	public Server() {
		Socket socket = null;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Server launched");

			while (true){
				socket = serverSocket.accept();                                      //прослушивание
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream());
				System.out.println(in.hasNext());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {

			try {
				serverSocket.close();
				socket.close();
				System.out.println("—ервер завершил работу");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}






