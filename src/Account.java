public class Account {
    public String website;
    public String username;
    public String password;

    /**
     * Account Object Constructor
     * @param website String value for Account.website
     * @param username String value for Account.username
     * @param password String value for Account.password
     */
    public Account(String website, String username, String password){
        this.website = website;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return this.website + "," + this.username + "," + this.password;
    }
}
