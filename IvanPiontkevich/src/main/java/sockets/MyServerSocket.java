package sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by serhii on 21.01.18.
 */
public class MyServerSocket {

    private int port;

    public MyServerSocket(int port) {
        this.port = port;
    }

    // start server socket and wait for new connection
    // if we establish new connection, firstly read from client read until meet '\n' as the last character,
    // execute that command through a command line on the  host,
    // then return the response(must be in one line with '\n' in the end) of execution
    // if client send "shutdown-server" command,  we kill the serverSocket
    // if we send "os", the server must reply with either "LINUX" or "WIN"
    public void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started...");
            while (!server.isClosed()) {
                Socket client = server.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                String command;
                if ((command = br.readLine()) != null) {
                    switch (command) {
                        case "shutdown-server":
                            pw.close();
                            server.close();
                            System.err.println("SERVER SHUTDOWN");
                            break;
                        case "os":
                            BufferedReader cmdResponse = getStreamFromRuntime("pwd");
                            if (showCmdResponse(cmdResponse).contains("/home")) {
                                pw.println("LINUX");
                                pw.close();
                            } else {
                                pw.println("WIN");
                                pw.close();
                            }
                            break;
                        default:
                            String str = showCmdResponse(getStreamFromRuntime(command));
                            pw.print(str);
                            pw.flush();
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedReader getStreamFromRuntime(String cmd) throws IOException {
        return new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream()));
    }

    private String showCmdResponse(BufferedReader stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String string;
        while ((string = stream.readLine()) != null) {
            sb.append(string).append("\n");
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        new MyServerSocket(8989).start();
    }
}
