package br.com.fantasticpalmtree.dto;

public final class DepositRequest extends BaseRequest {
    private final double value;
    private final long account;

    public DepositRequest(long account, double value) {
        this.account = account;
        this.value = value;
    }

    @Override
    public String toString() {
        return "DepositRequest[" +
                "id=" + id + ", " +
                "value=" + value + ']';
    }

    public long getAccount() {
        return account;
    }

    public double getValue() {
        return value;
    }
}
