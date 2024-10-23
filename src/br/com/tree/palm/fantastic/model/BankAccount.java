package br.com.tree.palm.fantastic.model;

public class BankAccount {
    private long id;

    private double currentBalance;

    private String owner;

    public BankAccount(double currentBalance, String owner) {
        this.currentBalance = currentBalance;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
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
                ", currentBalance=" + currentBalance +
                ", owner='" + owner + '\'' +
                '}';
    }
}
