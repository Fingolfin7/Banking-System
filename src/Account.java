import java.util.ArrayList;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
public class Account {
    private final String PASSWORD;
    private final String USERNAME;
    private final String ACCOUNT_NUM;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String username, String accountNum, String password){
        this.USERNAME = username;
        this.ACCOUNT_NUM = accountNum;
        this.PASSWORD = password;
        this.transactionHistory = new ArrayList<String>();
    }

    public Account(String username, String accountNum, String password, ArrayList<String> transactionHistory){
        this.USERNAME = username;
        this.ACCOUNT_NUM = accountNum;
        this.PASSWORD = password;
        this.transactionHistory = transactionHistory;
    }
    private static String getDateAndTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        return myDateObj.format(myFormatObj);
    }

    public void makeDeposit(double amount){
        if(amount > 0){
            balance += amount;
            String transaction = "Deposited: $" + String.format("%.2f", amount) +
                    "\nOn: " + getDateAndTime() +
                    "\nNew Balance: $" + String.format("%.2f", getBalance());
            transactionHistory.add(transaction);
            System.out.println(transaction);
        }
        else{
            System.out.println("Cannot deposit negative sums!");
        }
    }

    public void makeWithdrawal(double amount){
        if(amount > 0 && balance > amount){
            balance -= amount;
            String transaction = "Withdrew: $" + String.format("%.2f", amount) +
                    "\nOn: " + getDateAndTime() +
                    "\nNew Balance: $" + String.format("%.2f", getBalance());
            transactionHistory.add(transaction);
            System.out.println(transaction);
        }
        else if(balance < amount){
            System.out.println("Amount exceeds current balance! Balance: $ " + String.format("%.2f", getBalance()));
        }
        else{
            System.out.println("Cannot withdraw negative sums!");
        }
    }

    public Account transfer(Account toAcc, double amount){
        if(amount > 0 && balance > amount){
            balance -= amount;
            toAcc.receiveTransfer(ACCOUNT_NUM, USERNAME, amount);
            String transaction = "Transferred: $" + String.format("%.2f", amount) +
                    "\nTo Account: " + toAcc.getAccountNum() +
                    "\nHolder: " + toAcc.getUsername() +
                    "\nOn: " + getDateAndTime() +
                    "\nNew Balance: $" + String.format("%.2f", getBalance());
            transactionHistory.add(transaction);
            System.out.println(transaction);
        }
        else if(balance < amount){
            System.out.println("Amount exceeds current balance! Balance: $ " + String.format("%.2f", amount));
        }
        else{
            System.out.println("Cannot transfer negative sums!");
        }

        return toAcc;
    }
    private void receiveTransfer(String accountNum, String username, double amount){
        balance += amount;
        String transaction = "Received: $" + String.format("%.2f", amount) +
                "\nFrom Account: " + accountNum+
                "\nHolder: " + username +
                "\nOn: " + getDateAndTime();
        transactionHistory.add(transaction);
    }
    public boolean checkPassword(String password) {
        return PASSWORD.equals(password);
    }
    public String getUsername(){
        return USERNAME;
    }
    public String getAccountNum(){
        return ACCOUNT_NUM;
    }
    public double getBalance(){ return balance; }
    public void viewTransactionHistory() {
        if(transactionHistory.isEmpty()){
            System.out.println("No transaction history yet.");
        }
        else{
            for(String trans: this.transactionHistory){
                System.out.println(trans);
                System.out.println();
            }
        }
    }

}
