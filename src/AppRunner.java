import enums.ActionLetter;
import model.*;
import util.PaymentAcceptor;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final PaymentAcceptor paymentAcceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });

        paymentAcceptor = choosePayment();
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private PaymentAcceptor choosePayment() {
        print("Выберите способ оплаты");
        print("1 - Монеты");
        print("2 - Купюры");
        print("3 - Карта");

        String input = fromConsole();

        switch (input) {
            case "2":
                return new BillAcceptor(0);
            case "3":
                return new CardAcceptor();
            default:
                return new CoinAcceptor(0);
        }
    }

    private void startSimulation() {
        print("\nВ автомате доступны:");
        showProducts(products);

        print("\nТекущий баланс: " + paymentAcceptor.getAmount());

        UniversalArray<Product> allowProducts = getAllowedProducts();
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();

        for (int i = 0; i < products.size(); i++) {
            // ИСПРАВЛЕНО: было coinAcceptor, стало paymentAcceptor
            if (paymentAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print("a - Пополнить баланс");
        showActions(products);
        print("h - Выйти");

        String action = fromConsole().substring(0, 1);

        try {
            if ("a".equalsIgnoreCase(action)) {
                paymentAcceptor.addAmount(0);
                return;
            }

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter()
                        .equals(ActionLetter.valueOf(action.toUpperCase()))) {

                    if (paymentAcceptor.pay(products.get(i).getPrice())) {
                        print("Вы купили " + products.get(i).getName());
                    }
                    return;
                }
            }

            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            }
        } catch (Exception e) {
            print("Неверный ввод");
        }
    } // ИСПРАВЛЕНО: Добавлена закрывающая скобка метода

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
