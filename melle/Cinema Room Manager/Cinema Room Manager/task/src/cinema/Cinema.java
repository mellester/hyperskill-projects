package cinema;

import java.util.Scanner;

public class Cinema {
    static int rows;
    static int seats;
    static int totalTickets = 0;
    static int currentIncome = 0;

    public  static  final Scanner scanner = new Scanner(System.in);

    static char [][] cinema;

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        cinema = new char[rows + 1][ seats + 1];
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                if (i == 0 && j == 0) cinema[i][j] = ' ';
                else
                if (j == 0) cinema[i][j] = Integer.toString(i).charAt(0);
                else
                if (i == 0 ) cinema[i][j] = Integer.toString(j).charAt(0);
                else
                    cinema[i][j] = 'S';
            }
        }
        int option;
        do {
            System.out.println("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit""");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    print(args);
                    break;
                case 2:
                    buyTicket();
                    break;
                case 0:
                    break;
                case 3:
                    statistics();
                    break;
                default:
                    System.out.println("Wrong option!");
                    break;
            }
        } while ( option != 0 );

    }

    public static void statistics() {
        System.out.println("Number of purchased tickets: " + totalTickets);
        double percentage = (double) totalTickets / (rows * seats) * 100;
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome());
    }

    public static int totalIncome() {
        int income = 0;
        if (rows * seats <= 60) {
            income = rows * seats * 10;
        } else {
            if (rows % 2 == 0) {
                income = (rows / 2) * seats * 10 + (rows / 2) * seats * 8;
            } else {
                income = (rows / 2) * seats * 10 + (rows / 2 + 1) * seats * 8;
            }
        }
        return income;
    }

    public static void print(String[] args) {

        System.out.println("Cinema:");
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void  buyTicket() {
        try {
        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();
        System.out.println("Total income:");
        int income = 0;
        if (rows * seats <= 60) {
            income = 10;
        } else {
            if (row + 1 / 2 <= rows / 2) {
                income = 10;
            } else {
                income = 8;
            }
        }
        System.out.println("Ticket price: $" + income);
        if (cinema[row][seat] == 'B') {
            System.out.println("That ticket has already been purchased!");
            buyTicket();
        }
        cinema[row][seat] = 'B';
        totalTickets++;
        currentIncome += income;
        }
        catch (Exception e) {
            System.out.println("Wrong input!");
            buyTicket();
        }
    }

}