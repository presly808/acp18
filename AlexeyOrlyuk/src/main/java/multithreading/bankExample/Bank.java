package multithreading.bankExample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private double[] accounts;
    private Lock lock;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++){
            accounts[i] = initialBalance;
        }

        lock = new ReentrantLock(true);
    }

    public void transfer(int from, int to, double amount) {

        lock.lock();

        if (accounts[from] < amount) {
            lock.unlock();
            return;
        }
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());

        lock.unlock();

    }

    private double getTotalBalance() {
        double sum = 0;
        for (double a: accounts){
            sum += a;
        }
        return sum;
    }

    public int size(){
        return accounts.length;
    }
}
