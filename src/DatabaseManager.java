import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.System.exit;

public class DatabaseManager {
    Connection con;

    PreparedStatement ps;
    String query;
    ResultSet rs;

    DatabaseManager(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bankdb","root","");
        }
        catch (ClassNotFoundException | SQLException e){
            System.out.println("Connection to database has failed. Please make sure the database is running.\n");
            System.out.println(e.getMessage());
            exit(-1);
        } catch(Exception e){
            System.out.println(e.getMessage());
            exit(-1);
        }
        /**/
    }

    public boolean saveData(HashMap<String, Account> customerAccounts){

        for(Account acc: customerAccounts.values()){
            try{
                query = "INSERT INTO accounts (account_pk, username, password, balance) " +
                        "VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE balance = ?";
                ps = con.prepareStatement(query);

                ps.setString(1, acc.getAccountNum());
                ps.setString(2, acc.getUsername());
                ps.setString(3, acc.getPassword());
                ps.setDouble(4, acc.getBalance());
                ps.setDouble(5, acc.getBalance());

                int i = ps.executeUpdate();
                if (!(i > 0)){ return false; }

                //save transactions
                for(String trans: acc.getTransactionHistory()){
                    query = "SELECT count(*) FROM transactions WHERE account_fk = ? AND details = ? ";
                    ps = con.prepareStatement(query);
                    ps.setString(1, acc.getAccountNum());
                    ps.setString(2, trans);
                    rs = ps.executeQuery();

                    rs.next();
                    if(rs.getInt(1) == 0){
                        query = "INSERT INTO transactions (account_fk, details) " +
                                "VALUES (?,?)";
                        ps = con.prepareStatement(query);
                        ps.setString(1, acc.getAccountNum());
                        ps.setString(2, trans);
                        i = ps.executeUpdate();
                        if (!(i > 0)){ return false; }
                    }
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
                return false;
            }
        }

        return true;
    }

    public HashMap<String, Account> loadData(){
       HashMap<String, Account> loadedAccounts = new HashMap<String, Account>();

       try{
           query = "SELECT * FROM accounts";
           ps = con.prepareStatement(query);
           rs = ps.executeQuery(query);

           while(rs.next()){
               String username, accountNum, password;
               double balance;
               ArrayList<String> transHist = new ArrayList<>();

               accountNum = rs.getString(1);
               username = rs.getString(2);
               password = rs.getString(3);
               balance = rs.getDouble(4);

               query = "SELECT * FROM transactions WHERE `account_fk` = ?";
               ps = con.prepareStatement(query);
               ps.setString(1, accountNum);
               ResultSet rs2 = ps.executeQuery();

               while(rs2.next()){
                   transHist.add(rs2.getString(3));
               }

               Account newAcc = new Account(username, accountNum, password, balance, transHist);
               loadedAccounts.put(newAcc.getAccountNum(), newAcc);

           }
       }
       catch (Exception e){
           System.out.println(e.toString());
           return null;
       }

       return loadedAccounts;
    }
}
