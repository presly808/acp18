package multithreading.bankExample;

/**
 * Created by Anna on 12.09.2016.
 */
public class TransferRunnable implements Runnable {
    private Bank bank;
    private int fromAccount;
    private double maxAmount;
    private int DELAY = 10;

    private int operationCount;

    public TransferRunnable(Bank b, int from, double max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    public TransferRunnable(Bank b, int from, double max, int operationCount) {
        bank = b;
        fromAccount = from;
        maxAmount = max;

        this.operationCount = operationCount;
    }

    @Override
    public void run() {

        try {
            int i = 0;
            while (true){
                int toAccount = (int) (bank.size()*Math.random());
                double amount = maxAmount*Math.random();
                bank.transfer(fromAccount, toAccount, amount);
                Thread.sleep((int) (DELAY*Math.random()));

                if (operationCount > 0 && i++ >= operationCount) {
                    break;
                }
            }
        }
        catch (InterruptedException e){}


    }
}
