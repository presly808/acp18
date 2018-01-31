package sockets;

import java.io.*;
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
    public void start() {

        java.net.ServerSocket srvSocket = initServerSocket();

        String cmd = "";
        while (!"shutdown-server".equalsIgnoreCase(cmd)){

            ClientConnection client = establishConnection(srvSocket);
            CmdExecutor cmdExecutor = new CmdExecutor();

            String result;
            //while (client != null && client.isConnected()) {

                cmd = client.read();
                if (cmd == null) continue;

                result = cmdExecutor.exec(cmd);
                client.send(result);
            //}
            client.close();
        }
        close(srvSocket);

    }

    private java.net.ServerSocket initServerSocket(){

        java.net.ServerSocket srvSocket = null;
        try {
            srvSocket = new java.net.ServerSocket(this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srvSocket;
    }

    private void close(java.net.ServerSocket srvSocket){
        try {
            srvSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ClientConnection establishConnection(java.net.ServerSocket serverSocket) {

        ClientConnection clientConnection = null;
        Socket socket;
        try {
            socket = serverSocket.accept();
            clientConnection = new ClientConnection(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientConnection;

    }

    public static void main(String[] args) {
        ServerSocket ss = new ServerSocket(8080);
        ss.start();
    }
}
