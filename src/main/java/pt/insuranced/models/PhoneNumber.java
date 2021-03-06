package pt.insuranced.models;

public class PhoneNumber {
    private String id;

    private String prefix;

    private int number;

    public PhoneNumber() {
    }

    public PhoneNumber(String id, String prefix, int number) {
        this.id = id;
        this.prefix = prefix;
        this.number = number;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return new StringBuilder(this.prefix)
                .append(this.number)
                .toString();
    }

}
