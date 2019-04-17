package myClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author liva
 */
public class ClientWindow extends JFrame {

	private static final int         CLIENT_PORT = 8888;
	private static final String      CLIENT_HOST = "localhost";
	private              Socket      socket;
	private              Scanner     inMessage;
	private              PrintWriter outMessage;
	private              String      clientName;

	JTextArea  chat;
	JPanel     panel;
	JTextField inputName;
	JTextField inputMessage;
	JButton    sendMessage;

	public ClientWindow() {
		try {
			socket = new Socket(CLIENT_HOST, CLIENT_PORT);
			inMessage = new Scanner(socket.getInputStream());
			outMessage = new PrintWriter(socket.getOutputStream());

			setBounds(300, 300, 400, 400);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setTitle("ЧАТ");

			chat = new JTextArea();
			chat.setEditable(false);
			add(chat, BorderLayout.CENTER);

			panel = new JPanel(new BorderLayout());
			add(panel, BorderLayout.SOUTH);

			inputName = new JTextField("Введите имя");
			inputMessage = new JTextField("Введите сообщение");
			sendMessage = new JButton("Отправить");

			panel.add(inputName, BorderLayout.WEST);
			panel.add(inputMessage, BorderLayout.CENTER);
			panel.add(sendMessage, BorderLayout.EAST);

			inputName.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					super.focusGained(e);
					inputName.setText("");
				}
			});

			inputMessage.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					super.focusGained(e);
					inputMessage.setText("");
				}
			});

			sendMessage.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					outMessage.write(inputName.getText() + " : " + inputMessage.getText());
				}
			});

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(true);


	}
}
