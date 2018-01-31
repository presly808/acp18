package sockets;

import java.io.*;
import java.util.Date;

public class CmdExecutor {

    private Runtime runtime;

    public CmdExecutor() {
        this.runtime = Runtime.getRuntime();
    }

    /**
     * Executes Console command in Runtime.
     *
     * @param command String console command.
     * @return result of command execution.
     */
    public String exec(String command) {

        String result = ownCommandsHandler(command);
        if (!result.isEmpty()) {
            return result;
        }

        return osHandler(command);

    }

    public Runtime getRuntime() {
        return runtime;
    }

    private String osHandler(String cmd) {

        String result = "";
        Process process = null;

        try {

            process = runtime.exec(cmd);
            process.waitFor();

            result = read(process.getInputStream());

        } catch (IOException e) {
            result = "Something gone wrong! - " + e.toString();
        } catch (NullPointerException e){
            try {
                result = read(process.getErrorStream());
            } catch (IOException e1) {
                result += e1.toString();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            result = "Command didn't execute! - " + e.toString();
        }

        return result;
    }

    private String read(InputStream inputStream) throws IOException {

        String result = "", line;

        BufferedReader bufReadResult = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufReadResult.readLine()) != null) {
            result += line + "\n";
        }
        return result;
    }

    private static String ownCommandsHandler(String cmd) {

        if (cmd.isEmpty()) {
            return "enter command!";
        }

        if (cmd.equalsIgnoreCase("os")) {
            return getOSVerion();
        }

        if (cmd.equalsIgnoreCase("date")) {
            return (new Date(System.currentTimeMillis())).toString();
        }

        if (cmd.equalsIgnoreCase("cd")) {
            return new File("sockets").getAbsolutePath();
        }

        return "";
    }

    public static String getOSVerion() {

        String os = System.getProperty("os.name").toLowerCase();

        if (os.indexOf("win") >= 0) {
            os = "WIN";
        } else if (os.indexOf("lin") >= 0) {
            os = "LINUX";
        }

        return os;
    }


}
