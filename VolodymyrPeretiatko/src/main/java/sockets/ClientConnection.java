package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientConnection {

    private Socket socket;
    private BufferedReader in;
    private PrintStream out;
    private boolean connected;

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;

        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintStream(socket.getOutputStream());
        this.connected = true;
    }

    public String read() {

        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            this.connected = false;
            return "";
        }
    }

    public void send(String msg){
        out.println(msg);
    }

    public boolean isConnected() {
        return this.connected;
    }

    public Socket getSocket() {
        return socket;
    }
}
