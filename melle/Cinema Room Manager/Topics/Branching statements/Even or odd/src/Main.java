import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        while (scanner.hasNext()) {
            var int1  = scanner.nextInt();
            if (int1 == 0 ) return;
            System.out.println(
                    int1 % 2 != 0 ? "odd" :
                    "even"
            );
        }
    }
}