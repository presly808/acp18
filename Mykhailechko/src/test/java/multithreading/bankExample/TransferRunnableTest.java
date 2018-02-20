package multithreading.bankExample;

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
    public static final int INITIAL_BALANCE = 1000;
    private List<Thread> threadList;

    @Test
    public void mainTest (){
        threadList = new ArrayList<>();

        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            //TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE,100000);
            //Thread t = new Thread(r);
            //threadList.add(t);
           // t.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //assertThat(b.getTotalBalance(), CoreMatchers.equalTo(100000));

    }


}