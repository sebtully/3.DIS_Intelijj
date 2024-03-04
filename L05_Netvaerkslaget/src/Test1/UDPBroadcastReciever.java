package Test1;

import java.io.IOException;
import java.net.*;

class UDPBroadcastReceiver {
    private DatagramSocket broadcastSocket;
    private DatagramSocket svarSocket;

    public UDPBroadcastReceiver() throws SocketException {
        this.broadcastSocket = new DatagramSocket(4321);
        this.svarSocket = new DatagramSocket();
    }

    public static void main(String[] args) throws SocketException {
        UDPBroadcastReceiver receiver = new UDPBroadcastReceiver();
        receiver.lytEfterBroadcast("Hello!");
    }

    public void lytEfterBroadcast(String tt) {
        Runnable runnable = new Runnable() {
            public void run() {
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
                boolean modtaget = false;
                while (!modtaget) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    try {
                        broadcastSocket.receive(receivePacket);
                        String sentence = new String(receivePacket.getData()).trim();
                        InetAddress IPAddress = receivePacket.getAddress();
                        System.out.println("Received broadcast: " + sentence +" / " + IPAddress.getHostAddress());

                        int port = receivePacket.getPort();
                        sendData = tt.trim().getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        svarSocket.send(sendPacket);
                        modtaget = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                svarSocket.close();
            }
        };
        runnable.run();
    }
}