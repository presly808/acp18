package exclude;

/**
 * Created by serhii on 27.01.18.
 */
public class TestDeamon {

    public static void main(String[] args) {
        Thread hello = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("hello");
                }
            }
        });

        hello.setDaemon(false);
        hello.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hello.interrupt();

    }
}
