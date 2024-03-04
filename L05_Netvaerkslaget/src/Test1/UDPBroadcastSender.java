package Test1;

import java.net.*;

class UDPBroadcastSender {
    private DatagramSocket socket;

    public UDPBroadcastSender() throws SocketException {
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(10000);
    }

    public static void main(String[] args) throws Exception {
        UDPBroadcastSender sender = new UDPBroadcastSender();
        sender.udsendBroadcast("Hallå, hvor er i?");
    }

    public void udsendBroadcast(String t) throws Exception {
        InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
        byte[] buffer = t.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, 4321);

        for (int i = 0; i < 3; i++) {
            try {
                socket.send(packet);
                DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
                socket.receive(response);
                System.out.println("Response received: " + new String(response.getData()).trim());
                break;
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout, retrying...");
            }
        }
    }
}