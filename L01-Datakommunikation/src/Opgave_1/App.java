package Opgave_1;

public class App {
    public static void main(String[] args) {
        SharedString sharedString = new SharedString();

        Thread1 thread1 = new Thread1(sharedString);
        Thread2 thread2 = new Thread2(sharedString);

        thread1.start();
        thread2.start();
    }
}
