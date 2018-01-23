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
    public void start(){
        try {

            java.net.ServerSocket srvSocket = new java.net.ServerSocket(port);

            ClientConnection client = establishConnection(srvSocket);

            CmdExecutor cmdExecutor = new CmdExecutor();

            String cmd, result;
            while(!client.isClosed()){
                cmd = client.read();
                result = cmdExecutor.exec(cmd);
                client.send(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ClientConnection establishConnection(java.net.ServerSocket serverSocket) throws IOException {

        Socket socket = serverSocket.accept();

        ClientConnection clientConnection = new ClientConnection(socket);

        return clientConnection;

    }

    public static void main(String[] args) {
        ServerSocket ss = new ServerSocket(8080);
        ss.start();
    }
}
