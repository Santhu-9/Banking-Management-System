package DAO;

import java.sql.*;
import java.util.Objects;

public class transations {

    private Connection con;

    public transations(Connection con) {

        this.con = con;

    }

    public boolean recordTransaction(Long fromAccNo, Long toAccNo, String transactionType, long amount) {

        try {

            String sql = "INSERT INTO transactions " +
                         "(from_account_no,to_account_no,transaction_type,amount) VALUES (?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1,fromAccNo);
            pstmt.setLong(2,toAccNo);
            pstmt.setString(3,transactionType);
            pstmt.setLong(4,amount);

            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void getTransactionHistory(long accountNumber) {

        try {

            String sql = "SELECT * FROM transactions WHERE from_account_no = ? OR to_account_no = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setLong(1,accountNumber);
            pstmt.setLong(2,accountNumber);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {

                if(Objects.equals(rs.getString("transaction_type"), "DEPOSIT")) {

                    System.out.println("Account no: " + rs.getLong("to_account_no") + " Type " +
                            rs.getString("transaction_type") + " Amount: " + rs.getLong("amount") + " /-" +
                            " Date: " + rs.getTimestamp("transaction_date"));
                }

                else if(Objects.equals(rs.getString("transaction_type"), "WITHDRAWAL")) {
                    System.out.println("Account no: " + rs.getLong("from_account_no") + " Type " +
                            rs.getString("transaction_type") + " Amount: " + rs.getLong("amount") + " /-" +
                            " Date: " + rs.getTimestamp("transaction_date"));
                }

                else {
                    System.out.println("From Account no: " + rs.getLong("from_account_no") +
                            " To Account no: " + rs.getLong("to_account_no") + " Type " +
                            rs.getString("transaction_type") + " Amount: " + rs.getLong("amount") + " /-" +
                            " Date: " + rs.getTimestamp("transaction_date"));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllTransactions() {

        try {

            String sql = "SELECT * FROM transactions;";

            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {

                if(Objects.equals(rs.getString("transaction_type"), "DEPOSIT")) {

                    System.out.println("Account no: " + rs.getLong("to_account_no") + " Type " +
                            rs.getString("transaction_type") + " Amount: " + rs.getLong("amount") + " /-" +
                            " Date: " + rs.getTimestamp("transaction_date"));
                }

                else if(Objects.equals(rs.getString("transaction_type"), "WITHDRAWAL")) {
                    System.out.println("Account no: " + rs.getLong("from_account_no") + " Type " +
                            rs.getString("transaction_type") + " Amount: " + rs.getLong("amount") + " /-" +
                            " Date: " + rs.getTimestamp("transaction_date"));
                }

                else {
                    System.out.println("From Account no: " + rs.getLong("from_account_no") +
                            " To Account no: " + rs.getLong("to_account_no") + " Type " +
                            rs.getString("transaction_type") + " Amount: " + rs.getLong("amount") + " /-" +
                            " Date: " + rs.getTimestamp("transaction_date"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
