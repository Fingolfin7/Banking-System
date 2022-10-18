import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Random;

public class Bank {
    HashMap<String, Account> customerAccounts;
    double totalAccountBalances;

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
        }while(customerAccounts.containsKey(accNumber));

        return accNumber;
    }

    public Account getAccount(String accountNum){
        return customerAccounts.get(accountNum);
    }

    public Account makeNewAccount(String name, int pin){
        String accountNumber = makeAccountNumber();
        Account newAcc = new Account(name, accountNumber, pin);
        customerAccounts.put(accountNumber, newAcc);

        return newAcc;
    }
}
