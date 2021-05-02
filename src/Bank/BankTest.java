package Bank;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankTest {
    private static boolean DEBUG = false;
    private static int ACCOUNT_AMOUNT = 10;
    private static int INITIAL_BALANCE = 10000;
    //private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        Bank bank = new Bank(ACCOUNT_AMOUNT, INITIAL_BALANCE, DEBUG);
        System.out.println(bank.numberOfAccounts());
        for (int i = 0; i < ACCOUNT_AMOUNT; i++) {
            AccountThread thread = new AccountThread(bank, i, INITIAL_BALANCE, DEBUG);
            thread.setPriority(Thread.NORM_PRIORITY + i % 2);
            thread.start();
        }
        while (true){
                bank.test();
        }
    }
}
