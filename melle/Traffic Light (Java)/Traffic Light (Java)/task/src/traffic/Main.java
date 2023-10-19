package traffic;

import java.util.Scanner;

public class Main {

  final static Scanner scanner = new Scanner(System.in);
  public static void main(String[] args){
    
    System.out.print("Input the number of roads:");
    final var roads = scanner.nextInt();
    System.out.println();
    
    System.out.print("Input the interval:");
    final var interval = scanner.nextInt();
    System.out.println();

    System.out.println("""
            Welcome to the traffic management system!
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit
            """);
  }
  Collectors.to
}
