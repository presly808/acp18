package multithreading.bankExample;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private final int[] accounts;
    private Lock lock;
    private Condition condition;

    public Bank(int n, int initialBalance) {
        lock = new ReentrantLock();
        condition = lock.newCondition();
        accounts = new int[n];
        for (int i = 0; i < accounts.length; i++){
            accounts[i] = initialBalance;
        }
    }



    public void transfer(int from, int to, int amount) {
        try{
            lock.lock();
            while(accounts[from] < amount) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            accounts[from] -= amount;
            accounts[to] += amount;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getTotalBalance() {
        int sum = 0;
        for (int a: accounts){
            sum += a;
        }
        return sum;
    }

    public int size(){
        return accounts.length;
    }
}
