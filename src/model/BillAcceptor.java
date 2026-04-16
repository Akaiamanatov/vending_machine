package model;

import util.PaymentAcceptor;

import java.util.Scanner;

public class BillAcceptor implements PaymentAcceptor {

    private int amount;

    public BillAcceptor(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void addAmount(int amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Внесите купюры: ");
        int input = scanner.nextInt();
        this.amount += input;
    }

    @Override
    public boolean pay(int amount) {
        while (this.amount < amount) {
            System.out.println("Недостаточно средств");
            addAmount(0);
        }
        this.amount -= amount;
        return true;
    }
}