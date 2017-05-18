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
            System.out.println("-b Client Details");
            System.out.println("-c New Policy");
            System.out.println("-d Policy Details");
            System.out.print("\nOption: ");
            s = scanner.next();
            String[] params = s.split(" ");
            new cli(params, scanner);
        } while (!s.equals(0));

    }
}