package DAO;

import util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class adminDAO {

    private static Connection con;

    public adminDAO(Connection con) {

        this.con = con;

    }

    public static boolean verifyAdmin(String email, String enteredPassword) {

        try {

            String sql = "SELECT * FROM admins WHERE email = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {

                String storedHash = rs.getString("password_hash");
                return PasswordUtil.verifyPassword(enteredPassword, storedHash);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
