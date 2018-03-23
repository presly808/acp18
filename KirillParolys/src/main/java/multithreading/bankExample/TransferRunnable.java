package multithreading.bankExample;

public class TransferRunnable implements Runnable {
    private Bank bank;
    private int fromAccount;
    private double maxAmount;
    private int operationsCount;

    public TransferRunnable(Bank b, int from, double max, int operationsCount) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.operationsCount = operationsCount;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
