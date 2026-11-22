# Banking-Management-System
Banking Management System using Java, JDBC and PostgreSQL

A console-based Banking Management System developed using **Java, JDBC, and PostgreSQL**. This project demonstrates Java Object-Oriented Programming, database connectivity, SQL operations, and secure authentication.

## 🚀 Project Overview

The Banking Management System allows customers to manage their bank accounts and perform essential banking operations. It also provides an admin panel to manage customers, accounts, and account statuses.

## 🛠️ Technologies Used

- **Programming Language:** Java
- **Database:** PostgreSQL
- **Database Connectivity:** JDBC
- **Password Security:** BCrypt
- **IDE:** IntelliJ IDEA

## ✨ Features

### 👤 Customer Module
- Customer registration
- Secure customer login
- Create bank accounts
- View account details
- Check account balance
- Deposit money
- Withdraw money
- Transfer money between accounts
- View transaction history

### 🔐 Admin Module
- Admin authentication
- View all registered customers
- View all bank accounts
- Freeze bank accounts
- Activate bank accounts
- View transaction records

## 🗄️ Database Design

The application uses PostgreSQL to store and manage banking information.

Main database tables:

| Table | Purpose |
|---|---|
| `customers` | Stores customer information |
| `accounts` | Stores bank account details and balances |
| `transactions` | Stores deposit, withdrawal, and transfer records |
| `admins` | Stores administrator credentials |

## 🧠 Java Concepts Implemented

- Object-Oriented Programming (OOP)
- Encapsulation
- Inheritance and Polymorphism (where implemented)
- Classes and Objects
- Constructors and Getters
- DAO (Data Access Object) pattern
- Exception Handling
- JDBC connectivity
- PreparedStatement and ResultSet
- Database transaction management

## 🔒 Security

- BCrypt password hashing
- PreparedStatement to help prevent SQL injection
- Separate customer and admin authentication

## ⚙️ Installation and Setup

### 1. Clone the repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

### 2. Create the PostgreSQL database

```sql
CREATE DATABASE bank_db;
```

### 3. Create the required tables

Create the `customers`, `accounts`, `transactions`, and `admins` tables using the SQL scripts included in the project.

### 4. Configure database connectivity

Update your database URL, username, and password in the connection configuration.

Use environment variables or another secure configuration method for database credentials. Do not upload passwords to GitHub.

### 5. Add required dependencies

Ensure the PostgreSQL JDBC driver and jBCrypt dependency are configured in your project.

### 6. Run the application

Open the project in IntelliJ IDEA, build it, and run the main Java class.

## 📂 Project Structure

```text
BankingManagementSystem/
│
├── src/
│   ├── DAO/
│   │   ├── customerDAO.java
│   │   ├── accountDAO.java
│   │   ├── AdminDAO.java
│   │   └── transations.java
│   │
│   ├── db/
│   │   └── connection.java
│   │
│   ├── model/
│   │   ├── Customer.java
│   │   ├── Account.java
│   │   └── Admin.java
│   │
│   ├── util/
│   │   └── PasswordUtil.java
│   │
│   └── menu.java
│
└── README.md
```

*Adjust the folder structure above to match your actual repository.*

## 🎯 Learning Outcome

Building this project helped me strengthen my Java programming, SQL, JDBC, database management, and backend development skills by implementing practical banking operations.

## 👨‍💻 Author

**Java Backend Development Enthusiast**

If you find this project useful, feel free to explore the repository and share your feedback!

---

⭐ Thank you for checking out this project!
