package ar.edu.unq.virtuaula.builder;

import ar.edu.unq.virtuaula.model.AccountType;

public class AccountTypeBuilder {

    private final AccountType instance = new AccountType();

    public static AccountTypeBuilder accountTypeWithUsername(String name) {
        AccountTypeBuilder accountTypeBuilder = new AccountTypeBuilder();
        accountTypeBuilder.instance.setName(name);
        return accountTypeBuilder;
    }

    public AccountType build() {
        return this.instance;
    }
}
