package model;

import util.PaymentAcceptor;

import java.util.Scanner;

public class CardAcceptor implements PaymentAcceptor {

    @Override
    public int getAmount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void addAmount(int amount) {
    }

    @Override
    public boolean pay(int amount) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите номер карты: ");
        scanner.nextLine();

        System.out.print("Введите OTP: ");
        scanner.nextLine();

        System.out.println("Оплата прошла");
        return true;
    }
}

