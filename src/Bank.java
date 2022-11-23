import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.lang.System.*;

public class Bank {
    private final DatabaseManager BANKDB;
    private final HashMap<String, Account> CUSTOMER_ACCOUNTS;
    private double totalAccountBalances;

    Bank(){
        BANKDB = new DatabaseManager();
        CUSTOMER_ACCOUNTS = BANKDB.loadData();

        if(CUSTOMER_ACCOUNTS != null && !CUSTOMER_ACCOUNTS.isEmpty()){
            for(Account acc: CUSTOMER_ACCOUNTS.values()) {
                totalAccountBalances += acc.getBalance();
            }
        }
        else{
            totalAccountBalances = 0.0;
        }
    }
    private String makeAccountNumber(){
        char [] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String accNumber;

        do{
            int n = (int)Math.floor( Math.random() * 100000 + 1 );
            NumberFormat formatter = new DecimalFormat("00000");
            String number = formatter.format(n);

            int rnd = new Random().nextInt(alphabet.length);
            accNumber = alphabet[rnd] + number;
        }while(CUSTOMER_ACCOUNTS.containsKey(accNumber));

        return accNumber;
    }

    private Account getAccount(String accountNum){
        return CUSTOMER_ACCOUNTS.getOrDefault(accountNum, null);
    }

    private Account makeNewAccount(String name, String password){
        String accountNumber = makeAccountNumber();
        Account newAcc = new Account(name, accountNumber, password);
        CUSTOMER_ACCOUNTS.put(accountNumber, newAcc);

        return newAcc;
    }

    private void DeleteAccount(String accountNum, String password){
        if(getAccount(accountNum).checkPassword(password)){
            Account deleted = CUSTOMER_ACCOUNTS.remove(accountNum);
            System.out.println("Deleted Account: " + deleted.getAccountNum() + "\nHolder: " + deleted.getUsername());
        }
        else if(getAccount(accountNum) == null){
            System.out.println("Account Number: " + accountNum + " does not exist.");
        }
        else{
            System.out.println("Incorrect password!");
        }
    }

    private Account loginScreen(Scanner input){
        int optn = -1;
        System.out.print("Would you like to:\n1. Login,\n2. Create an Account.\n>");
        try{
            // better than .nextInt()
            // ref: https://stackoverflow.com/questions/26586489/integer-parseintscanner-nextline-vs-scanner-nextint
            optn = Integer.parseInt(input.nextLine());
        }
        catch (NumberFormatException e){};

        if(optn == 1){
            System.out.print("Account Number: ");
            String accNum = input.nextLine();
            System.out.print("Account Password: ");
            String password = input.nextLine();

            if(getAccount(accNum) == null){
                System.out.println("\nAccount Number: " + accNum + " does not exist.");
                return null;
            }
            if(getAccount(accNum).checkPassword(password)){
                System.out.println("\nLogin Successful!\n");
                return getAccount(accNum);
            }
            else{
                System.out.println("\nIncorrect password!");
                return null;
            }
        }
        else if(optn == 2){
            System.out.print("Enter you full name: ");
            String fullName = input.nextLine();
            System.out.print("Enter a new password: ");
            String password = input.nextLine();

            Account newAcc = makeNewAccount(fullName, password);
            System.out.println("\nAccount created successfully!\nNew Account Number: "
                    + newAcc.getAccountNum() + "\n");
            return newAcc;
        }
        else{
            System.out.println("Invalid option!\n");
            return null;
        }
    }

    public void menu(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Unbankrupt Yourself!\n");
        Account acc = loginScreen(input);
        input.nextLine();

        if(acc == null){
            return;
        }

        while(true){
            int optn = -1;
            System.out.println("What would you like to do?");
            System.out.println();
            System.out.println("1. Check your balance");
            System.out.println("2. Make a deposit");
            System.out.println("3. Make a withdrawal");
            System.out.println("4. Make a transfer");
            System.out.println("5. View account statement");
            System.out.println("6. Exit");
            System.out.print(">");
            try{
            optn = Integer.parseInt(input.nextLine());
            }
            catch (NumberFormatException e){};

            System.out.println();

            switch (optn){
                case 1:
                    System.out.println("Your Current Balance is: $" + acc.getBalance());
                    input.nextLine();
                    break;
                case 2:
                    System.out.println("How much would you like to deposit?");
                    System.out.print("> $");
                    double dep_amnt = Double.parseDouble(input.nextLine());
                    System.out.println();

                    acc.makeDeposit(dep_amnt);

                    input.nextLine();
                    break;
                case 3:
                    System.out.println("How much would you like to withdraw?");
                    System.out.print("> $");
                    double wdr_amnt = Double.parseDouble(input.nextLine());
                    System.out.println();

                    acc.makeWithdrawal(wdr_amnt);

                    input.nextLine();
                    break;
                case 4:
                    System.out.println("How much would you like to transfer?");
                    System.out.print("> $");
                    double trans_amnt = Double.parseDouble(input.nextLine());

                    System.out.println("Destination Account number?");
                    System.out.print(">");
                    String destAccNum = input.nextLine();
                    System.out.println();

                    Account destAcc = getAccount(destAccNum);
                    if(destAcc == null){
                        break;
                    }

                    acc.transfer(destAcc, trans_amnt);

                    input.nextLine();
                    break;
                case 5:
                    System.out.println("Account Statement:");
                    acc.viewTransactionHistory();

                    input.nextLine();
                    break;
                case 6:
                    System.out.println("Thank you for banking with us!");
                    boolean success = BANKDB.saveData(CUSTOMER_ACCOUNTS);
                    if(!success){
                        System.out.println("Failed to save data to database. Exit anyway?");
                        System.out.println("Yes (y) / No (n)");
                        System.out.println("> ");
                        char qt = input.nextLine().charAt(0);

                        if(qt == 'y' || qt == 'Y'){
                            return;
                        }
                        else{
                            break;
                        }
                    }
                default:
                    System.out.println("\nInvalid option. Please select options 1 - 6.\n");
                    break;
            }
        }

    }
}
