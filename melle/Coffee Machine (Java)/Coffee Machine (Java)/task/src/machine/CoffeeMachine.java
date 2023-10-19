import java.text.MessageFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.Locale;

public class CoffeeMachine {
    static int water, milk, beans, cups, money;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        water = 400;
        milk = 540;
        beans = 120;
        cups = 9;
        money = 550;
        int totalCups = totalCups();
        printStatus();
        askAction();
        printStatus();

    }

    private static void printStatus() {
        System.out.println(MessageFormat.format(
                """
                        The coffee machine has:
                        {0} ml of water
                        {1} ml of milk
                        {2} g of coffee beans
                        {3} disposable cups
                        ${4} of money
                        """,
                Integer.toString(water),
                Integer.toString(milk),
                Integer.toString(beans),
                Integer.toString(cups),
                Integer.toString(money)));
        System.out.println("");

    }

    private static int askQuestion(String question) {
        System.out.println(question);
        return scanner.nextInt();
    }

    private static void askAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String action = scanner.next();
        switch (action) {
            case "buy":
                buy();
                break;
            case "fill":
                fill();
                break;
            case "take":
                take();
                break;

            default:
                System.out.println("Invalid action");
                break;
        }
    }

    private static int totalCups() {
        int waterCups = water / 200;
        int milkCups = milk / 50;
        int beansCups = beans / 15;
        return Math.min(Math.min(waterCups, milkCups), beansCups);
    }

    private static void buy() {
        int type = askQuestion("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        switch (type) {
            case 1:
                water -= 250;
                beans -= 16;
                money += 4;
                break;
            case 2:
                water -= 350;
                milk -= 75;
                beans -= 20;
                money += 7;
                break;
            case 3:
                water -= 200;
                milk -= 100;
                beans -= 12;
                money += 6;
                break;
            default:
                System.out.println("Invalid type");
                break;
        }
        cups--;
        System.out.println("");

    }

    private static void fill() {
        water += askQuestion("Write how many ml of water do you want to add:");
        milk += askQuestion("Write how many ml of milk do you want to add:");
        beans += askQuestion("Write how many grams of coffee beans do you want to add:");
        cups += askQuestion("Write how many disposable cups of coffee do you want to add:");
        System.out.println("");

    }

    private static void take() {
        System.out.println(MessageFormat.format("I gave you ${0}", Integer.toString(money)));
        money = 0;
    }

}