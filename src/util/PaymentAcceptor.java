package util;

public interface PaymentAcceptor {
    int getAmount();
    void addAmount(int amount);
    boolean pay(int amount);
}
