package multithreading.bankExample;

/**
 * Created by Anna on 12.09.2016.
 */
public class Bank {

    private final int[] accounts;

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        for (int i = 0; i < accounts.length; i++){
            accounts[i] = initialBalance;
        }
    }

    public synchronized void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) {return;}
        accounts[from] -= amount;
        accounts[to] += amount;
    }

    public int getTotalBalance() {
        int sum = 0;
        for (int a: accounts){
            sum += a;
        }
        return sum;
    }

    public int size(){
        return accounts.length;
    }
}
