package pt.insuranced.main;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        String s;

        do {
            System.out.println("Welcome Menu\n");
            System.out.println("-a New Client");
            System.out.println("-b New Client");
            System.out.println("-c Client Details");
            System.out.println("-d New Policy");
            System.out.println("-e Policy Details");
            System.out.print("\nOption: ");
            s = scanner.next();
            String[] params = s.split(" ");
            new cli(params, scanner);
        } while (!s.equals(0));

    }
}