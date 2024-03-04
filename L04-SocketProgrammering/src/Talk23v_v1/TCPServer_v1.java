package Talk23v_v1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer_v1 {
    public static void main(String[] args) throws Exception {

        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomSocket = new ServerSocket(6789);
        System.out.println("Serveren venter på klient");
        Socket connectionSocket = welcomSocket.accept();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        System.out.println("Klient forbundet til Server");

        clientSentence = inFromClient.readLine();
        System.out.println(clientSentence);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(System.in));
        capitalizedSentence = inFromServer.readLine() + '\n';
        outToClient.writeBytes(capitalizedSentence);

    }
}
