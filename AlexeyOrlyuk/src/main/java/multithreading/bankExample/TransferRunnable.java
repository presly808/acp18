package multithreading.bankExample;

import multithreading.bankExample.bank.Bank;

/**
 * Bank Client entity. Can be also named as Bank Runner.
 *
 * @author Anna
 * @author alex323glo
 * @version 1.1
 */
public class TransferRunnable implements Runnable {
    private Bank bank;
    private int fromAccount;
    private double maxAmount;

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

        int i = 0;
            while (true){
                int toAccount = (int) (bank.size() * Math.random());
                double amount = maxAmount*Math.random();
                bank.transfer(fromAccount, toAccount, amount);

                if (operationCount > 0 && i++ >= operationCount) {
                    break;
                }
            }

    }
}
