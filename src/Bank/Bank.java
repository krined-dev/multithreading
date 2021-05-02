package Bank;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.interrupted;

public class Bank {
    private int TO_STRING_FREQUENCY;
    private int TEST_FREQUENCY;
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
    }

    void transfer(int fromID,int toID, int amount) {
        while (true) {
            if (amount <= accounts[fromID].getBalance()) {
                accounts[fromID].withdraw(amount);
                accounts[toID].deposit(amount);
                break;
            }
        }
    }

    void test() {
        int totalSum = accounts.length * this.initialbalance;
        int accountSum = 0;
        for (int i = 0; i < accounts.length; i++) {
            accountSum += accounts[i].getBalance();
        }
        System.out.println("Expected sum: " + totalSum + "\nCurrent sum: " + accountSum);
    }

    public String toString(){
        //TODO
        return "";
    }

    int numberOfAccounts() {
        return accounts.length;
    }

    private long getTransactionCount() {
        return transactionCount;
    }

    private int getDeviationCount() {
        return deviationCount;
    }

    private double getErrorPercentage() {
        return (1.0 * getDeviationCount() / getTransactionCount()) * 100;
    }

}


class Account {
    private int balance;
    private int accountID;
    private Lock lock = new ReentrantLock();
    private Condition lockCondition = lock.newCondition();

    Account(int startBalance, int accountID) {
        this.balance = startBalance;
        this.accountID = accountID;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        lock.lock();
        if (amount <= this.balance) {
            this.balance -= amount;
        }
        lock.unlock();
    }

    public int getAccountID() {
        return this.accountID;
    }
    public int getBalance() {
        return this.balance;
    }

    public Lock getLock() {
        return lock;
    }
}

class AccountThreads extends Runnable{
    private Bank bank;
    private boolean debug;
    private int accountIndex;
    private int maxTransferAmount;
    private Random random;

    AccountThreads(Bank bank, int accountIndex, int maxTransferAmount, boolean debug) {
        this.accountIndex = accountIndex;
        this.maxTransferAmount = maxTransferAmount;
        this.debug = debug;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            for (int i = 0; i < 1000; i++) {
                int toAccount = random.nextInt(bank.numberOfAccounts());
                int amount = (int) (this.maxTransferAmount * Math.random() / 1000);
                bank.transfer(accountIndex, toAccount, amount);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}