package multithreading.bankExample.bank;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concurrent Bank entity.
 *
 * @author Anna
 * @author alex323glo
 * @version 1.1
 */
public abstract class Bank {

    protected double[] accounts;

    protected Lock lock;

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
    }

    /**
     * Carries out inside-Bank transaction.
     *
     * @param from index of account-sender in accounts' array.
     * @param to index of account-receiver in accounts' array.
     * @param amount funds amount, which will be discounted from sender's balance
     *               and will be added to receiver's balance.
     */
    public abstract void transfer(int from, int to, double amount);

    /**
     * Shows total amount of this Bank.
     *
     * @return sum of amounts of all accounts in this Bank.
     */
    public double getTotalBalance() {
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
