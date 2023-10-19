import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        int sum = 0;
        while (scanner.nextInt() != 0) {
            sum++;
        }
        System.out.println(sum);
    }
}