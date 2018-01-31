package multithreading.bankExample;

/**
 * Created by Anna on 12.09.2016.
 */
public class TransferRunnable implements Runnable {
    private Bank bank;
    private int fromAccount;
    private double maxAmount;
    private int operationsCount;
    private int DELAY = 10;

    public TransferRunnable(Bank b, int from, double max, int operationsCount) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.operationsCount = operationsCount;
    }

    @Override
    public void run() {

        for (int i = 0; i < operationsCount; i++) {
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random());
            bank.transfer(fromAccount, toAccount, amount);
        }
    }
}
