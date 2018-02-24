package sockets;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

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
        //todo
        try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)){
            System.out.println("Server was started.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                String line = buffer.readLine();
                if (line != null) {
                    if (line.equals("shutdown-server")){
                        System.out.println("Server was shuted down.");
                        clientSocket.close();
                        serverSocket.close();
                        break;
                    }
                    if (line.equals("os")) {
                        writer.println("WIN");
                        writer.flush();
                    } else {
                        Runtime runtime = Runtime.getRuntime();
                        Process process = runtime.exec(line);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String response = reader.toString();
                        writer.println(response);
                        writer.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
