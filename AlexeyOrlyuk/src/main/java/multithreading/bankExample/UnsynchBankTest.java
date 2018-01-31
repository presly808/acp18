package multithreading.bankExample;

import multithreading.bankExample.bank.Bank;
import multithreading.bankExample.bank.BreakTransactionBank;
import multithreading.bankExample.bank.WaitTransactionBank;

/**
 * Created by Anna on 12.09.2016.
 *
 * Main test (demonstration) of Bank and Bank Client (TransferRunnable).
 *
 * @author Anna
 * @author alex323glo
 * @version 1.1
 */
public class UnsynchBankTest {

    public static final int NACCOUNTS = 100;
    public static final int INITIAL_BALANCE = 1000;

    public static void main(String[] args) {

        runSimpleDemonstration(NACCOUNTS, INITIAL_BALANCE);

//        runWaitDemonstration(10, 1000);
//
//        runDeadLockDemonstration(10, 1000);

    }

    private static void runSimpleDemonstration(int accountsNum, double initialBalance) {
        Bank b = new BreakTransactionBank(accountsNum, initialBalance);
        for (int i = 0; i < accountsNum; i++) {
            TransferRunnable r = new TransferRunnable(b, i, initialBalance, 100);            // !!!
            Thread t = new Thread(r);
            t.start();
        }
    }

    private static void runWaitDemonstration(int accountsNum, double initialBalance) {
        Bank b = new WaitTransactionBank(accountsNum, initialBalance);
        for (int i = 0; i < accountsNum; i++) {
            TransferRunnable r = new TransferRunnable(b, i, initialBalance, 100);            // !!!
            Thread t = new Thread(r);
            t.start();
        }
    }

    private static void runDeadLockDemonstration(int accountsNum, double initialBalance) {
        Bank b = new WaitTransactionBank(accountsNum, initialBalance);
        for (int i = 0; i < accountsNum; i++) {
            TransferRunnable r = new TransferRunnable(b, i, initialBalance * 2);    // !!!
            Thread t = new Thread(r);
            t.start();
        }
    }
}
