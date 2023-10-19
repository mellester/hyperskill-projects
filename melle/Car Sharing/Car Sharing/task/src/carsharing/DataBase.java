package carsharing;

import java.io.UncheckedIOException;
import java.sql.*;
import java.util.*;

public class DataBase implements AutoCloseable {
    private Connection conn;

    DataBase(String[] args) throws SQLException {
        String DATABASE_NAME = "a";
        if (Objects.equals(args[0], "-databaseFileName")) {
            DATABASE_NAME = args[1];
        }
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
            System.exit(1);
        }

        conn = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/" + DATABASE_NAME);
        Statement st = conn.createStatement();
        conn.setAutoCommit(true);
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY (" +
        "ID INT PRIMARY KEY AUTO_INCREMENT," +
        "NAME VARCHAR NOT NULL UNIQUE" +
        ");" +
        "CREATE TABLE IF NOT EXISTS CAR (" +
        "ID INT PRIMARY KEY AUTO_INCREMENT," +
        "NAME VARCHAR NOT NULL UNIQUE," +
        "COMPANY_ID INT NOT NULL," +
        "CONSTRAINT fk_companyId FOREIGN KEY (COMPANY_ID)" +
        "REFERENCES COMPANY(ID)" +
        ");" +
        "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
        "ID INT PRIMARY KEY AUTO_INCREMENT," +
        "NAME VARCHAR NOT NULL UNIQUE," +
        "RENTED_CAR_ID INT DEFAULT NULL," +
        "CONSTRAINT fk_rentId FOREIGN KEY (RENTED_CAR_ID)" +
        "REFERENCES CAR(ID)" +
        ");";

        st.executeUpdate(sql);
        System.out.println("Created table in given database...");
        st.close();
    }

    @Override
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void createCompany(String name) throws SQLException {
        Statement st = conn.createStatement();
        String sql = "INSERT INTO COMPANY (NAME) VALUES ('" + name + "')";
        st.executeUpdate(sql);
        System.out.println("The company was created!");
    }

    public List<Car> obtainCarList(int companyId) {

        List<Car> carList = new ArrayList<>();
        String sql = "SELECT *  FROM CAR WHERE COMPANY_ID = " + companyId;
        try {
            Statement st = conn.createStatement();
            var res = st.executeQuery(sql);
            while (res.next()) {
                Car car = new Car(res.getInt(1), res.getString(2), res.getInt(3));
                carList.add(car);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
        return  carList;
    }



    public void createCar(Company company, String name) {
        try {
            Statement st = conn.createStatement();
            String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('" + name + "', " + company.id + ")";
            st.executeUpdate(sql);
            System.out.println("The car was added!");
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
    }

    public List<Company> obtainCompanyList() throws SQLException {
            Statement st = conn.createStatement();
            ArrayList<Company> companyList = new ArrayList<>();
            String sql = "SELECT * FROM COMPANY";
            var res = st.executeQuery(sql);
            while (res.next()) {
                Company company = new Company(res.getInt(1), res.getString(2));
                companyList.add(company);
            }
            return companyList;
    }

    public void rentACar(CustomerAccount customerAccount, String name) {
        try {
            Statement st = conn.createStatement();
            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " + customerAccount.rentedCarId + " WHERE ID = " + customerAccount.id;
            st.executeUpdate(sql);
            System.out.println("You rented '" + name + "'");
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
    }

    public void returnCar(CustomerAccount customerAccount) {
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = " + customerAccount.id;
            var res = st.executeQuery(sql);
            if (res.next()) {
                int rentedCarId = res.getInt(1);
                if (rentedCarId == 0) {
                    System.out.println("You didn't rent a car!");
                } else {
                    sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = " + customerAccount.id;
                    st.executeUpdate(sql);
                    System.out.println("You've returned a rented car!");
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
    }

    public void getCustomersRentedCar(CustomerAccount customerAccount) {
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM CAR WHERE ID = " + customerAccount.rentedCarId;
            var res = st.executeQuery(sql);

            if (res.next()) {
                System.out.println("Your rented car:");
                System.out.println(res.getString("name"));
                System.out.println("Company:");
                sql = "SELECT NAME FROM COMPANY WHERE ID = " + res.getInt("company_id");
                var res2 = st.executeQuery(sql);
                res2.next();
                System.out.println(res2.getString("name"));
            } else {
                System.out.println("You didn't rent a car!");
            }
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
    }

    public void createCustomer(String name) {
        try {
            Statement st = conn.createStatement();
            String sql = "INSERT INTO CUSTOMER (NAME) VALUES ('" + name + "')";
            st.executeUpdate(sql);
            System.out.println("The customer was added!");
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }
    }


    public ArrayList<CustomerAccount> obtainCustomerList() {

        ArrayList<CustomerAccount> customers = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM CUSTOMER";
            var res = st.executeQuery(sql);
            while (res.next()) {
                CustomerAccount customer = new CustomerAccount(res.getInt(1), res.getString(2), res.getInt(3));
                customers.add(customer);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error: " + sqlException.getMessage());
        }

        return customers;
    }
}
