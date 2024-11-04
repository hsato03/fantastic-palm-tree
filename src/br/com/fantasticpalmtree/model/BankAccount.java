package br.com.fantasticpalmtree.model;

public class BankAccount {
    private long id;

    private double balance;

    private final String owner;

    public BankAccount(double balance, Client owner) {
        this.balance = balance;
        this.owner = owner.getName();
        id = owner.getId();
    }

    public synchronized void deposit(double value) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.balance += value;
    }

    public synchronized void withdraw(double value) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public String toString() {
        return String.format("BankAccount{ id=%d , currentBalance=%.2f, owner='%s'}", id, balance, owner);
    }
}
