package Opgave_1;

public class SharedString {
    private String string;

    public SharedString() {
        this.string = "";
    }

    public synchronized void setString(String string) {
        this.string = string;
    }

    public synchronized String getString() {
        return string;
    }
}
