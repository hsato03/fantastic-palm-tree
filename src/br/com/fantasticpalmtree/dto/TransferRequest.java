package br.com.fantasticpalmtree.dto;

public class TransferRequest extends BaseRequest {
    private final long origin;
    private final double value;
    private final long target;

    public TransferRequest(long originAccount, double value, long targetAccount) {
        this.value = value;
        this.origin = originAccount;
        this.target = targetAccount;
    }

    public long getOrigin() {
        return origin;
    }

    public double getValue() {
        return value;
    }

    public long getTarget() {
        return target;
    }
}
