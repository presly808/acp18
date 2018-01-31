package multithreading.bankExample.bank;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * Second Bank implementation (version), which waits for account balance update
 * if transaction can't be served by sender (too small balance).
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see Bank
 */
public class WaitTransactionBank extends Bank {

    private Condition discardCondition;

    /**
     * Constructor.
     *
     * @param n              initial number of accounts in this Bank.
     * @param initialBalance initial balance for each created account in this Bank.
     */
    public WaitTransactionBank(int n, double initialBalance) {
        super(n, initialBalance);

        discardCondition = super.lock.newCondition();
    }

    /**
     * Carries out inside-Bank transaction.
     *
     * @param from   index of account-sender in accounts' array.
     * @param to     index of account-receiver in accounts' array.
     * @param amount funds amount, which will be discounted from sender's balance
     */
    @Override
    public void transfer(int from, int to, double amount)  {

        try {

            lock.lock();

            while (accounts[from] < amount) {
                try {
                    System.out.print(Thread.currentThread());
                    System.out.printf(" Can't send %10.2f from %d to %d (to small balance).\n", amount, from, to);
                    discardCondition.await(1, TimeUnit.SECONDS);
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
}
