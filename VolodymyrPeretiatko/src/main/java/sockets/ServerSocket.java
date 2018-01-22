package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by serhii on 21.01.18.
 */
public class ServerSocket {

    private int port;

    public ServerSocket(int port) {
        this.port = port;
    }

    // start server socket and wait for new connection
    // if we establish new connection, firstly read from client read until meet '\n' as the last character,
    // execute that command through a command line on the  host,
    // then return the response(must be in one line with '\n' in the end) of execution
    // if client send "shutdown-server" command,  we kill the serverSocket
    // if we send "os", the server must reply with either "LINUX" or "WIN"
    public void start(){
        try {
            java.net.ServerSocket server = new java.net.ServerSocket(port);

            Socket client = establishConnection(server);

            while(!client.isClosed()){

                DataInputStream in = new DataInputStream(client.getInputStream());
                String clientMsg = in.readUTF();

                PrintWriter out = new PrintWriter(client.getOutputStream());
                out.printf(clientMsg);
                out.flush();

            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Socket establishConnection(java.net.ServerSocket serverSocket) throws IOException {

        Socket client = serverSocket.accept();
        return client;

    }

    public static void main(String[] args) {
        ServerSocket ss = new ServerSocket(8080);
        ss.start();
    }
}
