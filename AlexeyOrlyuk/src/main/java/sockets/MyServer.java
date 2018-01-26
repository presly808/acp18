package sockets;

import java.io.*;
import java.net.Socket;

/**
 * Single-thread Server, based on ServerSockets.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see ServerSocket
 */
public class MyServer {

    /**
     * Starts server under entered port.
     *
     * @param serverPort target port for Server's work.
     * @throws IOException if some I/O actions (I/O streams,
     * or console commands execution in Runtime) are failed.
     *
     * @see IOException
     */
    public void runServer(int serverPort) throws IOException {
        java.net.ServerSocket serverSocket = new java.net.ServerSocket(serverPort);

        System.out.printf("*** Server started *** \nInfo: %s \n***\n", serverSocket);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.printf("New connection: %s\n", client);

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(client.getOutputStream());

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                System.out.printf("Client[addr:%s; port:%s; local_port:%s]: %s\n",
                        client.getInetAddress(), client.getPort(), client.getLocalPort(), line);

                switch (line.trim()) {
                    case "shutdown-server":
                        System.out.printf("Client closed connection. Client's info: %s\n", client);
                        System.out.println("*** Shutting down server. ***");
                        reader.close();
                        writer.close();
                        client.close();
                        serverSocket.close();
                        return;

                    case "os":
                        String osName = System.getProperty("os.name").toUpperCase();
                        writer.println(osName.equals("WINDOWS") ? osName.substring(0, 3) : osName);
                        writer.flush();
                        break;

                    default:
                        try {
                            String commandExecutionResult = executeRuntimeCommand(line);

                            writer.println(commandExecutionResult);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }

            reader.close();
            writer.close();
            client.close();
        }
    }

    /**
     * Executes Console command in Runtime.
     *
     * @param command String console command.
     * @return result of command execution.
     * @throws IOException if ca't execute console command in Runtime.
     */
    private String executeRuntimeCommand(String command) throws IOException {
        if (command == null) {
            System.err.printf("Runtime: Error! Inputed command is null.");
            return null;
        }

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command, null,
                new File(this.getClass().getResource("/sockets").getFile()));

        return readFromInputStream(process.getInputStream());
    }

    /**
     * Collects data from InputStream to String line.
     *
     * @param stream target InputStream.
     * @return result String line.
     *
     * @see InputStream
     */
    private static String readFromInputStream(InputStream stream) {
        if (stream == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder resultBuilder = new StringBuilder();

        try {

            String line = reader.readLine();
            while (line != null) {
                resultBuilder.append(line).append('\n');
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultBuilder.toString();
    }

}