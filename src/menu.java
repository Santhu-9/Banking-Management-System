import db.connection;
import model.*;
import DAO.*;
import util.PasswordUtil;
import java.sql.Connection;
import java.util.*;

public class menu {

    static Connection con = connection.getConnection();

    static customerDAO c_dao = new customerDAO(con);
    static accountDAO a_dao = new accountDAO(con);
    static transations t_dao = new transations(con);
    static adminDAO adminDAO = new adminDAO(con);

    static void main() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println(" ");
            System.out.println("---------- LALITHA NATIONAL BANK ----------");
            System.out.println(" ");
            System.out.println("1. Registration");
            System.out.println("2. Login Customer");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit Applicaion");

            System.out.println(" ");

            System.out.println("Enter Choice: ");
            int opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1:

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter your Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone Number: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter Account Type: ");
                    String account_type = sc.nextLine();

                    System.out.print("Set your password: ");
                    String password = sc.nextLine();

                    String hashedPassword = PasswordUtil.hashPassword(password);

                    Customer customer = new Customer(name,email,phone,age,hashedPassword);

                    Account account = new Account(account_type );

                    int customerId = c_dao.findCustomerId(email);

                    if (customerId != -1) {

                        System.out.println("Existing customer found!");

                    } else if (c_dao.emailExists(email)) {

                        System.out.println("Email already registered with different customer details.");
                        break;

                    } else {

                        boolean c_created = c_dao.createNewCustomer(customer);

                        if (!c_created) {
                            System.out.println("Customer registration failed!");
                            break;
                        }

                        customerId = c_dao.findCustomerId(email);

                        if (customerId == -1) {
                            System.out.println("Unable to retrieve customer ID!");
                            break;
                        }

                        System.out.println("Customer registered successfully!");
                    }

                    boolean a_created = a_dao.createAccount(customerId, account);

                    if(!a_created) System.out.println("Coundn't create Account");
                    else System.out.println("Account created Successfully");
                    break;

                case 2:

                    System.out.print("Email: ");
                    String email1 = sc.next();

                    System.out.print("Password: ");
                    String pswd = sc.next();

                    boolean verified = c_dao.verifyCustomer(email1,pswd);

                    if(verified) {

                        boolean loggedIn = true;

                        while(loggedIn) {

                            System.out.println(" ");
                            System.out.println("Login Successful");
                            System.out.println(" ");
                            System.out.println("1.Create Account");
                            System.out.println("2.View Accounts");
                            System.out.println("3.Withdraw");
                            System.out.println("4.Transfer to Account");
                            System.out.println("5.View transaction history");
                            System.out.println("6.log out.");

                            int opt1 = sc.nextInt();
                            sc.nextLine();

                            switch (opt1) {

                                case 1:

                                    System.out.print("Enter your Account Type: ");
                                    account_type = sc.nextLine();

                                    int customerId1 = c_dao.findCustomerId(email1);

                                    Account account1 = new Account(account_type);
                                    boolean created = a_dao.createAccount(customerId1, account1);

                                    if(created) System.out.println("Account created Successfully");
                                    else System.out.println("Account not created");

                                    break;

                                case 2:

                                    a_dao.viewAccounts(email1);
                                    break;

                                case 3:

                                    System.out.print("Enter Account number: ");
                                    long accountNumber = sc.nextLong();

                                    System.out.print("Enter amount: ");
                                    long amount = sc.nextLong();

                                    long availableBalance = a_dao.available_balance(accountNumber);

                                    if (amount <= availableBalance) {

                                        boolean deducted = a_dao.deductAmount(accountNumber, amount);

                                        if (!deducted) System.out.println("Withdraw failed");
                                        else {

                                            boolean recorded = t_dao.recordTransaction(accountNumber, null,
                                                    "WITHDRAWAL", amount);

                                            if (recorded) System.out.println("Transaction recorded Successfully");
                                            else System.out.println("Transaction record Failed");

                                            System.out.println("Withdraw Successful");
                                            System.out.println("Available balance: " + a_dao.available_balance(accountNumber) + "/-");

                                        }

                                    } else System.out.println("Insufficient Funds!!!");

                                    break;

                                case 4:

                                    System.out.print("Account number(Transfer from): ");
                                    long fromAccountNo = sc.nextLong();

                                    System.out.print("Account number(Transfer to): ");
                                    long toAccountNo = sc.nextLong();

                                    System.out.print("Enter amount to transfer: ");
                                    long toAmount = sc.nextLong();

                                    long fromAvailableBalance = a_dao.available_balance(fromAccountNo);

                                    if (toAmount <= fromAvailableBalance) {

                                        boolean deducted = a_dao.deductAmount(fromAccountNo, toAmount);

                                        if (deducted) {

                                            boolean added = a_dao.addAmount(toAccountNo, toAmount);

                                            if (added) {

                                                boolean recorded = t_dao.recordTransaction(fromAccountNo, toAccountNo,
                                                        "TRANSFER", toAmount);

                                                if (recorded) System.out.println("Transaction recorded Successfully");
                                                else System.out.println("Transaction record Failed");

                                                System.out.println("Money Transfer Successful");
                                            } else {
                                                boolean returned = a_dao.addAmount(fromAccountNo, toAmount);
                                                if (returned) System.out.println("Your money was Refunded");
                                            }
                                            ;
                                        } else System.out.println("Transfer failed");
                                    } else System.out.println("Insufficient Funds");

                                    break;

                                case 5:

                                    System.out.print("Enter accountNumber");
                                    long accNo = sc.nextLong();

                                    t_dao.getTransactionHistory(accNo);

                                    break;

                                case 6:

                                    loggedIn = false;
                                    break;

                                default:
                                    System.out.println("Invalid Choice");
                            }

                        }
                    }

