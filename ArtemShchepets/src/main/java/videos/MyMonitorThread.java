package videos;

public class MyMonitorThread extends Thread {

    public static void main(String[] args) {
        // get current thread
        Thread main = Thread.currentThread();
        System.out.println(main.getName());

        MyThread myThread = new MyThread("poor info thread");
        myThread.setDaemon(true);
        myThread.start();

        for (int i = 0; i < 10; i++) {
            System.out.printf("main working %d\n", i);
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyThread extends Thread {

    private String threadDescription;

    public MyThread(String threadDescription) {
        this.threadDescription = threadDescription;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.printf("My work %s, %s, %s\n", getId(), getName(), getState());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
            }
        }
    }
}
