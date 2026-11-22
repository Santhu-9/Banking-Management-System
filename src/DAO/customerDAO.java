package DAO;

import java.sql.*;
import model.Customer;
import util.PasswordUtil;

public class customerDAO{

    private Connection con;

    public customerDAO(Connection con) {

        this.con = con;

    }

    public boolean createNewCustomer(Customer customer) {

        try {

            String sql = "INSERT INTO customers(name,age,email,phone,password_hash) VALUES (?,?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, customer.getName());
            pstmt.setInt(2, customer.getAge());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5,customer.getHashedPassword());

            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int findCustomerId(String email) {

        try {
            String sql = "SELECT customer_id FROM customers WHERE email = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("customer_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean emailExists(String email) {

        try {
            String sql = "SELECT customer_id FROM customers WHERE email = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean verifyCustomer(String email, String entered_password){

        try {

            String sql = "SELECT customer_id,name,password_hash FROM customers WHERE email = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){

                String stored_hash = rs.getString("password_hash");

                boolean verifyPassword = PasswordUtil.verifyPassword(entered_password,stored_hash);

                if(verifyPassword) return true;
                else return false;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public void viewAllCustomers(){

        try {

            String sql = "SELECT * FROM customers;";

            PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();

             while (rs.next()) {

                 System.out.println("Name: " + rs.getString("name") + " Email: " + rs.getString("email")
                 + " phone: " + rs.getString("phone"));

             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}