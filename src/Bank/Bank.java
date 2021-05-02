package Bank;

import java.util.concurrent.locks.Lock;


public class Bank {
    private int TO_STRING_FREQUENCY = 0;
    private int TEST_FREQUENCY = 100;
    private Lock lock;
    private long transactionCount = 0L;
    private int deviationCount = 0;
    private int initialbalance;
    private Account[] accounts;
    private boolean debug = false;
    private int testCount;

    Bank(int accountAmount, int initalBalance, boolean debug) {
        this.initialbalance = initalBalance;
        this.debug = true;
        this.accounts = new Account[accountAmount];
        for (int i = 0; i < accountAmount; i++) {
           this.accounts[i] = new Account(this.initialbalance, i);
        }
        System.out.println(accounts.length);
    }

    void transfer(int fromID,int toID, int amount) throws InterruptedException {
                accounts[fromID].withdraw(amount);
                accounts[toID].deposit(amount);
                transactionCount ++;
                int currentTotal = 0;
                for (int i = 0; i < this.accounts.length; i++) {
                    currentTotal += accounts[i].getBalance();
                }

                if (currentTotal != this.initialbalance * accounts.length) {
                    this.deviationCount++;
                }
    }

    void test() {
        int totalSum = accounts.length * this.initialbalance;
        int accountSum = 0;
        System.out.println("Expected sum: " + totalSum + "\nCurrent sum: " + accountSum);
    }

    public String toString(){
        //TODO
        return "";
    }

    public int numberOfAccounts() {
        return this.accounts.length;
    }

    public long getTransactionCount() {
        return transactionCount;
    }

    public int getDeviationCount() {
        return deviationCount;
    }

    public double getErrorPercentage() {
        return (1.0 * getDeviationCount() / getTransactionCount()) * 100;
    }

}




