package Bank;

import java.util.Random;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public class AccountThread extends Thread {
    private Bank bank;
    private boolean debug;
    private int accountIndex;
    private int maxTransferAmount;
    private Random random = new Random();

    AccountThread(Bank bank, int accountIndex, int maxTransferAmount, boolean debug) {
        this.bank = bank;
        this.accountIndex = accountIndex;
        this.maxTransferAmount = maxTransferAmount;
        this.debug = debug;
    }

    @Override
    public void run() {
        while (!interrupted()) {
                int toAccount = random.nextInt(this.bank.numberOfAccounts());
                int amount = (int) (this.maxTransferAmount * Math.random() / 10);
                try {
                    bank.transfer(accountIndex, toAccount, amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Thread.interrupted()) {
                    System.out.println("Thread STOPPED BY INTERRUPT");
                }
        }
    }
}
