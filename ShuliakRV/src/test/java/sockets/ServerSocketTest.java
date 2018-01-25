package sockets;

import org.hamcrest.CoreMatchers;
import org.junit.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by serhii on 21.01.18.
 */
public class ServerSocketTest {

    public static final int PORT = 9999;
    public static final String LOCALHOST = "localhost";

    @BeforeClass
    public static void startServer() throws Exception {
        Server serverSocket = new Server(PORT);
        CompletableFuture.runAsync(serverSocket::start);
    }

    @Test
    public void testSimpleCommand() throws IOException {
        String response = sendReq("localhost", PORT, "os");
        assertThat(response, anyOf(equalTo("LINUX"),equalTo("WIN")));


        String secResp = null;
        if(Objects.equals(response, "LINUX")){
            secResp = sendReq("localhost", PORT, "pwd");
        } else if(Objects.equals(response, "WIN")){
            secResp = sendReq("localhost", PORT, "cd");
        }

        assertThat(secResp, containsString("sockets"));

    }

    @Test
    public void testDate() throws IOException {
        String response = sendReq("localhost", PORT, "help");
        assertThat(response, containsString("cd"));
    }

    private static String sendReq(String host, int port, String message){

        try (Socket socket = new Socket(host, port);
             BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {

            printWriter.println(message);

            printWriter.flush();

            return inputStreamReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @AfterClass
    public static void shutDownServer() throws Exception {
        sendReq(LOCALHOST,PORT,"shutdown-server");
    }


}