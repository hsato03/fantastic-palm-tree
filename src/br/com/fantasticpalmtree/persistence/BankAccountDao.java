package br.com.fantasticpalmtree.persistence;

import br.com.fantasticpalmtree.model.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountDao {
    private long id = 1;
    Map<Long, BankAccount> records = new HashMap<>();

    public void save(BankAccount bankAccount) {
        bankAccount.setId(id);
        records.put(id, bankAccount);
        id++;
    }

    public BankAccount findById(long id) {
        return records.get(id);
    }

    public void remove(long id) {
        records.remove(id);
    }
}
