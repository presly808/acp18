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

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;

        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintStream(socket.getOutputStream());

    }

    public String read() {

        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            //this.connected = false;
            return "";
        }
    }

    public void send(String msg){
        out.println(msg);
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public Socket getSocket() {
        return socket;
    }

    public void close(){
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
