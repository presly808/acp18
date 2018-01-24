package sockets.exclude.clientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientServ {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.1.1", 8080);

        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println("ls");
        pw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = br.readLine();

        System.out.println(line);
    }
}
