package Opgave_1;

public class Thread2 extends Thread {
    private SharedString sharedString;

    public Thread2(SharedString sharedString) {
        this.sharedString = sharedString;

    }

    @Override
    public void run() {
        while (true) {
            System.out.println(sharedString.getString());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
