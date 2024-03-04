package Opgave_1;

import java.util.Scanner;

public class Thread1 extends Thread {

    SharedString sharedString = new SharedString();

    public Thread1(SharedString sharedString) {
        this.sharedString = sharedString;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            sharedString.setString(input);
        }
    }


}