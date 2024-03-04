package Test;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class TCPNameServer {

    private Map<String, String> registeredClients = new HashMap<>();

    public static void main(String[] args) {
        TCPNameServer nameServer = new TCPNameServer();
        nameServer.startServer();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(53);
            System.out.println("TCP Name Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientCommand = inFromClient.readLine();
            String[] commandParts = clientCommand.split(" ");

            if (commandParts[0].equals("REGISTER")) {
                registerClient(commandParts[1], commandParts[2]);
                outToClient.println("REGISTERED");
            } else if (commandParts[0].equals("GETLIST")) {
                sendClientList(outToClient);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void registerClient(String clientName, String networkId) {
        registeredClients.put(clientName, networkId);
        System.out.println("Client registered: " + clientName);
    }

    private synchronized void sendClientList(PrintWriter outToClient) {
        for (Map.Entry<String, String> entry : registeredClients.entrySet()) {
            outToClient.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
