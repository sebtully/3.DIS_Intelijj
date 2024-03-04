package Talk23v_v3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class DNSServer {

    private static final Map<String, String> ipToNameMapping = new HashMap<>();

    static {
        // Hardkodning af IP-adresser til navne
        ipToNameMapping.put("Mikkel", "10.10.139.9");
        ipToNameMapping.put("Mazza", "10.10.138.80");
        ipToNameMapping.put("Tully", "10.10.138.166");

    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            System.out.println("DNS-serveren venter på klienter...");
            while (true) {
                new DNSHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class DNSHandler extends Thread {
        private final Socket clientSocket;

        public DNSHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream())) {

                String clientMessage = inFromClient.readLine();
                System.out.println("Anmodning modtaget fra klient: " + clientMessage);

                String response = resolve(clientMessage);
                outToClient.writeBytes(response + '\n');

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String resolve(String ipAddress) {
            return ipToNameMapping.getOrDefault(ipAddress, "Unknown");
        }
    }
}
