/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank_kata;

/**
 *
 * @author hichem
 */
public class Account {
    String user="";
    String pass="";
    double Balance=0;
    String id="";

    public Account() {
        System.out.println("Welcome to the Bank Application, to enter the Application you must authenticate");
    }

    public Account(String user, String pass, double Balance, String id) {
        this.user = user;
        this.pass = pass;
        this.Balance = Balance;
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
