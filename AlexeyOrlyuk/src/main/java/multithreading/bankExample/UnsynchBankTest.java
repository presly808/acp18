package multithreading.bankExample;

/**
 * Created by Anna on 12.09.2016.
 */
public class UnsynchBankTest {

    public static final int NACCOUNTS = 100;
    public static final int INITIAL_BALANCE = 1000;

    public static void main(String[] args) {

//        runSimpleDemonstration(NACCOUNTS, INITIAL_BALANCE);

        runDeadLockDemonstration(10, 1000);

    }

    private static void runSimpleDemonstration(int accountsNum, double initialBalance) {
        Bank b = new Bank(accountsNum, initialBalance);
        for (int i = 0; i < accountsNum; i++) {
            TransferRunnable r = new TransferRunnable(b, i, initialBalance);            // !!!
            Thread t = new Thread(r);
            t.start();
        }
    }

    private static void runDeadLockDemonstration(int accountsNum, double initialBalance) {
        Bank b = new Bank(accountsNum, initialBalance);
        for (int i = 0; i < accountsNum; i++) {
            TransferRunnable r = new TransferRunnable(b, i, initialBalance * 2);    // !!!
            Thread t = new Thread(r);
            t.start();
        }
    }
}
