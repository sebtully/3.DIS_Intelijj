package Talk23v_v2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient_v2 {

    private BufferedReader inFromServer;

    public static void main(String[] args) throws Exception {
        TCPClient_v2 client = new TCPClient_v2();
        client.startClient();
    }

    public void startClient() {
        try {
            String sentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket("localhost", 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Indtast et ord");


            listenForMessage();


            while (!clientSocket.isClosed()) {
                sentence = inFromUser.readLine();
                outToServer.writeBytes(sentence + '\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
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
