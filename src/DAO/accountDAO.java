package DAO;

import model.Account;
import java.sql.*;

public class accountDAO {

    private Connection con;

    public accountDAO(Connection con) {

        this.con = con;

    }

    public boolean createAccount(int id,Account account){

        try {
            String sql = "INSERT INTO accounts(customer_id,account_type) VALUES(?,?);";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1,id);
            pstmt.setString(2, account.getAccount_type());

            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void viewAccounts(String email){

        try {

            String sql = "SELECT c.name,a.account_number,a.balance,a.status,a.account_type " +
                    " FROM customers c INNER JOIN accounts a ON c.customer_id = a.customer_id " +
                    " WHERE c.email = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){

                System.out.println("Account no: " + rs.getLong("account_number") + " | Account type: "
                        + rs.getString("account_type") + " | Available balance: " + rs.getBigDecimal("balance")
                        + " /- | Status: " + rs.getString("status"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long available_balance(long account_number){

        try {

            String sql = "SELECT balance FROM accounts WHERE account_number = ?;";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setLong(1,account_number);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){

                return rs.getLong("balance");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;

    }

    public boolean deductAmount(long account_number, long amount) {

        try {

            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setLong(1,amount);
            pstmt.setLong(2,account_number);

            int rows = pstmt.executeUpdate();

            if(rows > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAmount(long accountNo, long amount) {

        try {

            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setLong(1,amount);
            pstmt.setLong(2,accountNo);

            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkAccountNumber(long accountNumber) {

        try {

            String sql = "Select account_number FROM accounts WHERE account_number = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1,accountNumber);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public void viewAllAccounts() {

        try {

            String sql = "SELECT * FROM accounts;";

            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                System.out.println("Account number" + rs.getString("account_number") + " Type: " +
                        rs.getString("account_type") + " Balance: " + rs.getLong("balance")
                        + " Status: " + rs.getString("status") + " Created on: " + rs.getDate("created_at"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean freezeAccount(long accountNumber) {

        try {

            String sql = " UPDATE accounts SET account_status = ? WHERE account_number = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"FROZEN");
            pstmt.setLong(2,accountNumber);
            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean activateAccount(long accountNumber) {

        try {

            String sql = " UPDATE accounts SET account_status = ? WHERE account_number = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"ACTIVE");
            pstmt.setLong(2,accountNumber);
            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
