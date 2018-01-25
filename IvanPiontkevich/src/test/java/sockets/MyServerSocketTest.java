package sockets;

import org.junit.*;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by serhii on 21.01.18.
 */
public class MyServerSocketTest {

    public static final int PORT = 9999;
    public static final String LOCALHOST = "localhost";

    @BeforeClass
    public static void startServer() throws Exception {
        MyServerSocket myServerSocket = new MyServerSocket(PORT);
        CompletableFuture.runAsync(myServerSocket::start);
        Thread.sleep(2000);
    }

    @Test
    public void testSimpleCommand() throws IOException, InterruptedException {
        String response = sendReq("localhost", PORT, "os");
        assertThat(response, anyOf(equalTo("LINUX"),equalTo("WIN")));


        String secResp = null;
        if(Objects.equals(response, "LINUX")){
            secResp = sendReq("localhost", PORT, "pwd");
        } else if(Objects.equals(response, "WIN")){
            secResp = sendReq("localhost", PORT, "cd");
        }

        assertThat(secResp, containsString("IvanPiontkevich"));

    }

    @Test
    public void testDate() throws IOException, InterruptedException {
        String response = sendReq("localhost", PORT, "date");
        System.out.println(response);
        assertNotNull(response);
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