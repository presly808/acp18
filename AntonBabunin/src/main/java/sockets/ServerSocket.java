package sockets;

import sun.rmi.runtime.RuntimeUtil;

import java.io.*;
import java.net.Socket;

/**
 * Created by serhii on 21.01.18.
 */
public class ServerSocket extends java.net.ServerSocket{

    private int port;

    public ServerSocket(int port) throws IOException {
        super(port);
    }

    // start server socket and wait for new connection
    // if we establish new connection, firstly read from client read until meet '\n' as the last character,
    // execute that command through a command line on the  host,
    // then return the response(must be in one line with '\n' in the end) of execution
    // if client send "shutdown-server" command,  we kill the serverSocket
    // if we send "os", the server must reply with either "LINUX" or "WIN"


    public void start() {
        //todo
        Socket client = null;
        try {
            client = this.accept();
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
//            bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            boolean next = false;
            while (client.isConnected()) {
                String command = clientCommand(client);
                String response = "";
                if (command.equals("null\n")) {
                    this.close();
                    next = false;
                } else if (command.equals("os\n")) {
                    response = System.getProperty("os.name").toUpperCase().concat("\n");
                    writer.println(response);
                    writer.flush();
                    next = true;
                } else if (command.equals("shutdown-server\n")) {
                    response = "Server shutdown";
                    this.close();

                    writer.println(response);
                    next = false;
                } else if (command.equals("help\n")) {
//            System.out.println("Creating Process...");
                    Process process = Runtime.getRuntime().exec("man -h\n");

                    BufferedReader processReader = new BufferedReader(
                            new InputStreamReader(process.
                                    getInputStream()));

                    String line = "";
                    StringBuilder result = new StringBuilder();

                    while ((line = processReader.readLine()) != null) {

                        result.append(line);
                    }

                    writer.println(result.toString());

                    next = true;
                } else {
//            System.out.println("Creating Process...");
                    Process process = Runtime.getRuntime().exec("/bin/sh -c\n" + command);

                    BufferedReader processReader = new BufferedReader(
                            new InputStreamReader(process.
                                    getInputStream()));

                    String line = "";
                    StringBuilder result = new StringBuilder();

                    while ((line = processReader.readLine()) != null) {

                        result.append(line);
                    }

                    writer.println(result.toString());

                    next = true;
                    if (!next) {
                        client.shutdownInput();
                        client.shutdownOutput();
                        client.close();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean commandHandler (String command, PrintWriter writer) throws IOException {

        String response = "";
        if (command == null || command.equals("null\n")) {
            this.close();
            return false;
        }
        else if (command.equals("os\n")) {
            response = System.getProperty("os.name").toUpperCase().concat("\n");
            writer.println(response);
            writer.flush();
            return true;
        }

        else if (command.equals("shutdown-server\n")) {
            response = "Server shutdown";
            this.close();

            writer.println(response);
            return false;
        }
        else if (command.equals("help\n")) {
//            System.out.println("Creating Process...");
            Process process = Runtime.getRuntime().exec("man -h\n");

            BufferedReader processReader = new BufferedReader(
                    new InputStreamReader(process.
                            getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = processReader.readLine()) != null) {

                result.append(line);
            }

            writer.println(result.toString());

            return true;
        }
        else {
//            System.out.println("Creating Process...");
            Process process = Runtime.getRuntime().exec("/bin/sh -c\n"+command);

            BufferedReader processReader = new BufferedReader(
                    new InputStreamReader(process.
                            getInputStream()));

            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = processReader.readLine()) != null) {

                result.append(line);
            }

            writer.println(result.toString());

            return true;
        }


    }
    private String clientCommand(Socket client) throws IOException {
        InputStream is = client.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line = bufferedReader.readLine();
//        System.out.println(line);
        return line + "\n";
    }






}
