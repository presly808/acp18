package multithreading.bankExample.exclude;

import multithreading.bankExample.Bank;
import multithreading.bankExample.TransferRunnable;

/**
 * Created by Anna on 12.09.2016.
 */
public class UnsynchBankTest {

    public static final int NACCOUNTS = 100;
    public static final int INITIAL_BALANCE = 1000;

    public static void main(String[] args) {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE, 10000);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
