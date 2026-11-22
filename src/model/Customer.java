package model;

public class Customer {
    String name;
    String email;
    String phone;
    int age;
    String hashedPassword;

    public Customer(String name, String email, String phone, int age,String hashedPassword) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.hashedPassword = hashedPassword;
    }

    public String getName() { return name;}
    public String getEmail(){ return email; }
    public String getPhone(){ return phone; }
    public int getAge(){ return age;}
    public String getHashedPassword() {
        return hashedPassword;
    }
}
