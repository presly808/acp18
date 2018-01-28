package multithreading.bankExample;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private final double[] accounts;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++){
            accounts[i] = initialBalance;
        }
    }

    public void transfer(int from, int to, double amount) {
        /*ReadWriteLock rwLock = new ReentrantReadWriteLock();*/
        Lock lock = new ReentrantLock();
        Lock readLock = new ReentrantReadWriteLock().readLock();
        Lock writeLock = new ReentrantReadWriteLock().writeLock();

        Condition condition = lock.newCondition();

        try {
            lock.lock();

            while (accounts[from] < amount) {
                try {
                    System.out.printf("\n" + Thread.currentThread() + "waiting with amount %d \n", (int) amount);
                    condition.await();
                } catch (InterruptedException e) {
                    System.out.println("release");
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            /*writeLock.lock();*/
            System.out.print(Thread.currentThread() + " " + (int) accounts[from]);
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            condition.signalAll();

        } finally {
            lock.unlock();
            /*writeLock.unlock();*/
            /*readLock.unlock();*/
        }
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
