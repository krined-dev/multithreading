package Bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int balance;
    private int accountID;
    private Lock lock = new ReentrantLock();
    private Condition lockCondition = lock.newCondition();

    Account(int startBalance, int accountID) {
        this.balance = startBalance;
        this.accountID = accountID;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
            lockCondition.signalAll();
        }
        finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount) throws InterruptedException {
        lock.lock();
        while (balance < amount) {
            lockCondition.await();
        }
        this.balance -= amount;
        lock.unlock();
        //System.out.println("Account ID: " + accountID + " withdrew: " + amount + "\nBalance: " + this.balance);
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
