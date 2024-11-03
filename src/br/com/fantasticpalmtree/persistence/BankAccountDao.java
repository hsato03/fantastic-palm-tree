package br.com.fantasticpalmtree.persistence;

import br.com.fantasticpalmtree.model.BankAccount;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountDao {
    private static BankAccountDao instance;

    Map<Long, BankAccount> records = new HashMap<>();

    private BankAccountDao() {}

    public static synchronized BankAccountDao getInstance() {
        if (instance == null) {
            instance = new BankAccountDao();
        }
        return instance;
    }

    public void save(BankAccount bankAccount) {
        records.put(bankAccount.getId(), bankAccount);
    }

    public BankAccount findById(long id) {
        return records.get(id);
    }

    public Collection<BankAccount> findAll() {
        return records.values();
    }

    public void remove(long id) {
        records.remove(id);
    }
}
