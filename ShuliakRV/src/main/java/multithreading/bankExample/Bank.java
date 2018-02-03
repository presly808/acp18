package multithreading.bankExample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private final int[] accounts;
    private ReentrantReadWriteLock rwl;
    private Lock readLock;
    private Lock writeLock;

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
        rwl = new ReentrantReadWriteLock();
        readLock = rwl.readLock();
        writeLock = rwl.writeLock();
    }

    public void transfer(int from, int to, int amount) {

        writeLock.lock();
        try {
            if (accounts[from] < amount) {
                return;
            }
            accounts[from] -= amount;
            accounts[to] += amount;
        }
        finally {
            writeLock.unlock();
        }
    }

    public int getTotalBalance() {

        int sum = 0;
        readLock.lock();
        try {
            for (int a : accounts) {
                sum += a;
            }
        }
        finally {
            readLock.unlock();
        }
        return sum;
    }

    public int size() {
        return accounts.length;
    }
}
