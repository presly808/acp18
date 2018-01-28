package multithreading.bankExample;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private final double[] accounts;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++){
            accounts[i] = initialBalance;
        }
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {

        synchronized (this) {
            if (accounts[from] < amount) {
                Thread.currentThread().wait();
                //return;
            }
            Thread.currentThread().notifyAll();
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());

        }
    }

    private double getTotalBalance() {
        double sum = 0;
        for (double a: accounts){
            sum += a;
        }
        return sum;
    }

    public int size(){
        return accounts.length;
    }
}
