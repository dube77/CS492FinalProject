package account.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

	private final StringProperty websiteName;
	private final StringProperty username;
	private final StringProperty password;
	private final StringProperty websiteUrl;
	private final StringProperty notes;

    public Account() {
        this(null, null, null, null, null);
    }

    public Account(String websiteName, String username, String password, String websiteUrl, String notes) {
        this.websiteName = new SimpleStringProperty(websiteName);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.websiteUrl = new SimpleStringProperty(websiteUrl);
        this.notes = new SimpleStringProperty(notes);
    }

    //Website Name
    public String getWebsiteName() {
        return websiteName.get();
    }
    public void setWebsiteName(String websiteName) {
        this.websiteName.set(websiteName);
    }
    public StringProperty websiteNameProperty() {
        return websiteName;
    }

    //Username
    public String getUsername() {
        return username.get();
    }
    public void setUsername(String username) {
        this.username.set(username);
    }
    public StringProperty usernameProperty() {
        return username;
    }

    //Password
    public String getPassword() {
        return password.get();
    }
    public void setPassword(String password) {
        this.password.set(password);
    }
    public StringProperty passwordProperty() {
        return password;
    }

    //Website URL
    public String getWebsiteUrl() {
        return websiteUrl.get();
    }
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl.set(websiteUrl);
    }
    public StringProperty websiteUrlProperty() {
        return websiteUrl;
    }

    //Notes
    public String getNotes() {
        return notes.get();
    }
    public void setNotes(String notes) {
        this.notes.set(notes);
    }
    public StringProperty notesProperty() {
        return notes;
    }
    
    @Override
    public String toString() {
        return this.websiteName + "|" + this.username + "|" + this.password + "|" + this.websiteUrl + "|" + this.notes;
    }
}