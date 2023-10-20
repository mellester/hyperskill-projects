import java.text.MessageFormat;
import java.util.Scanner;

public class CoffeeMachine {

    static int water = 400;
    static int milk = 540;
    static int beans = 120;
    static int cups = 9;
    static int money = 550;

    enum State {
        START,
        ACTION,
        BUY,
        FILL,
        END,
    }

    private static State state = State.START;

    enum Fill {
        WATER,
        MILK,
        BEANS,
        CUPS,
        END,
    }

    private static Fill fill = Fill.END;

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        do {
            if (state == State.START) {
                state = askAction();
            }
            String input = scanner.nextLine();
            state = transition(input);
        } while (state != State.END);
    }

    public static State transition(String input) {
        return switch (state) {
            case START -> askAction();
            case ACTION -> answeredAction(input);
            case BUY -> buy(input);
            case FILL -> fill(input);
            default -> State.START;
        };
    }

    private static State fill(String input) {
        try {
            final int value = Integer.parseInt(input);
            switch (fill) {
                case WATER:
                    water += value;
                    System.out.println("Write how many ml of milk do you want to add:");
                    fill = Fill.MILK;
                    return State.FILL;
                case MILK:
                    milk += value;
                    System.out.println(
                            "Write how many grams of coffee beans do you want to add:"
                    );
                    fill = Fill.BEANS;
                    return State.FILL;
                case BEANS:
                    beans += value;
                    System.out.println(
                            "Write how many disposable cups of coffee do you want to add:"
                    );
                    fill = Fill.CUPS;
                    return State.FILL;
                case CUPS:
                    cups += value;
                    System.out.println();
                    fill = Fill.END;
                    return State.START;
                default:
                    System.out.println(
                            "Sorry, I don't understand what you mean by that."
                    );
                    return State.START;
            }
        } catch (Exception e) {
            System.out.println("Sorry, Not a number");
            return state;
        }
    }

    private static State printStatus() {
        System.out.format(
                """
                                            The coffee machine has:
                                            %d ml of water
                                            %d ml of milk
                                            %d g of coffee beans
                                            %d disposable cups
                                            $%d of money \n
                                        """,
                water,
                milk,
                beans,
                cups,
                money
        );
        return State.START;
    }

    private static State askAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        return State.ACTION;
    }

    private static State answeredAction(String input) {
        var action = input.trim().toLowerCase();
        return switch (action) {
            case "buy" -> askBuy();
            case "fill" -> askWater();
            case "take" -> take();
            case "remaining" -> printStatus();
            case "exit" -> State.END;
            default -> {
                System.out.println("Invalid action");
                yield State.START;
            }
        };
    }

    private static int totalCups() {
        int waterCups = water / 200;
        int milkCups = milk / 50;
        int beansCups = beans / 15;
        return Math.min(Math.min(waterCups, milkCups), beansCups);
    }

    private static State askBuy() {
        System.out.println(
                "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:"
        );
        return State.BUY;
    }

    private static State buy(String input) {
        String type = input.trim().toLowerCase();
        try {
            switch (type) {
                case "1":
                case "espresso":
                    checkSupply(250, 0, 16);
                    subtractFromSupply(250, 0, 16);
                    money += 4;
                    break;
                case "2":
                case "latte":
                    checkSupply(350, 75, 20);
                    subtractFromSupply(350, 75, 20);
                    money += 7;
                    break;
                case "3":
                case "cappuccino":
                    checkSupply(200, 100, 12);
                    subtractFromSupply(200, 100, 12);
                    money += 6;
                    break;
                case "back":
                    return State.START;
                default:
                    System.out.println("Invalid type");
                    break;
            }
            cups--;
            System.out.println("I have enough resources, making you a coffee!");
            return State.START;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return State.START;
        }
    }

    private static State askWater() {
        System.out.println("Write how many ml of water do you want to add:");
        fill = Fill.WATER;
        return State.FILL;
    }

    private static State take() {
        System.out.println(
                MessageFormat.format("I gave you ${0}", Integer.toString(money))
        );
        money = 0;
        return State.START;
    }

    private static void checkSupply(
            int waterToCheck,
            int milkToCheck,
            int beansToCheck
    ) throws Exception {
        if (waterToCheck > water) throw new Exception("Sorry, not enough water!");
        if (milkToCheck > milk) throw new Exception("Sorry, not enough milk!");
        if (beansToCheck > beans) throw new Exception("Sorry, not enough beans!");
        if (cups <= 0) throw new Exception("Sorry, not enough cups!");
    }

    private static void subtractFromSupply(
            int waterToSub,
            int milkToSub,
            int beansToSub
    ) {
        water -= waterToSub;
        milk -= milkToSub;
        beans -= beansToSub;
    }
}
