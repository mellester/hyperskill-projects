package carsharing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager {
    private DataBase db;
    Manager(DataBase db) {
        this.db =db;
    }
    void startManaging() {
        while (true) {
            System.out.println("""
                1. Company list
                2. Create a company
                0. Back
                    """);
            int input = Main.scanner.nextInt();
            Main.scanner.nextLine();
            switch (input) {
                case 1:
                    companyList(db);
                    break;
                case 2:
                    createCompany(db);
                    break;
                case 0:
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + input);
            }
        }
    }


    private void printManagerMenu() {

        System.out.println("" +
                "1. Company list\n" +
                "2. Create a company\n" +
                "0. Back"
        );
    }
    private static void createCompany(DataBase db) {
        String name = Main.askQuestion("Enter the company name:");
        try {
            db.createCompany(name);

        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
    }

    static void printCompanies(ArrayList<Company> companies) {


        System.out.println("Choose the company:");

        int id = 1;

        for (Company company : companies) {
            System.out.println(id + ". " + company.companyName);
            id++;
        }
        System.out.println("0. Back");
    }

    private static void companyList(DataBase db) {
        try {
            var result =  db.obtainCompanyList();
            if(result.isEmpty()) {
                System.out.println("The company list is empty");
                return;
            }
            Company.printCompany(result);
            int input = Main.scanner.nextInt();
            Main.scanner.nextLine();
            if (input == 0) return;
            result.get(input - 1).startInteraction(db);

        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }

    }
}