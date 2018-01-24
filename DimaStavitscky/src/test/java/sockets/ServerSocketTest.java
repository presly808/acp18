package sockets;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by serhii on 21.01.18.
 */
public class ServerSocketTest {

    public static final int PORT = 9999;
    public static final String LOCALHOST = "localhost";

    @BeforeClass
    public static void startServer() throws Exception {
        MyServerSocket serverSocket = new MyServerSocket(PORT);
        CompletableFuture.runAsync(serverSocket::start);
    }

    private static String sendReq(String host, int port, String message) {

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
        sendReq(LOCALHOST, PORT, "shutdown-server");
    }

    @Test
    public void testSimpleCommand() throws IOException {
        String response = sendReq("localhost", PORT, "os");
        assertThat(response, anyOf(equalTo("LINUX"), equalTo("WIN")));


        String secResp = null;
        if (Objects.equals(response, "LINUX")) {
            secResp = sendReq("localhost", PORT, "pwd");
        } else if (Objects.equals(response, "WIN")) {
            secResp = sendReq("localhost", PORT, "cd");
        }

        assertThat(secResp, containsString("DimaStavitscky"));

    }

    @Test
    public void testDate() throws IOException {
        String response = sendReq("localhost", PORT, "ls");
        assertThat(response, containsString("build.gradle"));
    }


}