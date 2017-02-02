package pt.insuranced.models;

public class Password {
    private int id;

    private String hashedPassword;

    public Password() {
    }

    public Password(int id, String hashedPassword) {
        this.id = id;
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public int getId() {
        return this.id;
    }

}
