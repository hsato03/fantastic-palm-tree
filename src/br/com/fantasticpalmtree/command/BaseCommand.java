package br.com.fantasticpalmtree.command;

import br.com.fantasticpalmtree.persistence.BankAccountDao;

public abstract class BaseCommand {
    protected BankAccountDao bankAccountDao = BankAccountDao.getInstance();
}
