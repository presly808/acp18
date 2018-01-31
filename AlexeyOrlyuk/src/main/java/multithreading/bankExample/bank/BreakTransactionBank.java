package multithreading.bankExample.bank;

/**
 * First implementation (version) of Bank, which breaks
 * transaction if it can't be served by sender (too small balance).
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see Bank
 */
public class BreakTransactionBank extends Bank {
    /**
     * Constructor.
     *
     * @param n              initial number of accounts in this Bank.
     * @param initialBalance initial balance for each created account in this Bank.
     */
    public BreakTransactionBank(int n, double initialBalance) {
        super(n, initialBalance);
    }

    /**
     * Carries out inside-Bank transaction.
     *
     * @param from   index of account-sender in accounts' array.
     * @param to     index of account-receiver in accounts' array.
     * @param amount funds amount, which will be discounted from sender's balance
     */
    @Override
    public void transfer(int from, int to, double amount) {
        try {

            lock.lock();

            if (accounts[from] < amount) {
                System.out.print(Thread.currentThread());
                System.out.printf(" Can't send %10.2f from %d to %d (to small balance).\n", amount, from, to);
                return;
            }

            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());

        } finally {
            lock.unlock();
        }
    }
}
