package br.com.fantasticpalmtree.model;

public class BankAccount {
    private long id;

    private double balance;

    private String owner;

    public BankAccount(double balance, String owner) {
        this.balance = balance;
        this.owner = owner;
    }

    public synchronized void deposit(double value) {
        this.balance += value;
    }

    public synchronized void withdraw(double value) {
        this.balance -= value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", currentBalance=" + balance +
                ", owner='" + owner + '\'' +
                '}';
    }
}
