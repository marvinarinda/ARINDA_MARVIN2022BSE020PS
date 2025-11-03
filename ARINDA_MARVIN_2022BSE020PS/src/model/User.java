package model;

public class User {
    private String username;
    private String passwordHash;
    private String passwordSalt;

    public User(String username, String passwordHash, String passwordSalt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getPasswordSalt() { return passwordSalt; }
}