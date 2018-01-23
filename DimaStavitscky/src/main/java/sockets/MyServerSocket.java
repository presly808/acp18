package sockets;

import jdk.nashorn.internal.ir.Terminal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by serhii on 21.01.18.
 */
public class MyServerSocket {

    private int port;
    private int id;

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
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server was ran");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.printf("new connection № %d", id++);

                InputStream is = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));


                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                PrintWriter pw = new PrintWriter(client.getOutputStream());
                if (line.equals("os")) {
                    pw.print("LINUX");

                } else if (line.equals("shutdown-server")) {
                    System.out.println();

                } else {
                    Process p = Runtime.getRuntime().exec(line);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));
                    String lineTerm = null;
                    System.out.println("first");
                    pw.write(in.read());
                    System.out.println("second");
                    pw.write(String.valueOf(p.getInputStream()));
                    System.out.println("the third");
                    while ((lineTerm = in.readLine()) != null) {
                        System.out.println(lineTerm);
                        /*pw.print(lineTerm);*/
                    }
                }

                pw.flush();
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/* public class Server {

    private int id;
    private List<String> list;

    public Server() {
        list = new ArrayList<>();
    }

    public void run() {
        try {
            MyServerSocket serverSocket = new MyServerSocket(8080);
            System.out.println("server was run");

            while (true) {
                Socket client = serverSocket.accept(); // blocking method

                String clientInfo = String.format("count id %s, address %s, port %s"
                        , id++
                        , client.getInetAddress()
                        , client.getPort());

                System.out.println(clientInfo);

                list.add(clientInfo);

                PrintWriter pw = new PrintWriter(client.getOutputStream());

                pw.printf("Hello from server, Your info %s, time %s\n"
                        , clientInfo
                        , new Date());
                pw.flush();
                pw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
public class Run {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.1.1", 8080);

        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null){
            sb.append(line).append("\n");
        }

        System.out.println(sb.toString());




    }
}
 */