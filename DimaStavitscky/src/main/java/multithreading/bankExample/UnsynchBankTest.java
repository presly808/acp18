package multithreading.bankExample;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anna on 12.09.2016.
 */
public class UnsynchBankTest {

    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 1000;

    public static void main(String[] args) {
        /*ExecutorService service  = Executors.newFixedThreadPool(NACCOUNTS);*/
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            /*service.submit(new TransferRunnable(bank, i, INITIAL_BALANCE));*/
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE, 10000);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
    /*Future future = service.submit(new IncThread(container, operationCount));

    ExecutorService service = Executors.newFixedThreadPool(20);

    private Lock lock = new ReentrantLock(); // (true) - queue, (false) - set
*/