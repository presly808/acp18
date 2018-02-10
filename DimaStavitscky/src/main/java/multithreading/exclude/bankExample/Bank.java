package multithreading.exclude.bankExample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private final int[] accounts;
    private Lock lock;
    Condition condition;
    private BlockingQueue<Integer> blockingQueue;

    public Bank(int n, int initialBalance) {
        blockingQueue = new ArrayBlockingQueue<Integer>(10);

        lock = new ReentrantLock();
        condition = lock.newCondition();

        accounts = new int[n];
        for (int i = 0; i < accounts.length; i++){
            accounts[i] = initialBalance;
        }
    }

    public void transfer(int from, int to, int amount) {

        try {
            lock.lock();

            while (accounts[from] < amount) {
                try {
                    /*System.out.printf("\n" + Thread.currentThread() + "waiting with amount %d \n", amount);*/
                    condition.await(1, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    System.out.println("release");
                    System.out.println(e.getMessage());
                }
            }

            /*System.out.print(Thread.currentThread() + " " + accounts[from]);*/
            accounts[from] -= amount;
            /*System.out.printf(" %d from %d to %d", amount, from, to);*/
            accounts[to] += amount;
            /*System.out.printf(" Total Balance: %d n", getTotalBalance());*/
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
/*
class Container implements IContainer {
    private volatile int count;

    private Lock lock = new ReentrantLock(); // (true) - queue, (false) - set
    private Condition producerCondition = lock.newCondition();
    private Condition consumerCondition = lock.newCondition();


    public synchronized void inc() { // lock this

        try{
            lock.lock();
            System.out.printf("inc %d thread id %d, takes monitor\n", count, Thread.currentThread().getId());

            while (count != 0) {
                try {
                    System.out.printf("inc %d thread id %d, wait\n", count, Thread.currentThread().getId());
                    producerCondition.await(); // release monitor
                    System.out.printf("inc %d thread id %d, awake\n", count, Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count++;
            consumerCondition.signal();
            System.out.printf("inc %d thread id %d, release monitor\n", count, Thread.currentThread().getId());

        } finally {
            lock.unlock();
        }
    }*/
