package Talk23v_v2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer_v2 {

    private BufferedReader inFromClient;

    public static void main(String[] args) throws Exception {
        TCPServer_v2 server = new TCPServer_v2();
        server.startServer();
    }

    public void startServer() {
        try {
            ServerSocket welcomeSocket = new ServerSocket(6789);
            System.out.println("Serveren venter på klient");
            Socket connectionSocket = welcomeSocket.accept();
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            System.out.println("Klient forbundet til Server");


            listenForMessage();


            BufferedReader infromServer = new BufferedReader(new InputStreamReader(System.in));
            while (!connectionSocket.isClosed()) {
                String serverMessage = infromServer.readLine();
                outToClient.writeBytes(serverMessage + '\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            try {
                while (true) {
                    String message = inFromClient.readLine();
                    System.out.println("FROM CLIENT: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
