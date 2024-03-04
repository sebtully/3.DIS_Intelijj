package Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

	private BufferedReader inFromServer = null;
	private DataOutputStream outToServer = null;
	private String clientName;
	private String networkId;

	public static void main(String[] args) throws Exception {
		TCPClient client = new TCPClient();
		client.startClient();
	}

	public void startClient() {
		try {
			String sentence;
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			Socket clientSocket = new Socket("10.10.138.166", 6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			System.out.println("Indtast et ord");

			registerWithServer();

			listenForMessage();

			while (!clientSocket.isClosed()) {
				sentence = inFromUser.readLine();
				outToServer.writeBytes(sentence + '\n');
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registerWithServer() throws IOException {
		clientName = "Alice";
		networkId = "123456";

		String registrationMessage = "REGISTER " + clientName + " " + networkId + "\n";
		outToServer.writeBytes(registrationMessage);

		String response = inFromServer.readLine();
		System.out.println("Server response: " + response);

		if (response.equals("REGISTERED")) {
			System.out.println("Registration successful!");
		} else {
			System.out.println("Registration failed. Exiting...");
			System.exit(1);
		}
	}


	public void listenForMessage() {
		new Thread(() -> {
			try {
				while (true) {
					String serverMessage = inFromServer.readLine();
					System.out.println("FROM SERVER: " + serverMessage);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