                    else System.out.println("Incorrect login details");

                    break;

                case 3:

                    loginAdmin(sc);

                    break;

                case 4:

                    con.close();
                    break;

                default:

                    System.out.println("Invalid Choice!!!");

            }
        }


    }

    static void loginAdmin(Scanner sc) {

        System.out.print("Enter Admin Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = sc.nextLine();

        if (adminDAO.verifyAdmin(email, password)) {
            System.out.println("Admin Login Successful!");
            adminMenu(sc);
        } else {
            System.out.println("Invalid Admin Credentials!");
        }
    }

    static void adminMenu(Scanner sc) {

        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n========== ADMIN PANEL ==========");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Accounts");
            System.out.println("3.Deposit money to Account");
            System.out.println("4. Freeze Account");
            System.out.println("5. Activate Account");
            System.out.println("6. View All Transactions");
            System.out.println("7. Logout");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    c_dao.viewAllCustomers();
                    break;
                case 2:
                    a_dao.viewAllAccounts();
                    break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    long accNo = sc.nextLong();

                    boolean verified = a_dao.checkAccountNumber(accNo);

                    if(!verified) {
                        System.out.println("Invalid Account Number");
                        break;
                    }
                    else {
                        System.out.print("Enter Amount: ");
                        long amount = sc.nextLong();

                        a_dao.addAmount(accNo, amount);
                        System.out.println("Amount Credited Successfully");
                    }
                case 4:
                    System.out.print("Enter Account Number to Freeze: ");
                    long accountNmuber = sc.nextLong();

                    boolean exists = a_dao.checkAccountNumber(accountNmuber);

                    if(!exists) {
                        System.out.println("Invalid Account Number");
                        break;
                    }

                    else {
                        a_dao.freezeAccount(accountNmuber);
                        System.out.println("Account Freezed successfully");
                    }
                    break;
                case 5:
                    System.out.print("Enter Account Number to Activate: ");
                    long accountNmuber1 = sc.nextLong();

                    boolean exists1 = a_dao.checkAccountNumber(accountNmuber1);

                    if(!exists1) {
                        System.out.println("Invalid Account Number");
                        break;
                    }

                    else {
                        a_dao.activateAccount(accountNmuber1);
                        System.out.println("Account Activated successfully");
                    }
                    break;
                case 6:
                    t_dao.viewAllTransactions();
                    break;
                case 7:
                    loggedIn = false;
                    System.out.println("Admin Logged Out!");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
