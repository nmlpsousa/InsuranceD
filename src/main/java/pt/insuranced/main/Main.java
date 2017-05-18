package pt.insuranced.main;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        String s;

        do {
            System.out.println("Welcome Menu");
            s = scanner.next();
            String[] params = s.split(" ");
            new cli(params);
        } while (!s.equals(0));

    }
}