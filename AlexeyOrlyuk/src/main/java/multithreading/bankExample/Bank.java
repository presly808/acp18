package multithreading.bankExample;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private double[] accounts;

    private Lock lock;
    private Condition discardCondition;

    /**
     * Constructor.
     *
     * @param n initial number of accounts in this Bank.
     * @param initialBalance initial balance for each created account in this Bank.
     */
    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++){
            accounts[i] = initialBalance;
        }

        lock = new ReentrantLock(true);
        discardCondition = lock.newCondition();
    }

    /**
     * Carries out inside-Bank transaction.
     *
     * @param from index of account-sender in accounts' array.
     * @param to index of account-receiver in accounts' array.
     * @param amount funds amount, which will be discounted from sender's balance
     *               and will be added to receiver's balance.
     */
    public void transfer(int from, int to, double amount) {

        try {

            lock.lock();

            while (accounts[from] < amount) {
                try {
                    System.out.print(Thread.currentThread());
                    System.out.printf(" Can't send %10.2f from %d to %d (to small balance).\n", amount, from, to);
                    discardCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());

            discardCondition.signalAll();

        } finally {
            lock.unlock();
        }

    }

    /**
     * Shows total amount of this Bank.
     *
     * @return sum of amounts of all accounts in this Bank.
     */
    private double getTotalBalance() {
        double sum = 0;
        for (double a: accounts){
            sum += a;
        }
        return sum;
    }

    /**
     * Shows this Bank's "size".
     *
     * @return number of accounts (length of accounts Array).
     */
    public int size(){
        return accounts.length;
    }
}
