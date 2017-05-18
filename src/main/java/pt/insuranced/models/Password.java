package pt.insuranced.models;

public class Password {
    private Long id;

    private String hashedPassword;

    public Password() {
    }

    public Password(Long id, String hashedPassword) {
        this.id = id;
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Long getId() {
        return this.id;
    }

}
