package exclude.week3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by serhii on 28.01.18.
 */
public class ReadWriteLockTest {


    public static final int CTHREAD = 100000;
    public static final int ITER = 2000000000;

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threadList = new ArrayList<>();

        long start = System.currentTimeMillis();

        Container container = new Container();
        for (int i = 0; i < CTHREAD; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < ITER; j++) {
                    container.getValue();
                }
            });
            threadList.add(thread);
        }


        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < ITER; j++) {
                    container.setValue((int) (Math.sin(0.87 * j) * Math.atan(0.3333)));
                }
            });
            threadList.add(thread);
        }


        for (Thread thread : threadList) {
            thread.join();
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);


    }

    static class Container {

        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = readWriteLock.readLock();
        private Lock writeLock = readWriteLock.writeLock();

        private int value = 0;

        public Container() {
        }

        public int getValue() {
            try {
                readLock.lock();
                return value;
            } finally {
                readLock.unlock();
            }
        }

        public void setValue(int value) {
            try {
                writeLock.lock();
                this.value = value;
            } finally {
                writeLock.unlock();
            }
        }
    }

}
