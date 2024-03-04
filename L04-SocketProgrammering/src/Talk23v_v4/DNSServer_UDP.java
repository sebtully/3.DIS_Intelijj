package Talk23v_v4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class DNSServer_UDP {

    private static final Map<String, String> ipToNameMapping = new HashMap<>();

    static {
        // Hardkodning af IP-adresser til navne
        ipToNameMapping.put("Mikkel", "10.10.139.9");
        ipToNameMapping.put("Mazza", "10.10.138.80");
        ipToNameMapping.put("Tully", "10.10.138.166");
    }

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(9090)) {
            byte[] receiveData = new byte[1024];
            byte[] sendData;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String clientMessage = new String(receivePacket.getData());

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                String response = resolve(clientMessage.trim());
                sendData = response.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String resolve(String ipAddress) {
        return ipToNameMapping.getOrDefault(ipAddress, "Unknown");
    }
}