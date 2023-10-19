package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerAccount {

    int id;
    String name;
    int rentedCarId;

    public CustomerAccount(int id, String name, int rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public CustomerAccount(CustomerAccount customerAccount) {
        this.id = customerAccount.id;
        this.name = customerAccount.name;
        this.rentedCarId = customerAccount.rentedCarId;
    }

    public static void startInteraction(DataBase db) {
        

        var list = db.obtainCustomerList();
        if (list.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }




        while (true) {
            printCustomers(list);

            int selection = Integer.parseInt(Main.scanner.nextLine());
            System.out.println();
            if (selection == 0) return;
            list.get(selection -1).customerMenu(db);

        }
    }

    static void createCustomer(DataBase db) {
        String name = Main.askQuestion("Enter the customer name:");
        db.createCustomer(name);
    }

    static void printCustomers(List<CustomerAccount> list) {

        
        System.out.println("Customer list:");


        for (CustomerAccount customerAccount : list) {
            System.out.println(customerAccount.id + ". " + customerAccount.name);
        }

        System.out.println("0. Back");
    }

    private void customerMenu(DataBase db) {
        while (true) {
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");
            int selection = Integer.parseInt(Main.scanner.nextLine());

            switch (selection) {
                case 1:
                    rentACar(db);
                    break;
                case 2:
                    returnCar(db);
                    break;
                case 3:
                    getCustomersRentedCar(db);
                    break;
                case 0:
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + selection);
            }
            
        }
    }

    private void getCustomersRentedCar(DataBase db) {
        db.getCustomersRentedCar(this);
    }

    private void returnCar(DataBase db) {

        db.returnCar(this);
    }

    private void rentACar(DataBase db)  {
        if (this.rentedCarId != 0) {
            System.out.println("You've already rented a car!");
            return;
        }
        try {
            var list = db.obtainCompanyList();
            Company.printCompany(list);
            int selection = Integer.parseInt(Main.scanner.nextLine());
            if (selection == 0) return;
            var com =  list.get(selection -1);
            var cars = com.printCars(db);
            int carSelection = Integer.parseInt(Main.scanner.nextLine());
            if (carSelection == 0) return;
            this.rentedCarId = cars.get(carSelection - 1).id;
            db.rentACar(this, cars.get(carSelection - 1).name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}