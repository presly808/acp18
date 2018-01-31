package multithreading.bankExample;

import multithreading.bankExample.bank.Bank;
import multithreading.bankExample.bank.BreakTransactionBank;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by serhii on 27.01.18.
 */
public class TransferRunnableTest {

    public static final int NACCOUNTS = 100;
    public static final int INITIAL_BALANCE = 1_000;
    public static final int OPERATIONS_COUNT = 10_000;
    private List<Thread> threadList;

    @Test
    public void mainTest (){
        threadList = new ArrayList<>();

        Bank b = new BreakTransactionBank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE,OPERATIONS_COUNT);
            Thread t = new Thread(r);
            threadList.add(t);
            t.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(NACCOUNTS * INITIAL_BALANCE, b.getTotalBalance(), 0.000_001);
    }


}