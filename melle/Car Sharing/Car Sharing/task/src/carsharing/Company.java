package carsharing;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Company {

    int id;
    String companyName;

    Company(int id, String name) {

        this.id = id;
        this.companyName = name;
    }

    Company(Company company) {

        this.id = company.id;
        this.companyName = company.companyName;
    }

    void startInteraction(DataBase db) {
        while (true) {
            printMenu();
            int input = Main.scanner.nextInt();
            Main.scanner.nextLine();
            switch (input) {
                case 1:
                    printCars(db);
                    break;
                case 2:
                    createCar(db);
                    break;
                case 0:
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + input);
            }
        }

    }

    private void createCar(DataBase db) {
        String name = Main.askQuestion("Enter the car name:");
        db.createCar(this, name);
    }

    void printMenu() {

        System.out.println(
                "'" + companyName + "' company\n" +
                "1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
    }

    static void printCompany(List<Company> companies) {
        System.out.println("Choose the company:");

        int id = 1;

        for (Company company : companies) {
            System.out.println(id + ". " + company.companyName);
            id++;
        }
        System.out.println("0. Back");

    }

    List<Car> printCars(DataBase db) {
        var carList =  db.obtainCarList(id);
        if (carList.isEmpty()) {
            System.out.println("The car list is empty!");
            return carList;
        }
        int i = 1;
        for (Car car : carList) {
            System.out.println(i + ". " + car.name);
            i++;
        }
        return carList;
    }
}