package carsharing;

import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        System.out.println(Arrays.toString(args));

        try (DataBase db = new DataBase(args);) {

            int input;
            while (true) {
                System.out.println("""
                        1. Log in as a manager
                        2. Log in as a customer
                        3. Create a customer
                        0. Exit
                        """);
                input = scanner.nextInt();
                scanner.nextLine();
                switch (input) {
                    case 1:
                        new Manager(db).startManaging();
                        break;
                    case 2:
                        CustomerAccount.startInteraction(db);
                        break;
                    case 3:
                        CustomerAccount.createCustomer(db);
                        break;
                    case 0:
                        return;
                    default:
                        throw new IllegalStateException("Unexpected value: " + input);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
    }

    public static String askQuestion(String question) {
        System.out.println(question);
        return scanner.nextLine();

    }

}